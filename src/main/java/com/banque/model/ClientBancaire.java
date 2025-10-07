package com.banque.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity
public class ClientBancaire {
    @Id
    @SequenceGenerator( name = "client_bancaire_sequence", sequenceName = "client_bancaire_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "client_bancaire_sequence")
    @Column(name = "id")
    protected Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            // nom de la table d'association
            name = "client_personne",
            // nom de la colonne clé étrangère côté ClientBancaire
            joinColumns = { @JoinColumn(name = "client_bancaire_id") },
            // nom de la colonne clé étrangère côté Personne
            inverseJoinColumns = { @JoinColumn(name = "personne_id") })
    private List<Personne> personnes = new ArrayList<>();

    // Relation OneToMany avec ProduitBancaire
    @OneToMany(mappedBy = "clientBancaire", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProduitBancaire> produitsBancaires = new ArrayList<>();

    public ClientBancaire() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<Personne> getPersonnes() {
        return personnes;
    }
    public void setPersonnes(List<Personne> personnes) {
        this.personnes = personnes;
    }
    public void addPersonne(Personne personne)
    {
        personnes.add(personne);
        personne.addClientBancaire(this);
    }
    public void removePersonne(Personne personne)
    {
        personnes.remove(personne);
        personne.getClientsBancaires().remove(this);
    }

    public List<ProduitBancaire> getProduitsBancaires() {
        return produitsBancaires;
    }

    public void setProduitsBancaires(List<ProduitBancaire> produitsBancaires) {
        this.produitsBancaires = produitsBancaires;
    }

    public void addProduitBancaire(ProduitBancaire produitBancaire) {
        this.produitsBancaires.add(produitBancaire);
    }

    public void removeProduitBancaire(ProduitBancaire produitBancaire) {
        this.produitsBancaires.remove(produitBancaire);
        produitBancaire.setClientBancaire(null);
    }

    @PreRemove
    private void gererLiens()
    {
        // Pour les personnes
        for (Personne personne : this.personnes) {
            personne.getClientsBancaires().remove(this);
        }
        this.personnes.clear();
        
        // Pour les produits bancaires
        for (ProduitBancaire produitBancaire : this.produitsBancaires) {
            produitBancaire.setClientBancaire(null);
        }
        this.produitsBancaires.clear();
    }

    @Override
    public String toString() {
        return "\nClientBancaire{" +
                "\n\tid=" + id +
                ", \n\tpersonnes=" + personnes.size() +
                ", \n\tproduitsBancaires=" + produitsBancaires.size() +
                '}';
    }

}