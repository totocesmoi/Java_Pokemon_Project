package Tests;

import Competences.CollectionCompetence;
import Competences.Competence;
import Joueurs.Joueur;
import Monstres.CollectionMonstres;
import Monstres.Monstre;
import Monstres.MonstreVM;
import Objets.CollectionObjets;
import Objets.Object;
import Shared.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestJoueur {

    // Collections statiques pour les tests
    private static List<MonstreVM> allMonsters;
    private static List<Competence> allSkills;
    private static List<Object> allObjects;

    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS JEU ===");

        // Chargement des données
        loadData();

        if(allMonsters.isEmpty() || allSkills.isEmpty() || allObjects.isEmpty()) {
            System.out.println("Erreur: Données manquantes. Vérifiez les fichiers .txt");
            return;
        }

        testLimiteMonstres();
        testLimiteObjets();
        testValiditeCompetences();
        afficherEquipeTest();
        testAffichageTroisMonstres();

        System.out.println("=== FIN DES TESTS ===");
    }

    private static void loadData() {
        CollectionMonstres colMonstres = new CollectionMonstres();
        colMonstres.load("src/Monstres/Monstres.txt");
        allMonsters = colMonstres.monstres;

        CollectionCompetence colSkills = new CollectionCompetence();
        colSkills.load("src/Competences/Competences.txt");
        allSkills = colSkills.competences;

        CollectionObjets colObjects = new CollectionObjets();
        colObjects.load("src/Objets/Objets.txt");
        allObjects = colObjects.objets;

        System.out.println("Données chargées: " + allMonsters.size() + " monstres, " + allSkills.size() + " skills, " + allObjects.size() + " objets.");
    }

    /*
     * Test 1: Ajouter un monstre et vérifier la limite de 3.
     */
    public static void testLimiteMonstres() {
        System.out.println("\n--- Test Limite Monstres (Max 3) ---");
        Joueur j = new Joueur("Sacha");

        // Création de 4 monstres à partir des données chargées
        // On prend les 4 premiers par exemple
        Monstre m1 = createMonsterFromVM(allMonsters.get(0));
        Monstre m2 = createMonsterFromVM(allMonsters.get(1));
        Monstre m3 = createMonsterFromVM(allMonsters.get(2));
        Monstre m4 = createMonsterFromVM(allMonsters.get(3));

        System.out.println("Ajout M1 (" + m1.getName() + "): " + j.addMonster(m1));
        System.out.println("Ajout M2 (" + m2.getName() + "): " + j.addMonster(m2));
        System.out.println("Ajout M3 (" + m3.getName() + "): " + j.addMonster(m3));

        // Ce dernier doit échouer
        boolean reussite = j.addMonster(m4);
        System.out.println("Ajout M4 (" + m4.getName() + ") (Devrait échouer): " + reussite);

        if (!reussite && j.getTeam().size() == 3) {
            System.out.println(">> SUCCES: Limite respectée.");
        } else {
            System.out.println(">> ECHEC: Limite non respectée.");
        }
    }

    /*
     * Test 2: Ajouter des objets et vérifier la limite de 5.
     */
    public static void testLimiteObjets() {
        System.out.println("\n--- Test Limite Objets (Max 5) ---");
        Joueur j = new Joueur("Ondine");

        Object objetTest = allObjects.get(0); // On prend le premier objet (ex: Potion)
        System.out.println("Objet de test: " + objetTest.getName());

        for(int i=1; i<=5; i++) {
            System.out.println("Ajout Objet " + i + ": " + j.ajouterObjet(objetTest));
        }

        // 6ème objet doit échouer
        boolean reussite = j.ajouterObjet(objetTest);
        System.out.println("Ajout Objet 6 (Devrait échouer): " + reussite);

        if (!reussite) {
            System.out.println(">> SUCCES: Limite respectée.");
        } else {
            System.out.println(">> ECHEC: Limite non respectée.");
        }
    }

    /*
     * Test 3: Choisir les compétences (Max 4, Type correct)
     */
    public static void testValiditeCompetences() {
        System.out.println("\n--- Test Validité Compétences ---");

        // On cherche un Monstre FEU (Salameche) ou on en prend un et on s'adapte
        Monstre monstreTest = null;
        for(MonstreVM vm : allMonsters) {
            if(vm.getType() == Types.FEU) {
                monstreTest = createMonsterFromVM(vm);
                break;
            }
        }
        if(monstreTest == null) monstreTest = createMonsterFromVM(allMonsters.get(0)); // fallback

        System.out.println("Monstre test: " + monstreTest.getName() + " (" + monstreTest.getType() + ")");

        // Chercher des compétences spécifique
        Competence cType = findSkillByType(monstreTest.getType());
        Competence cNormal = findSkillByType(Types.NORMAL);
        Competence cIncompatible = findSkillByType(getIncompatibleType(monstreTest.getType()));

        if(cType == null || cNormal == null || cIncompatible == null) {
            System.out.println("Pas assez de compétence pour le test. (Besoin Type, Normal, Incompatible)");
            return;
        }

        System.out.println("Test Types:");
        System.out.println("- Ajout Competence Meme Type (" + cType.getName() + ") : " + monstreTest.ajouterCompetence(cType));
        System.out.println("- Ajout Competence Normal (" + cNormal.getName() + ") : " + monstreTest.ajouterCompetence(cNormal));
        boolean testInvalide = monstreTest.ajouterCompetence(cIncompatible);
        System.out.println("- Ajout Competence Incompatible (" + cIncompatible.getName() + ") (Devrait échouer) : " + testInvalide);

        if (!testInvalide) {
            System.out.println(">> SUCCES: Type invalide rejeté.");
        } else {
            System.out.println(">> ECHEC: Type invalide accepté.");
        }

        System.out.println("\nTest Limite (Max 4):");
        // On a deja 2 competences (si type != normal). Ajoutons en jusqu'à 4.
        while(monstreTest.getCompetences().size() < 4) {
            monstreTest.ajouterCompetence(cType); // On peut ajouter la meme pour le test de size
        }

        // Maintenant on en a 4. Essayons une 5ème.
        boolean testLimite = monstreTest.ajouterCompetence(cNormal);
        System.out.println("Ajout 5ème compétence (Devrait échouer) : " + testLimite);

        if (!testLimite) {
            System.out.println(">> SUCCES: Limite de compétences respectée.");
        } else {
            System.out.println(">> ECHEC: Limite dépassée.");
        }
    }

    /*
     * Test 4: Afficher l'équipe
     */
    public static void afficherEquipeTest() {
        System.out.println("\n--- Test Affichage Équipe ---");
        Joueur j = new Joueur("Pierre");
        Monstre m = createMonsterFromVM(allMonsters.get(allMonsters.size()-1)); // Le dernier (ex: Racaillou)

        Competence c = findSkillByType(Types.NORMAL);
        if(c != null) m.ajouterCompetence(c);

        j.addMonster(m);

        // Simuler l'affichage
        System.out.println("Joueur: " + j.getName());
        for(Monstre monstre : j.getTeam()) {
            System.out.println("Monstre: " + monstre.getName() + " | PV: " + monstre.getPtnVie());
            System.out.print("Attaques: ");
            for(Competence comp : monstre.competences) {
                System.out.print(comp.getName() + " ");
            }
            System.out.println();
        }
    }

    /*
     * Test 5: Test supplémentaire avec 3 monstres
     */
    public static void testAffichageTroisMonstres() {
        System.out.println("\n--- Test Affichage 3 Monstres (Max Compétences) ---");
        Joueur j = new Joueur("Red");

        // Prendre 3 monstres différents si possible
        Monstre m1 = createMonsterFromVM(allMonsters.get(0));
        Monstre m2 = createMonsterFromVM(allMonsters.get(1 % allMonsters.size()));
        Monstre m3 = createMonsterFromVM(allMonsters.get(2 % allMonsters.size()));

        fillCompetences(m1);
        fillCompetences(m2);
        fillCompetences(m3);

        j.addMonster(m1);
        j.addMonster(m2);
        j.addMonster(m3);

        System.out.println("Joueur: " + j.getName() + " possède " + j.getTeam().size() + " monstres.");
        for(Monstre monstre : j.getTeam()) {
            System.out.println("- " + monstre.getName() + " [" + monstre.getType() + "]");
            System.out.print("  > Attaques: ");
            for(Competence c : monstre.getCompetences()) {
                System.out.print(c.getName() + " ");
            }
            System.out.println();
        }
    }

    // --- Helpers ---

    private static void fillCompetences(Monstre m) {
        // Remplir avec des compétences valides chargées
        int count = 0;
        for(Competence c : allSkills) {
            if(count >= 4) break;
            if(m.peutApprendre(c)) {
                m.ajouterCompetence(c);
                count++;
            }
        }
    }

    private static Monstre createMonsterFromVM(MonstreVM vm) {
        // Création simple en prenant les stats Max pour le test
        return new Monstre(
                vm.getName(),
                vm.getType(),
                vm.getHpMax(),
                vm.getAttackMax(),
                vm.getAttackSpeMax(),
                vm.getDefenseMax(),
                vm.getDefenseSpeMax(),
                vm.getSpeedMax(),
                new ArrayList<>()
        );
    }

    private static Competence findSkillByType(Types t) {
        for(Competence c : allSkills) {
            if(c.getType() == t) return c;
        }
        return null;
    }

    private static Types getIncompatibleType(Types t) {
        // Retourne un type différent de t et différent de NORMAL
        for(Types type : Types.values()) {
            if(type != t && type != Types.NORMAL && type.getFamilleId() != t.getFamilleId()) {
                return type;
            }
        }
        return (t == Types.EAU) ? Types.FEU : Types.EAU; // Fallback simple
    }
}