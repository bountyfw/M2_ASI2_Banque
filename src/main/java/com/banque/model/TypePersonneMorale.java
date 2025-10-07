package com.banque.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "TypePersonneMorale")
@Table(name = "type_personne_morale")
public class TypePersonneMorale {
    @Id
    @SequenceGenerator(name = "type_personne_morale_sequence", sequenceName = "type_personne_morale_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "type_personne_morale_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle", nullable = false, columnDefinition = "TEXT")
    private String libelle;

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    // Relation OneToMany côté NON propriétaire
    // mappedBy contient le nom de l'attribut ManyToOne dans PersonneMorale
    @OneToMany(mappedBy = "typePersonneMorale", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<PersonneMorale> personnesMorales = new ArrayList<>();

    public TypePersonneMorale() {
    }

    public TypePersonneMorale(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PersonneMorale> getPersonnesMorales() {
        return personnesMorales;
    }

    public void setPersonnesMorales(List<PersonneMorale> personnesMorales) {
        this.personnesMorales = personnesMorales;
    }

    public void addPersonneMorale(PersonneMorale personneMorale) {
        this.personnesMorales.add(personneMorale);
    }

    public void removePersonneMorale(PersonneMorale personneMorale) {
        this.personnesMorales.remove(personneMorale);
        personneMorale.setTypePersonneMorale(null);
    }

    @Override
    public String toString() {
        return "TypePersonneMorale{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TypePersonneMorale that = (TypePersonneMorale) o;
        return Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libelle);
    }

    @PreRemove
    private void gererLiens() {
        // Pour casser le lien avec les personnes morales
        // A utiliser dans le cas d'une cardinalité minimale 0
        for (PersonneMorale pm : personnesMorales) {
            pm.setTypePersonneMorale(null);
        }
        personnesMorales.clear();
    }
}
