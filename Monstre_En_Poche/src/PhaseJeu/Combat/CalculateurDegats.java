package PhaseJeu.Combat;

import Shared.*;
import Competences.Competence;
import Competences.Enum.Categories;
import Monstres.Monstre;
import java.util.concurrent.ThreadLocalRandom;

public class CalculateurDegats {
    /**
     * @brief : Calculate any damage produce by a skill monster and effect
     * @param forward : Monster who attack
     * @param target : Monster who receive damage
     * @param skill : Competence use
     * @return Total damage
     */
    public static Pair<Double, Effects> calculerDegats(Monstre forward, Monstre target, Competence skill) {
        double coef = ThreadLocalRandom.current().nextDouble(0.85, 1.0);
        coef = 1;
        double damages = 0;
        Effects effect = Effects.NONE;
        
        // Si il n'a pas de compétence, il tape à main nue, donc on considère que c'est des dommages physique
        if (skill == null) {
            damages = (int)(20*((double) forward.getAttack() / target.getDefense())*coef);
            return new Pair<>(damages, effect);
        }

        // Gestion de la précision
        int random = 0; 
        if (skill.getAccuracy() < random) {
            System.out.println("The attack missed !");
            return new Pair<>(0.0, Effects.NONE);
        }
        
        int puissance = skill.getPower();

        // Si on met de sort de status ?
        // A voir comment on le traite ici ou ailleurs après
        // TODO : Gérer l'altération de status
        if (target.getStatus() == Effects.NONE && (skill.getCategory() == Categories.STATUS || skill.getEffect() != Effects.NONE)) {
            // Calcule de si on applique un effet ou pas
            random = 10; // A METTRE EN RANDOM
            if (random <= skill.getEffectRate())
                effect = skill.getEffect();
        }


        int statAttack = skill.getCategory() == Categories.SPECIAL ? target.getAttackSpe() : target.getAttack();
        int statDefense = skill.getCategory() == Categories.SPECIAL ? target.getDefenseSpe() : target.getDefense();

        // Eviter la division par zéro
        if (statDefense == 0) statDefense = 1;

        double effectivness = avantage(skill, target);
        if (effectivness >= 2) System.out.println("It's super effective !");
        else if (effectivness <= 0.5) System.out.println("It's not very effective...");

        damages = ((double)((11*puissance*statAttack)/(25*statDefense)) +2)  * coef * effectivness;
        
        return new Pair<>(damages, effect);
    }

    /**
     * @brief Allow to calculate the advantage in depend on type of skill and type of monster target
     * @param skill : Competence use
     * @param target : Monster Target
     * @return number of advantage.
     */
    public static double avantage(Competence skill, Monstre target) {
        // Gestion du type eau
        if (skill.getType().equals(Types.EAU)) {
            if (target.getType().equals(Types.FEU)) {
                return 2;
            }
            if (target.getType().equals(Types.FOUDRE)) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type feu
        if (skill.getType().equals(Types.FEU)) {
            if (target.getType().equals(Types.NATURE)) {
                return 2;
            }
            if (target.getType().equals(Types.EAU)) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type nature
        if (skill.getType().equals(Types.NATURE)) {
            if (target.getType().equals(Types.TERRE)) {
                return 2;
            }
            if (target.getType().equals(Types.FEU)) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type terre
        if (skill.getType().equals(Types.TERRE)) {
            if (target.getType().equals(Types.FOUDRE)) {
                return 2;
            }
            if (target.getType().equals(Types.NATURE)) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type foudre
        if (skill.getType().equals(Types.FOUDRE)) {
            if (target.getType().equals(Types.EAU)) {
                return 2;
            }
            if (target.getType().equals(Types.TERRE)) {
                return 0.5;
            }
            return 1;
        }

        // Par défaut en cas d'erreur on return 1
        // On ne doit jamais arriver ici (sauf type normal)
        return 1;
    }
}
