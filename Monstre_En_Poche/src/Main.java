import java.util.ArrayList;
import java.util.Collection;

import Competences.CollectionCompetence;
import Competences.Competence;
import Monstres.Monstre;
import Shared.Types;
import Tests.*;

// javac ./Main.java && java Main
public class Main {
    public static void main(String[] args) {
        // TestsMonstre tests = new TestsMonstre();
        // tests.testChargementMonstres();

        // TestsCompetence testsCompetence = new TestsCompetence();
        // testsCompetence.testChargementCompetence();


        // Pour tester les degats + effets (1 seul round)
        Monstre Noann = new Monstre("Noann", Types.FEU, "Test", 100, 50, 50, 50, 50, 50, new ArrayList<>());
        Monstre Thomas = new Monstre("Thomas", Types.EAU, "Test", 100, 50, 50, 50, 50, 50, new ArrayList<>());

        CollectionCompetence collectionCompetence = new CollectionCompetence();
        collectionCompetence.load("./Competences/competences.txt");

        Competence attaque = collectionCompetence.competences.get(0);

        Noann.attackMonster(Thomas, attaque);
        
        
        /*
        System.out.println("\n--------------------------------------------------\n");

        TestsObjects testsObjects = new TestsObjects();
        testsObjects.testObjets();
        */
    }
}