package PhaseJeu.Preparation;

import Competences.CollectionCompetence;
import Competences.Competence;
import Joueurs.Joueur;
import Monstres.CollectionMonstres;
import Monstres.Monstre;
import Monstres.MonstreVM;
import static Shared.Random.randInt;
import Shared.ReadInt;
import Shared.Types;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ManageTeam {
    private Scanner scanner;
    private CollectionMonstres collectionMonstres;
    private CollectionCompetence collectionCompetence;

    // ANSI styling
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

    public ManageTeam(CollectionMonstres collectionMonstres, CollectionCompetence collectionCompetence) {
        this.scanner = new Scanner(System.in);
        this.collectionMonstres = collectionMonstres;
        this.collectionCompetence = collectionCompetence;
    }

    public void menuGestionEquipe(Joueur joueur) {
        boolean continuer = true;
        while (continuer) {
            System.out.println();
            printSeparator();
            System.out.println(ANSI_BOLD + "         Team Manager : " + joueur.getName() + ANSI_RESET);
            System.out.println("1. Add monster");
            System.out.println("2. Show my team");
            System.out.println("3. Back");
            System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");

            String choix = scanner.nextLine();
            System.out.print("\033[H\033[2J");

            switch (choix) {
                case "1":
                    addMonster(joueur);
                    break;
                case "2":
                    showTeam(joueur);
                    break;
                case "3":
                    continuer = false;
                    break;
                default:
                    System.out.println("Invalide choice.");
            }
        }
    }

    private void addMonster(Joueur joueur) {
        if (joueur.getTeam().size() >= 3) {
            System.out.println("Your team is full (3 max).");
            return;
        }

        MonstreVM vm = chooseModeleStatMonster();
        if (vm == null) return;

        int[] stats = defineStats(vm);

        ArrayList<Competence> competences = chooseSkill(vm.getType());

        Monstre nouveauMonstre = new Monstre(
                vm.getName(),
                vm.getType(),
                stats[0],
                stats[1],
                stats[2],
                stats[3],
                stats[4],
                stats[5],
                competences
        );

        if (joueur.addMonster(nouveauMonstre)) {
            System.out.println(nouveauMonstre.getName() + " join your team !");
        }
    }

    private MonstreVM chooseModeleStatMonster() {
        printSeparator();
        System.out.println(ANSI_BOLD + "         Choice your monster" + ANSI_RESET);
        ArrayList<MonstreVM> monstres = collectionMonstres.monstres;
        for (int i = 0; i < monstres.size(); i++) {
            System.out.println((i + 1) + ". " + monstres.get(i).getName() +
            " (" + monstres.get(i).getType() + ")");
        }
        System.out.print("Choice (0 to exit) : ");
        int choix = ReadInt.readInt(scanner, 0, monstres.size());
        if (choix > 0 && choix <= monstres.size()) {
            return monstres.get(choix - 1);
        }
        return null;
    }

    private int[] defineStats(MonstreVM vm) {
        printSeparator();
        System.out.println(ANSI_BOLD + "         Choose your monster stats" + ANSI_RESET);
        System.out.println("1. Random");
        System.out.println("2. Manual");
        System.out.print(ANSI_UNDERLINE + "Choice :" + ANSI_RESET + " ");
        String mode = scanner.nextLine();

        int hp, atk, atkSpe, def, defSpe, speed;

        if (mode.equals("2")) {
            hp = requestStat("HP", vm.getHpMin(), vm.getHpMax());
            atk = requestStat("Attack", vm.getAttackMin(), vm.getAttackMax());
            atkSpe = requestStat("AttackSpe", vm.getAttackSpeMin(), vm.getAttackSpeMax());
            def = requestStat("Defense", vm.getDefenseMin(), vm.getDefenseMax());
            defSpe = requestStat("DefenseSpe", vm.getDefenseSpeMin(), vm.getDefenseSpeMax());
            speed = requestStat("Speed", vm.getSpeedMin(), vm.getSpeedMax());
        } else {
            Random rand = new Random();
            hp = randInt(rand, vm.getHpMin(), vm.getHpMax());
            atk = randInt(rand, vm.getAttackMin(), vm.getAttackMax());
            atkSpe = randInt(rand, vm.getAttackSpeMin(), vm.getAttackSpeMax());
            def = randInt(rand, vm.getDefenseMin(), vm.getDefenseMax());
            defSpe = randInt(rand, vm.getDefenseSpeMin(), vm.getDefenseSpeMax());
            speed = randInt(rand, vm.getSpeedMin(), vm.getSpeedMax());
            System.out.println("Random stats generate successfully.");
        }

        return new int[]{hp, atk, atkSpe, def, defSpe, speed};
    }

    private int requestStat(String nomStat, int min, int max) {
        while (true) {
                System.out.print(nomStat + " (" + min + "-" + max + ") : ");
                int val = ReadInt.readInt(scanner, min, max);
                return val;
        }
    }

    private ArrayList<Competence> chooseSkill(Types typeMonstre) {
        ArrayList<Competence> choisies = new ArrayList<>();
        ArrayList<Competence> disponibles = new ArrayList<>();

        // Filtrer les compétences : Type du monstre OU Type "Normal"
        for (Competence c : collectionCompetence.competences) {
            if (c.getType() == (typeMonstre) || c.getType() == Types.NORMAL) {
                disponibles.add(c);
            }
        }
        
        System.out.println(ANSI_BOLD + "=================================================" + ANSI_RESET);
        System.out.println(ANSI_BOLD + "         Choisir des Compétences (Max 4)" + ANSI_RESET);
        
        if (disponibles.isEmpty()) {
            System.out.println("Aucune compétence disponible pour ce type.");
            return choisies;
        }

        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i).getName() + " (" + disponibles.get(i).getType() + ")");
        }

        while (choisies.size() < 4) {
            System.out.println("Compétences actuelles : " + ANSI_UNDERLINE + choisies.size() + "/4" + ANSI_RESET);
            System.out.print("Entrez le numéro de la compétence à ajouter (0 pour finir) : ");
            int choix = ReadInt.readInt(scanner, 0, disponibles.size());
            if (choix == 0) break;
            if (choix > 0 && choix <= disponibles.size()) {
                Competence c = disponibles.get(choix - 1);
                if (!choisies.contains(c)) {
                    choisies.add(c);
                    System.out.println(c.getName() + " ajoutée.");
                } else {
                    System.out.println("Déjà sélectionnée.");
                }
            } else {
                System.out.println("Invalide.");
            }
        }
        return choisies;
    }

    private void showTeam(Joueur joueur) {
        System.out.println();
        printSeparator();
        System.out.println(ANSI_BOLD + joueur.getName() + " team" + ANSI_RESET);
        for (Monstre m : joueur.getTeam()) {
            int hp = m.getPtnVie();
            int max = Math.max(1, hp); // no stored max here, show proportional bar (full)
            System.out.println("- " + ANSI_BOLD + m.getName() + ANSI_RESET + " " + renderHpBar(hp, max, 20) + " " + hp + "/" + max + " [" + m.getStatus() + "]");
            System.out.print("  Skills: ");
            for(Competence c : m.competences) {
                System.out.print(c.getName() + " ");
            }
            System.out.println();
        }
        printSeparator();
    }
}
