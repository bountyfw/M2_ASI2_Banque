package com.banque.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public class ProduitBancaire {
    @Id
    @SequenceGenerator(name = "produit_bancaire_sequence", sequenceName = "produit_bancaire_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "produit_bancaire_sequence")

    @Column(name = "id")
    protected Long id;

    @Column(name = "solde_courant", nullable = false, columnDefinition = "FLOAT")
    private float solde_courant;

    @Column(name = "numero_compte", nullable = false, columnDefinition = "TEXT")
    private String numeroCompte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_produit_id")
    private TypeProduit typeProduit;

    // Relation ManyToOne avec ClientBancaire
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_bancaire_id", nullable = true)
    private ClientBancaire clientBancaire;

    // Relation OneToMany avec Operation
    @OneToMany(mappedBy = "produitBancaire", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();
    public ProduitBancaire(float solde_courant, String numeroCompte, TypeProduit TypeProduit) {
        this.solde_courant = solde_courant;
        this.numeroCompte = numeroCompte;
        this.typeProduit = TypeProduit;
        TypeProduit.getProduitsBancaires().add(this);
    }

    public ProduitBancaire(float solde_courant, String numeroCompte, TypeProduit typeProduit, ClientBancaire clientBancaire) {
        this.solde_courant = solde_courant;
        this.numeroCompte = numeroCompte;
        this.typeProduit = typeProduit;
        this.clientBancaire = clientBancaire;
        if (typeProduit != null) {
            typeProduit.getProduitsBancaires().add(this);
        }
        if (clientBancaire != null) {
            clientBancaire.getProduitsBancaires().add(this);
        }
    }

    public ProduitBancaire() {
    }
    public TypeProduit getTypeProduit() {
        return typeProduit;
    }
    public void setTypeProduit(TypeProduit TypeProduit) {
        this.typeProduit = TypeProduit;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public float getSolde_courant() {
        return solde_courant;
    }
    public void setSolde_courant(float solde_courant) {
        this.solde_courant = solde_courant;
    }
    public String getNumeroCompte() {
        return numeroCompte;
    }
    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public ClientBancaire getClientBancaire() {
        return clientBancaire;
    }

    public void setClientBancaire(ClientBancaire clientBancaire) {
        this.clientBancaire = clientBancaire;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public void removeOperation(Operation operation) {
        this.operations.remove(operation);
        operation.setProduitBancaire(null);
    }

    @Override
    public String toString() {
        return "\nProduitBancaire{" +
                "\n\tid=" + id +
                ", \n\tsolde_courant=" + solde_courant +
                ", \n\tnumeroCompte =" + numeroCompte +
                ", \n\tTypeProduit=" + typeProduit +
                ", \n\tClientBancaire=" + (clientBancaire != null ? clientBancaire.getId() : "null") +
                ", \n\tOperations=" + operations.size() +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitBancaire that = (ProduitBancaire) o;
        return this.id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PreRemove
    private void gererLiens() {
        // On enlève le produit bancaire de la liste des produits qui est dans le type de produit lié
        if (typeProduit != null) {
            typeProduit.getProduitsBancaires().remove(this);
        }
        
        // On enlève le produit bancaire de la liste des produits du client bancaire
        if (clientBancaire != null) {
            clientBancaire.getProduitsBancaires().remove(this);
        }
        
        // Pour les opérations, on casse le lien avec le produit bancaire
        for (Operation operation : operations) {
            operation.setProduitBancaire(null);
        }
        operations.clear();
        
        // On casse tous les liens
        typeProduit = null;
        clientBancaire = null;
    }
}