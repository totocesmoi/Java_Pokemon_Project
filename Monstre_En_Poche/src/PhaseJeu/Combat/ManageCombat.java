package PhaseJeu.Combat;

import Joueurs.Joueur;
import Shared.ReadInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageCombat {
    private Scanner scanner;
    public List<Joueur> playersInCombat;
    public String gameMode = "PVE"; // PVE or PVP
    public String iaType = "Random"; // Random or Expert

    public ManageCombat() {
        this.scanner = new Scanner(System.in);
        this.playersInCombat = new ArrayList<>();
    }

    // ANSI styling
    private final String ANSI_BOLD = "\u001B[1m";
    private final String ANSI_UNDERLINE = "\u001B[4m";
    private final String ANSI_RESET = "\u001B[0m";

    private void printSeparator() {
        System.out.println(ANSI_BOLD + "==============================================" + ANSI_RESET);
    }

    public void CombatSettings (List<Joueur> players) {
        printSeparator();
        System.out.println(ANSI_BOLD + "         Combat Settings" + ANSI_RESET);
        System.out.println("1. Player vs Environment (PVE IA)");
        System.out.println("2. Player vs Player (PVP)");
        System.out.println("3. Back");
        System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");

        String choix = scanner.nextLine();
        System.out.print("\033[H\033[2J");

        printSeparator();
        System.out.println(ANSI_BOLD + "         Choose Players" + ANSI_RESET);
        switch (choix) {
            case "1":
                this.gameMode = "PVE";
                
                printSeparator();
                System.out.println(ANSI_BOLD + "         Choose AI Difficulty" + ANSI_RESET);
                System.out.println("1. Random (Easy)");
                System.out.println("2. Expert (Hard)");
                System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");
                int iaChoice = ReadInt.readInt(scanner, 1, 2);
                this.iaType = (iaChoice == 1) ? "Random" : "Expert";

                System.out.println("Which player will you play ?");

                for (int i = 0; i < players.size(); i++) {
                    System.out.println((i + 1) + ". " + players.get(i).getName());
                }
                System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");
                int choice = ReadInt.readInt(scanner, 1, players.size()) - 1;
                Joueur p1 = players.get(choice);
                this.playersInCombat.add(p1);
                System.out.println(p1.getName() + " will face the " + this.iaType + " IA.");
                break;
            case "2":
                this.gameMode = "PVP";
                System.out.println("Which player will you play ?");

                for (int i = 0; i < players.size(); i++) {
                    System.out.println((i + 1) + ". " + players.get(i).getName());
                }

                System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");
                int p1choice = ReadInt.readInt(scanner, 1, players.size()) - 1;
                playersInCombat.add(players.get(p1choice));

                printSeparator();
                System.out.println(ANSI_BOLD + "         Choose Players" + ANSI_RESET);
                System.out.println("Which player will opponent play ?");

                for (int i = 0; i < players.size(); i++) {
                    if (i == p1choice) continue;
                    System.out.println((i + 1) + ". " + players.get(i).getName());
                }
                System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");
                int p2choice = ReadInt.readInt(scanner, 1, players.size()) - 1;
                if (p2choice == p1choice) {
                    System.out.println("Cannot select the same player as opponent.");
                } else {
                    playersInCombat.add(players.get(p2choice));
                }
                break;
            case "3":
                break;
        }
    }
}
