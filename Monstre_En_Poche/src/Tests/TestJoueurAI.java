package Tests;

import Competences.Competence;
import Competences.Enum.Categories;
import Joueurs.IAExpert;
import Joueurs.IARandom;
import Joueurs.Joueur;
import Monstres.Monstre;
import Monstres.MonstreVM;
import Shared.Effects;
import Shared.Types;
import java.util.ArrayList;
import java.util.List;

public class TestJoueurAI {

    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS IA ===");

        // 1. Préparation des données factices (Mocking)
        List<MonstreVM> mockMonsters = createMockMonsters();
        List<Competence> mockSkills = createMockSkills();

        // 2. Test IARandom
        testIARandom(mockMonsters, mockSkills);

        // 3. Test IAExpert
        testIAExpert(mockMonsters, mockSkills);

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

    // --- Helpers pour créer des données de test ---

    private static List<MonstreVM> createMockMonsters() {
        List<MonstreVM> list = new ArrayList<>();
        // Basé sur Monstres.txt
        
        // Pikachu (Electric -> FOUDRE)
        list.add(new MonstreVM("Pikachu", Types.FOUDRE, "Special", 110, 141, 75, 106, 75, 106, 50, 82, 50, 82, 110, 141));
        
        // Bulbizarre (Plante -> PLANTE)
        list.add(new MonstreVM("Bulbizarre", Types.PLANTE, "Physique", 45, 60, 49, 65, 49, 65, 49, 65, 49, 65, 45, 60));
        
        return list;
    }

    private static List<Competence> createMockSkills() {
        List<Competence> list = new ArrayList<>();
        // Basé sur Competences.txt

        // Flammeche: FEU, Spe, 40, Acc 100, PP 25, Effect Burned, Rate 50
        list.add(createSkill("Flammeche", Types.FEU, 40, Categories.SPECIAL, 25, 100, Effects.BURNED, 50));

        // Hydrocanon: EAU, Spe, 110, Acc 80, PP 5
        list.add(createSkill("Hydrocanon", Types.EAU, 110, Categories.SPECIAL, 5, 80, Effects.NONE, 0));

        // Pistolet a Eau: EAU, Spe, 30, Acc 100, PP 30
        list.add(createSkill("Pistolet a Eau", Types.EAU, 30, Categories.SPECIAL, 30, 100, Effects.NONE, 0));

        return list;
    }

    private static Competence createSkill(String name, Types type, int power, Categories cat, int pp, int accuracy, Effects effect, int rate) {
        Competence c = new Competence();
        c.setName(name);
        c.setType(type);
        c.setPower(power);
        c.setCategory(cat);
        c.setAccuracy(accuracy);
        c.setPP(pp);
        c.setEffect(effect);
        c.setEffectRate(rate);
        return c;
    }
}
