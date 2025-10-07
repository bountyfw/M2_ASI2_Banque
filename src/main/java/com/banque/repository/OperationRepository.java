package com.banque.repository;

import com.banque.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    
    // Recherche par produit bancaire
    List<Operation> findByProduitBancaireId(Long produitBancaireId);
    
    // Recherche par type d'opération
    List<Operation> findByType(String type);
    
    // Recherche par type contenant
    List<Operation> findByTypeContains(String type);
    
    // Recherche par montant supérieur à
    List<Operation> findByMontantGreaterThan(float montant);
    
    // Recherche par montant inférieur à
    List<Operation> findByMontantLessThan(float montant);
    
    // Recherche par montant entre deux valeurs
    List<Operation> findByMontantBetween(float montantMin, float montantMax);
    
    // Recherche par date d'opération
    List<Operation> findByDateOperation(Date dateOperation);
    
    // Recherche par date d'opération entre deux dates
    List<Operation> findByDateOperationBetween(Date dateDebut, Date dateFin);
    
    // Recherche par libellé contenant
    List<Operation> findByLibelleContains(String libelle);
    
    // Recherche des 5 dernières opérations
    List<Operation> findFirst5ByOrderByDateOperationDesc();
    
    // Recherche par produit bancaire et type
    @Query("SELECT o FROM Operation o WHERE o.produitBancaire.id = :produitBancaireId AND o.type = :type")
    List<Operation> findByProduitBancaireIdAndType(@Param("produitBancaireId") Long produitBancaireId, @Param("type") String type);
    
    // Recherche par produit bancaire et montant supérieur à
    @Query("SELECT o FROM Operation o WHERE o.produitBancaire.id = :produitBancaireId AND o.montant > :montant")
    List<Operation> findByProduitBancaireIdAndMontantGreaterThan(@Param("produitBancaireId") Long produitBancaireId, @Param("montant") float montant);
    
    // Recherche des opérations par client bancaire (via produit bancaire)
    @Query("SELECT o FROM Operation o WHERE o.produitBancaire.clientBancaire.id = :clientBancaireId")
    List<Operation> findByClientBancaireId(@Param("clientBancaireId") Long clientBancaireId);
    
    // Recherche des opérations par client bancaire et type
    @Query("SELECT o FROM Operation o WHERE o.produitBancaire.clientBancaire.id = :clientBancaireId AND o.type = :type")
    List<Operation> findByClientBancaireIdAndType(@Param("clientBancaireId") Long clientBancaireId, @Param("type") String type);
    
    // Recherche des opérations par client bancaire et montant supérieur à
    @Query("SELECT o FROM Operation o WHERE o.produitBancaire.clientBancaire.id = :clientBancaireId AND o.montant > :montant")
    List<Operation> findByClientBancaireIdAndMontantGreaterThan(@Param("clientBancaireId") Long clientBancaireId, @Param("montant") float montant);
}
