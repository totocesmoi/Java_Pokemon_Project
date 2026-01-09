package PhaseJeu.Combat;
import Joueurs.Joueur;
import Monstres.Monstre;
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
                int attaqueChoisie = Integer.parseInt(scanner.nextLine()) - 1;
                return new AttackAction(player1.getActifMonster(), player2, player1.getActifMonster().getCompetences().get(attaqueChoisie));
            case "2":
                // Changer de monstre
                return null;
            case "3":
                // Utiliser un objet
                return null;
            case "4":
                // Declarer forfait
                return null;
            default:
                return null;
        }
    }

    public void combat () {
        System.out.print("\033[H\033[2J");
        System.out.println("Le combat entre " + player1.getName() + " et " + player2.getName() + " commence !");
        
        // Logique de combat ici
        Scanner scanner = new Scanner(System.in);

        // Le pokemon de depart avec le plus de vitesse commence
        Monstre m1 = player1.getActifMonster();
        Monstre m2 = player2.getActifMonster();

        if (m1.getSpeed() >= m2.getSpeed()) {
            System.out.println(player1.getName() + " commence le combat !");
        } else {
            System.out.println(player2.getName() + "2 commence le combat !"); 
        }

        while (true) {
            // Si un des joueurs n'a plus de monstres en vie, l'autre joueur gagne
            for (int i = 0; i < player1.getTeam().size(); i++) {
                if (player1.getTeam().get(i).getPtnVie() > 0) {
                    // Le monstre peut attaquer
                } else {
                    System.out.println(player1.getTeam().get(i).getName() + " est KO !");
                    return;
                }

                if (player2.getTeam().get(i).getPtnVie() > 0) {
                    // Le monstre peut attaquer
                } else {
                    System.out.println(player2.getTeam().get(i).getName() + " est KO !");
                    return;
                }
            }

            turn++;
            
            // System.out.println(player1.getName() + ", choisissez une action pour " + player1.getActifMonster().getName() + " :");
            // System.out.println("1. Attaquer");
            // System.out.println("2. Changer de monstre");
            // System.out.println("3. Utiliser un objet");
            // System.out.println("4. Declarer forfait");

            // String choix = scanner.nextLine();
            // System.out.print("\033[H\033[2J");

            // Il faut declarer une variable pour stocker le choix du joueur 1 car il y a des actions prioritaires
            System.out.println("\n--- Tour " + turn + " ---");
            IAction actionP1 = selectAction(player1, player2, scanner);
            System.out.print("\033[H\033[2J");
            System.out.println("\n--- Tour " + turn + " ---");
            IAction actionP2 = selectAction(player2, player1, scanner);
            System.out.print("\033[H\033[2J");
            
            // switch (choix) {
            //     case "1" :
            //         // Attaquer
            //         if (player1.getActifMonster().getCompetences().isEmpty()) {
            //             System.out.println("Attaque a main nue.");
            //             actionP1 = new AttackAction(player1.getActifMonster(), player2, null);
            //             break;
            //         }
            //         System.out.println("Attaques disponibles :");
            //         for (int i = 0; i < player1.getActifMonster().getCompetences().size(); i++) {
            //             System.out.println((i + 1) + ". " + player1.getActifMonster().getCompetences().get(i).getName());
            //         }
            //         System.out.print("Choisissez une attaque : ");
            //         int attaqueChoisie = Integer.parseInt(scanner.nextLine()) - 1;
            //         // player1.chooseAttack(attaqueChoisie);
            //         actionP1 = new AttackAction(player1.getActifMonster(), player2, player1.getActifMonster().getCompetences().get(attaqueChoisie));
            //         break;
            //     default:
            //         throw new AssertionError();
            // }

            // System.out.println("\n--- Tour " + turn + " ---");
            // System.out.println(player2.getName() + ", choisissez une action pour " + player2.getActifMonster().getName() + " :");
            // System.out.println("1. Attaquer");
            // System.out.println("2. Changer de monstre");
            // System.out.println("3. Utiliser un objet");
            // System.out.println("4. Declarer forfait");

            // choix = scanner.nextLine();
            // System.out.print("\033[H\033[2J");

            // switch (choix) {
            //     case "1" :
            //         // Attaquer
            //         System.out.println("Attaques disponibles :");
            //         for (int i = 0; i < player2.getActifMonster().getCompetences().size(); i++) {
            //             System.out.println((i + 1) + ". " + player2.getActifMonster().getCompetences().get(i).getName());
            //         }
            //         System.out.print("Choisissez une attaque : ");
            //         int attaqueChoisie = Integer.parseInt(scanner.nextLine()) - 1;
            //         actionP2 = new AttackAction(player2.getActifMonster(), player1, player2.getActifMonster().getCompetences().get(attaqueChoisie));
            //         break;
            //     default:
            //         throw new AssertionError();
            // }


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
