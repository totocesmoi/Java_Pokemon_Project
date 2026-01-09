package Tests;

import Objets.CollectionObjets;
import Objets.Object;

public class TestsObjetsParser {
    public static void main(String[] args) {
        System.out.println("=== DEBUT TEST CHARGEMENT OBJETS ===");

        CollectionObjets collection = new CollectionObjets();
        collection.load("src/Objets/Objets.txt");

        if (collection.objets != null) {
            System.out.println("Nombre d'objets chargés : " + collection.objets.size());
            for (Object obj : collection.objets) {
                System.out.println(obj.toString());
                System.out.println("---");
            }
        } else {
            System.out.println("Erreur : Collection vide.");
        }

        System.out.println("=== FIN DU TEST ===");
    }
}
