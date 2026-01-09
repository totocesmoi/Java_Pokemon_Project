package Tests;

import Competences.CollectionCompetence;
import Competences.Competence;
import Competences.Enum.Categories;
import Joueurs.IAExpert;
import Joueurs.IARandom;
import Joueurs.Joueur;
import Monstres.CollectionMonstres;
import Monstres.Monstre;
import Monstres.MonstreVM;
import Shared.Effects;
import Shared.Types;
import java.util.ArrayList;
import java.util.List;

public class TestJoueurAI {

    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS IA ===");

        // 1. Chargement des données réelles
        CollectionMonstres colMonstres = new CollectionMonstres();
        colMonstres.load("src/Monstres/Monstres.txt");
        List<MonstreVM> loadedMonsters = colMonstres.monstres;

        CollectionCompetence colCompetences = new CollectionCompetence();
        colCompetences.load("src/Competences/Competences.txt");
        List<Competence> loadedSkills = colCompetences.competences;

        if (loadedMonsters == null || loadedMonsters.isEmpty() || loadedSkills == null || loadedSkills.isEmpty()) {
            System.out.println("Erreur: Impossible de charger les données pour le test.");
            return;
        }

        System.out.println("Données chargées : " + loadedMonsters.size() + " monstres, " + loadedSkills.size() + " compétences.");

        // 2. Test IARandom
        testIARandom(loadedMonsters, loadedSkills);

        // 3. Test IAExpert
        testIAExpert(loadedMonsters, loadedSkills);

        System.out.println("=== FIN DES TESTS IA ===");
    }

    private static void testIARandom(List<MonstreVM> monsters, List<Competence> skills) {
        System.out.println("\n--- Test IA Random ---");
        
        // Instanciation
        IARandom ia = new IARandom("Robot-Random", monsters, skills);
        System.out.println("IA créée : " + ia.getName());

        // Vérification de l'équipe
        checkTeam(ia);

        // Test de prise de décision
        Monstre attaquant = ia.getActifMonster();
        // Cible pour tester
        Monstre cible = new Monstre("Poutchichou", Types.NORMAL, "Dummy", 100, 10, 10, 10, 10, 10, new ArrayList<>());
        
        System.out.println("Monstre actif IA : " + attaquant.getName() + " (" + attaquant.getType() + ")");
        Competence choix = ia.choisirCompetence(cible);
        
        if (choix != null) {
            System.out.println(">> SUCCES: L'IA a choisi l'attaque : " + choix.getName());
        } else {
            System.out.println(">> ECHEC: L'IA n'a pas choisi d'attaque (ou pas de compétences ?)");
        }
    }

    private static void testIAExpert(List<MonstreVM> monsters, List<Competence> skills) {
        System.out.println("\n--- Test IA Expert (Stratégique) ---");
        
        IAExpert ia = new IAExpert("Robot-Expert", monsters, skills);
        checkTeam(ia);

        Monstre attaquant = ia.getActifMonster();
        // Créons une cible spécifique pour tester l'intelligence
        // Si l'IA a un monstre EAU, mettons une cible FEU pour voir si elle tape EAU.
        Monstre cible = new Monstre("Cible-Test", Types.FEU, "Dummy", 500, 0, 0, 0, 0, 0, new ArrayList<>());
        
        System.out.println("Monstre actif IA : " + attaquant.getName() + " (" + attaquant.getType() + ")");
        System.out.println("Cible : " + cible.getName() + " (" + cible.getType() + ")");
        
        // Affichons les compétences disponibles pour comprendre le choix
        System.out.print("Compétences dispo: ");
        for(Competence c : attaquant.getCompetences()) {
            System.out.print(c.getName() + "[" + c.getType() + "] ");
        }
        System.out.println();

        Competence choix = ia.choisirCompetence(cible);
        
        if (choix != null) {
            System.out.println(">> L'IA a choisi : " + choix.getName());
        } else {
            System.out.println(">> ECHEC: Pas de choix.");
        }
    }

    private static void checkTeam(Joueur j) {
        if (j.getTeam().size() == 3) {
            System.out.println(">> SUCCES: L'équipe contient bien 3 monstres.");
        } else {
            System.out.println(">> ECHEC: Taille équipe incorrecte (" + j.getTeam().size() + ")");
        }

        // Vérification des compétences
        boolean skillsOk = true;
        for (Monstre m : j.getTeam()) {
            if (m.getCompetences().isEmpty()) {
                skillsOk = false;
                System.out.println("  -> Attention: Le monstre " + m.getName() + " n'a pas de compétences !");
            }
        }
        if (skillsOk) {
            System.out.println(">> SUCCES: Tous les monstres ont des compétences.");
        }
    }
}