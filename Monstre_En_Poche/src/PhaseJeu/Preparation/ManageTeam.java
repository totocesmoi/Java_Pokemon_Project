package PhaseJeu.Preparation;

import Competences.CollectionCompetence;
import Competences.Competence;
import Joueurs.Joueur;
import Monstres.CollectionMonstres;
import Monstres.Monstre;
import Monstres.MonstreVM;
import static Shared.Random.randInt;
import Shared.Types;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ManageTeam {
    private Scanner scanner;
    private CollectionMonstres collectionMonstres;
    private CollectionCompetence collectionCompetence;

    public ManageTeam(CollectionMonstres collectionMonstres, CollectionCompetence collectionCompetence) {
        this.scanner = new Scanner(System.in);
        this.collectionMonstres = collectionMonstres;
        this.collectionCompetence = collectionCompetence;
    }

    public void menuGestionEquipe(Joueur joueur) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\n--- Team Manager : " + joueur.getName() + " ---");
            System.out.println("1. Add monster");
            System.out.println("2. Show my team");
            System.out.println("3. Back");
            System.out.print("Choice : ");

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
        System.out.println("\n--- Choice your monster ---");
        ArrayList<MonstreVM> monstres = collectionMonstres.monstres;
        for (int i = 0; i < monstres.size(); i++) {
            System.out.println((i + 1) + ". " + monstres.get(i).getName() +
            " (" + monstres.get(i).getType() + ")");
        }
        System.out.print("Choice (0 to exit) : ");
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            if (choix > 0 && choix <= monstres.size()) {
                return monstres.get(choix - 1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error : Invalid stdin.");
        }
        return null;
    }

    private int[] defineStats(MonstreVM vm) {
        System.out.println("\n--- Choose your monster stats ---");
        System.out.println("1. Random");
        System.out.println("2. Manual");
        System.out.print("Choice : ");
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
            try {
                int val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.println("Index value out of range.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid stdin.");
            }
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
        
        System.out.println("\n--- Choisir des Compétences (Max 4) ---");
        
        if (disponibles.isEmpty()) {
            System.out.println("Aucune compétence disponible pour ce type.");
            return choisies;
        }

        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i).getName() + " (" + disponibles.get(i).getType() + ")");
        }

        while (choisies.size() < 4) {
            System.out.println("Compétences actuelles : " + choisies.size() + "/4");
            System.out.print("Entrez le numéro de la compétence à ajouter (0 pour finir) : ");
            try {
                int choix = Integer.parseInt(scanner.nextLine());
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
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide.");
            }
        }
        return choisies;
    }

    private void showTeam(Joueur joueur) {
        System.out.println("\n--- " + joueur.getName() + " team ---");
        for (Monstre m : joueur.getTeam()) {
            System.out.println("- " + m.getName() + " (PV: " + m.getPtnVie() + ")");
            System.out.print("  Skills: ");
            for(Competence c : m.competences) {
                System.out.print(c.toString() + " ");
            }
            System.out.println();
        }
    }
}
