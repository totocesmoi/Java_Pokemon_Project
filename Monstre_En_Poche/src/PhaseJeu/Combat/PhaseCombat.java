package PhaseJeu.Combat;
import Competences.Competence;
import Joueurs.IA;
import Joueurs.Joueur;
import Monstres.Monstre;
import Shared.ReadInt;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PhaseCombat {
    int turn;
    Joueur player1;
    Joueur player2;
    
    public PhaseCombat(Joueur p1, Joueur p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.turn = 0;
    }

    private IAction selectAction(Joueur player1, Joueur player2, Scanner scanner) {
        // AI Logic
        if (player1 instanceof IA) {
            // System.out.println(player1.getName() + " (IA) is choosing move...");
            try {
                Thread.sleep(1000); // Petit délai pour le réalisme
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Competence c = ((IA) player1).choisirCompetence(player2.getActifMonster());
            return new AttackAction(player1.getActifMonster(), player2, c);
        }

        System.out.println(player1.getName() + ", choisissez une action pour " + player1.getActifMonster().getName() + " :");
        System.out.println("1. Attaquer");
        System.out.println("2. Changer de monstre");
        System.out.println("3. Utiliser un objet");
        System.out.println("4. Declarer forfait");

        String choix = scanner.nextLine();
        switch (choix) {
            case "1":
                if (player1.getActifMonster().getCompetences().isEmpty()) {
                    System.out.println("Attaque a main nue.");
                    return new AttackAction(player1.getActifMonster(), player2, null);
                }
                System.out.println("Attaques disponibles :");
                for (int i = 0; i < player1.getActifMonster().getCompetences().size(); i++) {
                    System.out.println((i + 1) + ". " + player1.getActifMonster().getCompetences().get(i).getName());
                }
                System.out.print("Choisissez une attaque : ");
                int attaqueChoisie = ReadInt.readInt(scanner, 1, player1.getActifMonster().getCompetences().size()) - 1;
                return new AttackAction(player1.getActifMonster(), player2, player1.getActifMonster().getCompetences().get(attaqueChoisie));
            case "2":
                // Changer de monstre
                System.out.println("Monstres disponibles :");
                for (int i = 0; i < player1.getTeam().size(); i++) {
                    if (player1.getTeam().get(i).isKO()) {
                        continue;
                    }
                    System.out.println((i + 1) + ". " + player1.getTeam().get(i).getName());
                }
                System.out.print("Choisissez un monstre : ");
                int monstreChoisi;
                while (true) {
                    monstreChoisi = ReadInt.readInt(scanner, 1, player1.getTeam().size()) - 1;
                    if (!player1.getTeam().get(monstreChoisi).isKO()) break;
                    System.out.println("Monstre KO, choisissez-en un autre.");
                }
                return new SwitchAction(player1, monstreChoisi);
            case "3":
                // Utiliser un objet
                System.out.println("Objets disponibles :");
                for (int i = 0; i < player1.getBag().size(); i++) {
                    System.out.println((i + 1) + ". " + player1.getBag().get(i).getName());
                }
                if (player1.getBag().isEmpty()) {
                    System.out.println("Aucun objet disponible.");
                    return selectAction(player1, player2, scanner);
                }
                System.out.print("Choisissez un objet : ");
                int objetChoisi = ReadInt.readInt(scanner, 1, player1.getBag().size()) - 1;
                return new ItemAction(player1, objetChoisi);
            case "4":
                // Declarer forfait
                System.out.println(player1.getName() + " a declare forfait. " + player2.getName() + " gagne le combat !");
                System.exit(0);
            default:
                System.out.println("Choix invalide, veuillez reessayer.");
                return selectAction(player1, player2, scanner);

        }
    }

    private void handleKOSwitch(Joueur player, Scanner scanner) {
        if (!player.getActifMonster().isKO()) return;

        if (player instanceof IA) {
            for(int i=0; i<player.getTeam().size(); i++) {
                if (!player.getTeam().get(i).isKO()) {
                    player.changeMonster(i);
                    return;
                }
            }
        }
        
        System.out.println(player.getName() + ", choisissez un nouveau monstre :");
        for (int i = 0; i < player.getTeam().size(); i++) {
           if (player.getTeam().get(i).isKO()) {
               continue;
           }
           System.out.println((i + 1) + ". " + player.getTeam().get(i).getName());
       }
       System.out.print("Choisissez un monstre : ");
       int monstreChoisi;
       while (true) {
           monstreChoisi = ReadInt.readInt(scanner, 1, player.getTeam().size()) - 1;
           if (!player.getTeam().get(monstreChoisi).isKO()) break;
           System.out.println("Monstre KO, choisissez-en un autre.");
       }
       player.changeMonster(monstreChoisi);
   }

    public void checkInteruption (Scanner scanner) {
        if (player1.isDefeated()) {
            System.out.println(player1.getName() + " n'a plus de monstres en vie. " + player2.getName() + " gagne le combat !");
            System.exit(0);
        }
        if (player2.isDefeated()) {
            System.out.println(player2.getName() + " n'a plus de monstres en vie. " + player1.getName() + " gagne le combat !");
            System.exit(0);
        }

        handleKOSwitch(player1, scanner);
        handleKOSwitch(player2, scanner);
    }

    // --- Affichage amélioré (ANSI) ---
    private final String ANSI_BOLD = "\u001B[1m";
    private final String ANSI_UNDERLINE = "\u001B[4m";
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_RED = "\u001B[31m";

    private void printSeparator() {
        System.out.println(ANSI_BOLD + "==============================================" + ANSI_RESET);
    }

    private String renderHpBar(int current, int max, int length) {
        if (max <= 0) max = 1;
        double ratio = (double) current / max;
        int filled = (int) Math.round(ratio * length);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < length; i++) {
            if (i < filled) sb.append("#"); else sb.append("-");
        }
        sb.append("]");
        String color = ANSI_GREEN;
        if (ratio < 0.33) color = ANSI_RED;
        else if (ratio < 0.66) color = ANSI_YELLOW;
        return color + sb.toString() + ANSI_RESET;
    }

    private void printBattleHUD(Joueur viewer, Joueur opponent, Map<Monstre, Integer> maxHp) {
        Monstre me = viewer.getActifMonster();
        Monstre opp = opponent.getActifMonster();
        int oppMax = maxHp.getOrDefault(opp, opp.getPtnVie());
        int meMax = maxHp.getOrDefault(me, me.getPtnVie());

        printSeparator();
        System.out.println(ANSI_BOLD + "Joueur: " + viewer.getName() + ANSI_RESET + "  |  " + ANSI_UNDERLINE + "Tour: " + turn + ANSI_RESET);
        System.out.println(ANSI_BOLD + "Votre monstre:" + ANSI_RESET + " " + me.getName() + "  " + renderHpBar(me.getPtnVie(), meMax, 20) + " " + me.getPtnVie() + "/" + meMax + "  [" + me.getStatus() + "]");
        System.out.println(ANSI_BOLD + "Adversaire:" + ANSI_RESET + " " + opp.getName() + "  " + renderHpBar(opp.getPtnVie(), oppMax, 20) + " " + opp.getPtnVie() + "/" + oppMax + "  [" + opp.getStatus() + "]");
        printSeparator();
    }

    public void combat () {
        System.out.print("\033[H\033[2J");
        System.out.println("Le combat entre " + player1.getName() + " et " + player2.getName() + " commence !");
        
        // Logique de combat ici
        Scanner scanner = new Scanner(System.in);

        // Préparer la table des PV max pour chaque monstre (pour les barres de vie)
        Map<Monstre, Integer> maxHp = new HashMap<>();
        for (Monstre m : player1.getTeam()) maxHp.put(m, m.getPtnVie());
        for (Monstre m : player2.getTeam()) maxHp.put(m, m.getPtnVie());

        // Le pokemon de depart avec le plus de vitesse commence
        Monstre m1 = player1.getActifMonster();
        Monstre m2 = player2.getActifMonster();

        if (m1.getSpeed() >= m2.getSpeed()) {
            System.out.println(player1.getName() + " commence le combat !");
        } else {
            System.out.println(player2.getName() + " commence le combat !"); 
        }

        while (true) {
            // Si un des joueurs n'a plus de monstres en vie, l'autre joueur gagne
            checkInteruption(scanner);

            turn++;

            // Il faut declarer une variable pour stocker le choix du joueur 1 car il y a des actions prioritaires
            m1 = player1.getActifMonster();
            m2 = player2.getActifMonster();
            printBattleHUD(player1, player2, maxHp);
            IAction actionP1 = selectAction(player1, player2, scanner);
            // System.out.print("\033[H\033[2J");
            m1 = player1.getActifMonster();
            m2 = player2.getActifMonster();
            printBattleHUD(player2, player1, maxHp);
            IAction actionP2 = selectAction(player2, player1, scanner);
            // System.out.print("\033[H\033[2J");
            

            if (actionP1.getPriority() < actionP2.getPriority()) {
                actionP1.execute();
                actionP2.execute();
            } 
            else if (actionP2.getPriority() < actionP1.getPriority()) {
                actionP2.execute();
                actionP1.execute();
            }
            else if (actionP1.getPriority() == 3) {
                // Meme priorite, on execute dans l'ordre des joueurs
                if (m1.getSpeed() >= m2.getSpeed()) {
                    actionP1.execute();
                    actionP2.execute();
                } else {
                    actionP2.execute();
                    actionP1.execute();
                }
            }
            else {
                // Meme priorite, on execute dans l'ordre des joueurs
                actionP1.execute();
                actionP2.execute();
            }
        }
    }
}
