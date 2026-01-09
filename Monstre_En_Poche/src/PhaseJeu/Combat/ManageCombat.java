package PhaseJeu.Combat;

import Joueurs.Joueur;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageCombat {
    private Scanner scanner;
    public List<Joueur> playersInCombat;
    public String gameMode = "PVE"; // PVE or PVP

    public ManageCombat() {
        this.scanner = new Scanner(System.in);
        this.playersInCombat = new ArrayList<>();
    }

    public void CombatSettings (List<Joueur> players) {
        System.out.println("\n--- Combat Settings ---");
        System.out.println("1. Player vs Environment (PVE IA)");
        System.out.println("2. Player vs Player (PVP)");
        System.out.println("3. Back");
        System.out.println("Choice : ");

        String choix = scanner.nextLine();
        System.out.print("\033[H\033[2J");

        switch (choix) {
            case "1":
                this.gameMode = "PVE";
                System.out.println("\n--- Choose Players ---");
                System.out.println("Which player will you play ?");

                for (int i = 0; i < players.size(); i++) {
                    System.out.println((i + 1) + ". " + players.get(i).getName());
                }
                System.out.print("Choice : ");
                Joueur p1 = players.get(Integer.parseInt(scanner.nextLine()) - 1);
                this.playersInCombat.add(p1);
                System.out.println(p1.getName() + " will face the IA." + playersInCombat.size());
                // playersInCombat.add(players.get(Integer.parseInt(scanner.nextLine()) - 1));
                break;
            case "2":
                this.gameMode = "PVP";
                System.out.println("\n--- Choose Players ---");
                System.out.println("Which player will you play ?");

                for (int i = 0; i < players.size(); i++) {
                    System.out.println((i + 1) + ". " + players.get(i).getName());
                }

                System.out.print("Choice : ");
                int p1choice = Integer.parseInt(scanner.nextLine()) - 1;
                playersInCombat.add(players.get(p1choice));

                System.out.println("\n--- Choose Players ---");
                System.out.println("Which player will opponent play ?");

                for (int i = 0; i < players.size(); i++) {
                    if (i == p1choice) continue;
                    System.out.println((i + 1) + ". " + players.get(i).getName());
                }
                System.out.print("Choice : ");
                playersInCombat.add(players.get(Integer.parseInt(scanner.nextLine()) - 1));
                break;
            case "3":
                break;
        }
    }
}
