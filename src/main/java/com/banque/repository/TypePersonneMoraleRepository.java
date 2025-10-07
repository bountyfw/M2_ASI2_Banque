package com.banque.repository;

import com.banque.model.TypePersonneMorale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TypePersonneMoraleRepository extends JpaRepository<TypePersonneMorale, Long> {
    ArrayList<TypePersonneMorale> findByLibelle(String libelle);
    ArrayList<TypePersonneMorale> findByLibelleLike(String libelle);
    ArrayList<TypePersonneMorale> findByLibelleContains(String libelle);
    ArrayList<TypePersonneMorale> findFirst3ByOrderByIdDesc();
    
    @Query("SELECT t FROM TypePersonneMorale t where t.libelle = :libelle")
    ArrayList<TypePersonneMorale> findByTypePersonneMorale(@Param("libelle") String libelle);
}
