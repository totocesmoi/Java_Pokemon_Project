package Tests;

import Competences.CollectionCompetence;
import Competences.Competence;
import Monstres.CollectionMonstres;
import Monstres.Monstre;
import Monstres.MonstreVM;

public class TestsMonstre {
    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS JEU ===");

        testChargementMonstres();

        System.out.println("=== FIN DES TESTS ===");
    }

    public static void testChargementMonstres() {
        System.out.println("Début du test de chargement des monstres...");
        
        CollectionMonstres collection = new CollectionMonstres();
        
        // Le fichier est dans le package Monstres
        collection.load("./Monstres/Monstres.txt");
        
        if (collection.monstres != null) {
            System.out.println("Nombre de monstres chargés : " + collection.monstres.size());
            for (MonstreVM m : collection.monstres) {
                System.out.println(m);
            }
        } else {
            System.out.println("Erreur : La collection est vide ou nulle.");
        }
        
        System.out.println("Fin du test.");
    }
}
