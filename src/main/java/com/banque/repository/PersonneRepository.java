package com.banque.repository;

import com.banque.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonneRepository <T extends Personne> extends JpaRepository<T, Long>
{
}
