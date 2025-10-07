package com.banque;

import com.banque.model.ClientBancaire;
import com.banque.model.Operation;
import com.banque.model.PersonneMorale;
import com.banque.model.PersonnePhysique;
import com.banque.model.ProduitBancaire;
import com.banque.model.TypePersonneMorale;
import com.banque.model.TypeProduit;
import com.banque.repository.ClientBancaireRepository;
import com.banque.repository.OperationRepository;
import com.banque.repository.PersonneMoraleRepository;
import com.banque.repository.PersonnePhysiqueRepository;
import com.banque.repository.ProduitBancaireRepository;
import com.banque.repository.TypePersonneMoraleRepository;
import com.banque.repository.TypeProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class BanqueApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BanqueApplication.class, args);
    }

    @Bean
    CommandLineRunner testerBackend(TypeProduitRepository typeProduitRepository, PersonneMoraleRepository personneMoraleRepository, PersonnePhysiqueRepository personnePhysiqueRepository, ProduitBancaireRepository produitBancaireRepository, TypePersonneMoraleRepository typePersonneMoraleRepository, ClientBancaireRepository clientBancaireRepository, OperationRepository operationRepository)
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

            // ========== TEST : Ajoutons des relations personne/client ==========
            System.out.println("\n" + "=".repeat(60));
            System.out.println("TEST : Ajoutons des relations personne/client");
            System.out.println("=".repeat(60));

            // Création de produits bancaires
            tp2 = typeProduitRepository.findById(tp2.getId()).orElseThrow();
            ProduitBancaire pb4 = new ProduitBancaire(1,"num4",tp2);
            produitBancaireRepository.save(pb4);
            tp3 = typeProduitRepository.findById(tp3.getId()).orElseThrow();
            ProduitBancaire pb5 = new ProduitBancaire(2,"num5",tp3);
            produitBancaireRepository.save(pb5);
            tp3 = typeProduitRepository.findById(tp3.getId()).orElseThrow();
            ProduitBancaire pb6 = new ProduitBancaire(3,"num6",tp3);
            produitBancaireRepository.save(pb6);

            // Création de personnes physiques
            PersonnePhysique ppTest1 = new PersonnePhysique("adresse_pp1","nom_pp1","prenom_pp1");
            personnePhysiqueRepository.save(ppTest1);
            PersonnePhysique ppTest2 = new PersonnePhysique("adresse_pp2","nom_pp2","prenom_pp2");
            personnePhysiqueRepository.save(ppTest2);
            PersonnePhysique ppTest3 = new PersonnePhysique("adresse_pp3","nom_pp3","prenom_pp3");
            personnePhysiqueRepository.save(ppTest3);

            // Création de types de personnes morales
            TypePersonneMorale tpm1 = new TypePersonneMorale("SA", "Société Anonyme");
            typePersonneMoraleRepository.save(tpm1);
            TypePersonneMorale tpm2 = new TypePersonneMorale("SARL", "Société à Responsabilité Limitée");
            typePersonneMoraleRepository.save(tpm2);
            TypePersonneMorale tpm3 = new TypePersonneMorale("Auto Entrepreneur", "Statut d'auto-entrepreneur");
            typePersonneMoraleRepository.save(tpm3);

            // Création de personnes morales
            PersonneMorale pmTest1 = new PersonneMorale("pm1", "SIRET_pm1","raisonsoc_pm1", tpm1);
            personneMoraleRepository.save(pmTest1);
            PersonneMorale pmTest2 = new PersonneMorale("pm2", "SIRET_pm2","raisonsoc_pm2", tpm2);
            personneMoraleRepository.save(pmTest2);
            PersonneMorale pmTest3 = new PersonneMorale("pm3", "SIRET_pm3","raisonsoc_pm3", tpm1);
            personneMoraleRepository.save(pmTest3);

            // Création de clients bancaires
            ClientBancaire cb1 = new ClientBancaire();
            clientBancaireRepository.save(cb1);
            ClientBancaire cb2 = new ClientBancaire();
            clientBancaireRepository.save(cb2);
            ClientBancaire cb3 = new ClientBancaire();
            clientBancaireRepository.save(cb3);

            // Ajout des participants aux clients
            pmTest1 = personneMoraleRepository.findById(pmTest1.getId()).orElseThrow();
            cb1.addPersonne(pmTest1);
            clientBancaireRepository.save(cb1);

            cb1 = clientBancaireRepository.findById(cb1.getId()).orElseThrow();
            ppTest2 = personnePhysiqueRepository.findById(ppTest2.getId()).orElseThrow();
            cb1.addPersonne(ppTest2);
            clientBancaireRepository.save(cb1);

            ppTest1 = personnePhysiqueRepository.findById(ppTest1.getId()).orElseThrow();
            cb2 = clientBancaireRepository.findById(cb2.getId()).orElseThrow();
            ppTest1.addClientBancaire(cb2);
            personnePhysiqueRepository.save(ppTest1);

            cb3 = clientBancaireRepository.findById(cb3.getId()).orElseThrow();
            ppTest3 = personnePhysiqueRepository.findById(ppTest3.getId()).orElseThrow();
            cb3.addPersonne(ppTest3);
            clientBancaireRepository.save(cb3);

            System.out.println("***************************************************" +
                    "\nLes personnes physiques" +
                    "***********************************************************");
            System.out.println(personnePhysiqueRepository.findAll());

            System.out.println("***************************************************" +
                    "\nLes personnes morales" +
                    "***********************************************************");
            System.out.println(personneMoraleRepository.findAll());

            System.out.println("***************************************************" +
                    "\nLes clients bancaires" +
                    "***********************************************************");
            System.out.println(clientBancaireRepository.findAll());
            
            // ========== TEST : Modèle complet avec Opérations ==========
            System.out.println("\n" + "=".repeat(70));
            System.out.println("TEST : Modèle complet avec Opérations");
            System.out.println("=".repeat(70));
            
            // Création d'un nouveau client bancaire avec des produits et des opérations
            ClientBancaire clientComplet = new ClientBancaire();
            clientBancaireRepository.save(clientComplet);
            
            // Création d'un type de produit
            TypeProduit typeCompteCourant = new TypeProduit(0.5f, "Compte Courant", 25.0f);
            typeProduitRepository.save(typeCompteCourant);
            
            // Création d'un produit bancaire lié au client
            ProduitBancaire compteCourant = new ProduitBancaire(1000.0f, "CC001", typeCompteCourant, clientComplet);
            produitBancaireRepository.save(compteCourant);
            
            // Création d'opérations sur ce produit
            Operation operation1 = new Operation(
                Date.valueOf("2024-01-15"), 
                250.0f, 
                "DÉBIT", 
                "Achat en ligne", 
                compteCourant
            );
            Operation operation2 = new Operation(
                Date.valueOf("2024-01-16"), 
                1500.0f, 
                "CRÉDIT", 
                "Salaire", 
                compteCourant
            );
            Operation operation3 = new Operation(
                Date.valueOf("2024-01-17"), 
                75.0f, 
                "DÉBIT", 
                "Retrait DAB", 
                compteCourant
            );
            
            operationRepository.save(operation1);
            operationRepository.save(operation2);
            operationRepository.save(operation3);
            
            // Test des méthodes de recherche du repository Operation
            System.out.println("\n=== Test OperationRepository ===");
            System.out.println("Toutes les opérations :");
            System.out.println(operationRepository.findAll());
            
            System.out.println("\nOpérations par produit bancaire :");
            System.out.println(operationRepository.findByProduitBancaireId(compteCourant.getId()));
            
            System.out.println("\nOpérations de type DÉBIT :");
            System.out.println(operationRepository.findByType("DÉBIT"));
            
            System.out.println("\nOpérations avec montant > 100 :");
            System.out.println(operationRepository.findByMontantGreaterThan(100.0f));
            
            System.out.println("\nOpérations par client bancaire :");
            System.out.println(operationRepository.findByClientBancaireId(clientComplet.getId()));
            
            System.out.println("\n5 dernières opérations :");
            System.out.println(operationRepository.findFirst5ByOrderByDateOperationDesc());
            
            // Test de la relation complète
            System.out.println("\n=== Test relation complète ===");
            System.out.println("Client avec ses produits et opérations :");
            System.out.println(clientComplet);
            System.out.println("Produit bancaire avec ses opérations :");
            System.out.println(compteCourant);
            
            // Affichage final
            System.out.println("\n" + "=".repeat(70));
            System.out.println("RÉSUMÉ FINAL - TOUTES LES ENTITÉS");
            System.out.println("=".repeat(70));
            System.out.println("Personnes physiques : " + personnePhysiqueRepository.count());
            System.out.println("Personnes morales : " + personneMoraleRepository.count());
            System.out.println("Types de personnes morales : " + typePersonneMoraleRepository.count());
            System.out.println("Clients bancaires : " + clientBancaireRepository.count());
            System.out.println("Types de produits : " + typeProduitRepository.count());
            System.out.println("Produits bancaires : " + produitBancaireRepository.count());
            System.out.println("Opérations : " + operationRepository.count());
        };
    }
}