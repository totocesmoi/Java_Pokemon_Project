package Tests;

import Competences.CollectionCompetence;
import Competences.Competence;
import Monstres.CollectionMonstres;
import Monstres.MonstreVM;

public class TestsCompetence {
    public void testChargementCompetence() {
        System.out.println("Début du test de chargement des compétences...");

        CollectionCompetence collection = new CollectionCompetence();
        collection.load("src/Competences/Competences.txt");

        if (collection.competences != null) {
            System.out.println("Nombre de compétences chargés : " + collection.competences.size());
            for (Competence c : collection.competences) {
                System.out.println(c + "\n---");
            }
        } else {
            System.out.println("Erreur : La collection est vide ou nulle.");
        }

        System.out.println("Fin du test.");
    }
}
