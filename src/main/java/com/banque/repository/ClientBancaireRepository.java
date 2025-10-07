package com.banque.repository;

import com.banque.model.ClientBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientBancaireRepository extends JpaRepository<ClientBancaire, Long> {
    
    // Note: findById(Long) est déjà hérité de JpaRepository et retourne Optional<ClientBancaire>
    
    // Recherche des clients bancaires ayant une personne spécifique
    @Query("SELECT c FROM ClientBancaire c JOIN c.personnes p WHERE p.id = :personneId")
    List<ClientBancaire> findByPersonneId(@Param("personneId") Long personneId);
    
    // Recherche des clients bancaires ayant une personne avec une adresse spécifique
    @Query("SELECT c FROM ClientBancaire c JOIN c.personnes p WHERE p.adresse = :adresse")
    List<ClientBancaire> findByPersonneAdresse(@Param("adresse") String adresse);
    
    // Recherche des clients bancaires ayant plus de X personnes
    @Query("SELECT c FROM ClientBancaire c WHERE SIZE(c.personnes) > :nombrePersonnes")
    List<ClientBancaire> findByNombrePersonnesSuperieurA(@Param("nombrePersonnes") int nombrePersonnes);
    
    // Recherche des clients bancaires ayant exactement X personnes
    @Query("SELECT c FROM ClientBancaire c WHERE SIZE(c.personnes) = :nombrePersonnes")
    List<ClientBancaire> findByNombrePersonnesEgaleA(@Param("nombrePersonnes") int nombrePersonnes);
    
    // Recherche des clients bancaires vides (sans personnes)
    @Query("SELECT c FROM ClientBancaire c WHERE SIZE(c.personnes) = 0")
    List<ClientBancaire> findClientsVides();
    
    // Recherche des 3 derniers clients créés
    List<ClientBancaire> findFirst3ByOrderByIdDesc();
}
