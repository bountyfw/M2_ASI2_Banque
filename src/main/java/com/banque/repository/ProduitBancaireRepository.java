package com.banque.repository;

import com.banque.model.ProduitBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitBancaireRepository <T extends ProduitBancaire> extends JpaRepository<T, Long> {
}
