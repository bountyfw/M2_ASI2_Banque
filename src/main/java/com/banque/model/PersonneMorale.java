package com.banque.model;

import jakarta.persistence.*;

@Entity(name = "PersonneMorale")
@Table(name = "personne_morale")
public class PersonneMorale extends Personne {

    @Column(name = "siret", nullable = true, columnDefinition = "TEXT")
    private String SIRET;
    @Column(name = "raison_sociale", nullable = false, columnDefinition = "TEXT")
    private String raisonSociale;

    // Relation ManyToOne avec TypePersonneMorale
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_personne_morale_id")
    private TypePersonneMorale typePersonneMorale;

    public PersonneMorale() {}

    public PersonneMorale(String adresse, String SIRET, String raisonSociale) {
        super(adresse);
        this.SIRET = SIRET;
        this.raisonSociale = raisonSociale;
    }

    public PersonneMorale(String adresse, String SIRET, String raisonSociale, TypePersonneMorale typePersonneMorale) {
        super(adresse);
        this.SIRET = SIRET;
        this.raisonSociale = raisonSociale;
        this.typePersonneMorale = typePersonneMorale;
        if (typePersonneMorale != null) {
            typePersonneMorale.getPersonnesMorales().add(this);
        }
    }

    public String getSIRET() {
        return SIRET;
    }

    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public TypePersonneMorale getTypePersonneMorale() {
        return typePersonneMorale;
    }

    public void setTypePersonneMorale(TypePersonneMorale typePersonneMorale) {
        this.typePersonneMorale = typePersonneMorale;
    }

    @Override
    public String toString() {
        return "\nPersonneMorale{" +
                "\nid=" + id +
                ", \nSIRET='" + SIRET + '\'' +
                ", \nraisonSociale='" + raisonSociale + '\'' +
                ", \nadresse='" + adresse + '\'' +
                ", \ntypePersonneMorale=" + typePersonneMorale +
                "}\n";
    }

    public String nomComplet() {
        return raisonSociale;
    }
}
