package com.banque;

import com.banque.model.PersonneMorale;
import com.banque.model.PersonnePhysique;
import com.banque.model.ProduitBancaire;
import com.banque.model.TypePersonneMorale;
import com.banque.model.TypeProduit;
import com.banque.repository.PersonneMoraleRepository;
import com.banque.repository.PersonnePhysiqueRepository;
import com.banque.repository.ProduitBancaireRepository;
import com.banque.repository.TypePersonneMoraleRepository;
import com.banque.repository.TypeProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BanqueApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BanqueApplication.class, args);
    }

    @Bean
    CommandLineRunner testerBackend(TypeProduitRepository typeProduitRepository, PersonneMoraleRepository personneMoraleRepository, PersonnePhysiqueRepository personnePhysiqueRepository, ProduitBancaireRepository produitBancaireRepository, TypePersonneMoraleRepository typePersonneMoraleRepository)
    {
        return args -> {
            //////////////////// Code précédent
            ////////////////////////////////////////

            // Création de trois personnes morales
            PersonneMorale pm1 = new PersonneMorale("102bis rue du Vesuve", "SIRET1", "Pizza Tonio");
            PersonneMorale pm2 = new PersonneMorale("45 Boulevard du fleuve", "SIRET2", "Meubles cosy");
            PersonneMorale pm3 = new PersonneMorale("14 allee des platanes", "SIRET3", "Espaces tres verts");
            // Enregistrement en base
            personneMoraleRepository.save(pm1);
            personneMoraleRepository.save(pm2);
            personneMoraleRepository.save(pm3);
            // Affichage du résultat
            System.out.println(personneMoraleRepository.findAll());
            
            // Test de TypePersonneMorale
            System.out.println("\n=== Test TypePersonneMorale ===");
            TypePersonneMorale typePM1 = new TypePersonneMorale("SARL", "Société à Responsabilité Limitée");
            TypePersonneMorale typePM2 = new TypePersonneMorale("SAS", "Société par Actions Simplifiée");
            TypePersonneMorale typePM3 = new TypePersonneMorale("EURL", "Entreprise Unipersonnelle à Responsabilité Limitée");
            
            typePersonneMoraleRepository.save(typePM1);
            typePersonneMoraleRepository.save(typePM2);
            typePersonneMoraleRepository.save(typePM3);
            
            System.out.println("Types de personnes morales créés :");
            System.out.println(typePersonneMoraleRepository.findAll());
            
            // Test de la relation avec PersonneMorale
            System.out.println("\n=== Test relation PersonneMorale - TypePersonneMorale ===");
            PersonneMorale pmAvecType = new PersonneMorale("456 Avenue des Champs", "SIRET4", "Ma SARL Test", typePM1);
            personneMoraleRepository.save(pmAvecType);
            
            System.out.println("Personne morale avec type :");
            System.out.println(pmAvecType);
            
            // Ajout de personnes physiques
            PersonnePhysique pp1 = new PersonnePhysique("19 rue des fleurs, 80000 Amiens", "Dupont", "Jean");
            personnePhysiqueRepository.save(pp1);
            PersonnePhysique pp2 = new PersonnePhysique("143 boulevard des landes, 64200 Anglet", "Eche", "Piou");
            personnePhysiqueRepository.save(pp2);
            PersonnePhysique pp3 = new PersonnePhysique("56 avenue de Paris, 60000 Beauvais", "Tristan", "Jacques");
            personnePhysiqueRepository.save(pp3);
            System.out.println(personnePhysiqueRepository.findAll());

            List<TypeProduit> typesProduits;
            List<ProduitBancaire> produitBancaires;
            TypeProduit tp1=new TypeProduit((float)0.2,"tp1",0);
            typeProduitRepository.save(tp1);
            TypeProduit tp2=new TypeProduit(3,"tp2",0);
            typeProduitRepository.save(tp2);
            TypeProduit tp3=new TypeProduit(0,"tp3",15);
            typeProduitRepository.save(tp3);

            tp3= typeProduitRepository.findById(tp3.getId()).orElseThrow();
            ProduitBancaire pb1 = new ProduitBancaire(1,"num1", tp3);
            produitBancaireRepository.save(pb1);
            tp2= typeProduitRepository.findById(tp2.getId()).orElseThrow();
            ProduitBancaire pb2 = new ProduitBancaire(2,"num2",tp2);
            produitBancaireRepository.save(pb2);
            tp3= typeProduitRepository.findById(tp3.getId()).orElseThrow();
            ProduitBancaire pb3 = new ProduitBancaire(3,"num3",tp3);
            produitBancaireRepository.save(pb3);

            typesProduits=typeProduitRepository.findAll();
            produitBancaires=produitBancaireRepository.findAll();
            System.out.println(produitBancaires);

            pb1= (ProduitBancaire) produitBancaireRepository.findById(pb1.getId()).orElseThrow();
            produitBancaireRepository.deleteById(pb1.getId());
            if (typeProduitRepository.existsById(tp3.getId())){
                System.out.println("tp3 est toujours dans la base");
            }
            else{
                System.out.println("tp3 n''existe plus");
            }
            if (produitBancaireRepository.existsById(pb1.getId())) {
                System.out.println("pb1 est toujours dans la base");
            }
            else {
                System.out.println("pb1 n''existe plus");
            }

            if (produitBancaireRepository.existsById(pb3.getId())) {
                System.out.println("pb3 est toujours dans la base");
            }
            else {
                System.out.println("pb3 n''existe plus");
            }
        };
    }
}