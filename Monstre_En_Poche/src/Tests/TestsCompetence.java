package Tests;

import Competences.CollectionCompetence;
import Competences.Competence;

public class TestsCompetence {
    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS JEU ===");

        testChargementCompetence();

        System.out.println("=== FIN DES TESTS ===");
    }


    public static void testChargementCompetence() {
        System.out.println("Début du test de chargement des compétences...");

        CollectionCompetence collection = new CollectionCompetence();
        collection.load("./Competences/Competences.txt");

        if (collection.competences != null) {
            System.out.println("Nombre de compétences chargés : " + collection.competences.size());
            for (Competence c : collection.competences) {
                System.out.println(c.toString() + "\n---");
            }
        } else {
            System.out.println("Erreur : La collection est vide ou nulle.");
        }

        System.out.println("Fin du test.");
    }
}
