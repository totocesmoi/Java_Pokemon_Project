import Competences.CollectionCompetence;
import Joueurs.Joueur;
import Monstres.CollectionMonstres;
import PhaseJeu.Combat.ManageCombat;
import PhaseJeu.Combat.PhaseCombat;
import PhaseJeu.Preparation.ManageTeam;
import java.util.ArrayList;
import java.util.Scanner;

// javac ./Main.java && java Main
public class Main {
    public static void main(String[] args) {
        // TestsMonstre tests = new TestsMonstre();
        // tests.testChargementMonstres();

        // TestsCompetence testsCompetence = new TestsCompetence();
        // testsCompetence.testChargementCompetence();

        CollectionCompetence collectionCompetence = new CollectionCompetence();
        collectionCompetence.load("./Competences/Competences.txt");

        CollectionMonstres collectionMonstres = new CollectionMonstres();
        collectionMonstres.load("./Monstres/Monstres.txt");

        // Menu de gestion
        int startMenu = 1;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Joueur> players = new ArrayList<>();
        ManageTeam teamManager = new ManageTeam(collectionMonstres, collectionCompetence);
        ManageCombat combatManager = new ManageCombat();

        while (startMenu != 4) {
            System.out.print("\033[H\033[2J");
            System.out.println("\n--------------------------------------------------\n");
            System.out.println("1. Team Management");
            System.out.println("2. Combat Settings");
            System.out.println("3. Launch Game");
            System.out.println("4. Exit");
            System.out.println("\nChoice : ");
            
            String choix = scanner.nextLine();
            System.out.print("\033[H\033[2J");
            switch (choix) {
                case "1":
                    if (!players.isEmpty()) {
                        System.out.println("Which player do you want to manage?");
                        for (int i = 0; i < players.size(); i++) {
                            System.out.println((i + 1) + ". " + players.get(i).getName());
                        }
                        System.out.println((players.size() + 1) + ". Create new player");
                        System.out.println((players.size() + 2) + ". Back");
                        System.out.println("\nChoice : ");
                        String playerChoice = scanner.nextLine();
                        System.out.print("\033[H\033[2J");
                        
                        if (playerChoice.equals(String.valueOf(players.size() + 1))) {
                            Joueur joueur = new Joueur("P" + (players.size() + 1));
                            players.add(joueur);
                            teamManager.menuGestionEquipe(joueur); // Pass a Joueur object here
                            break;
                        }

                        if (playerChoice.equals(String.valueOf(players.size() + 2))) break;
                        teamManager.menuGestionEquipe(players.get(Integer.parseInt(playerChoice)));
                    }
                    else {
                        Joueur joueur = new Joueur("P" + (players.size() + 1));
                        players.add(joueur);
                        teamManager.menuGestionEquipe(joueur); // Pass a Joueur object here
                    }
                    break;
                case "2":
                    // Combat Settings
                    combatManager.CombatSettings(players);
                    break;
                case "3":
                    // Launch Game
                    PhaseCombat combat;
                    System.out.println("Launching game in " + combatManager.gameMode + " mode... " + combatManager.playersInCombat.size() + " players selected.");
                    if (combatManager.gameMode.equals( "PVE") && combatManager.playersInCombat.size() == 1) {
                        combat = new PhaseCombat(
                            combatManager.playersInCombat.get(0),
                            combatManager.playersInCombat.get(0) // IA
                        );
                        combat.combat();
                        break;
                    }
                    
                    if (combatManager.gameMode.equals("PVP") && combatManager.playersInCombat.size() == 2) {
                        combat = new PhaseCombat(
                            combatManager.playersInCombat.get(0),
                            combatManager.playersInCombat.get(1)
                        );
                        combat.combat();
                        break;
                    }

                    System.out.println("Combat cannot start. Check players selection and game mode.");
                    break;
                case "4":
                    startMenu = 4;
                    break;
                default:
                    System.out.println("Invalide choice.");
            }
        }



        
        /*
        System.out.println("\n--------------------------------------------------\n");

        TestsObjects testsObjects = new TestsObjects();
        testsObjects.testObjets();
        */
    }
}