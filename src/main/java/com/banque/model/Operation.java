package com.banque.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Operation")
@Table(name = "operation")
public class Operation {
    @Id
    @SequenceGenerator(name = "operation_sequence", sequenceName = "operation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "operation_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_operation", nullable = false, columnDefinition = "DATE")
    private Date dateOperation;

    @Column(name = "montant", nullable = false, columnDefinition = "FLOAT")
    private float montant;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "libelle", nullable = true, columnDefinition = "TEXT")
    private String libelle;

    // Relation ManyToOne avec ProduitBancaire
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_bancaire_id", nullable = false)
    private ProduitBancaire produitBancaire;

    public Operation() {
    }

    public Operation(Date dateOperation, float montant, String type, String libelle, ProduitBancaire produitBancaire) {
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.type = type;
        this.libelle = libelle;
        this.produitBancaire = produitBancaire;
        if (produitBancaire != null) {
            produitBancaire.getOperations().add(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ProduitBancaire getProduitBancaire() {
        return produitBancaire;
    }

    public void setProduitBancaire(ProduitBancaire produitBancaire) {
        this.produitBancaire = produitBancaire;
    }

    @Override
    public String toString() {
        return "\nOperation{" +
                "\n\tid=" + id +
                ", \n\tdateOperation=" + dateOperation +
                ", \n\tmontant=" + montant +
                ", \n\ttype='" + type + '\'' +
                ", \n\tlibelle='" + libelle + '\'' +
                ", \n\tproduitBancaire=" + (produitBancaire != null ? produitBancaire.getId() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PreRemove
    private void gererLiens() {
        // On enlève l'opération de la liste des opérations qui est dans le produit bancaire lié
        if (produitBancaire != null) {
            produitBancaire.getOperations().remove(this);
        }
        // On casse le lien de l'opération vers son produit bancaire
        produitBancaire = null;
    }
}
