package PhaseJeu.Combat;

import Competences.Competence;
import Competences.Enum.Types;
import Monstres.Monstre;

import java.util.concurrent.ThreadLocalRandom;

public class CalculateurDegats {
    /**
     * @brief : Calculate any damage produce by a skill monster
     * @param forward : Monster who attack
     * @param target : Monster who receive damage
     * @param skill : Competence use
     * @return Total damage
     */
    public static int calculerDegats(Monstre forward, Monstre target, Competence skill) {
        double coef = ThreadLocalRandom.current().nextDouble(0.85, 1.0);

        // Si il n'a pas de compétence, il tape à main nue, donc on considère que c'est des dommages physique
        if (skill == null) {
            return (int)(20*((double) forward.getAttack() / target.getDefense())*coef);
        }

        // On récupère la puissance de la compétence en fonction de son type
        boolean estSpecial = skill.getAttackSpe() > skill.getAttack();
        int puissance = estSpecial ? skill.getAttackSpe() : skill.getAttack();
        
        // Si on met de sort de status ?
        // A voir comment on le traite ici ou ailleurs après
        // TODO : Gérer l'altération de status
        if (puissance == 0){
            return 0;
        }


        int statAttack = estSpecial ? target.getAttackSpe() : target.getAttack();
        int statDefense = estSpecial ? target.getDefenseSpe() : target.getDefense();

        // Eviter la division par zéro
        if (statDefense == 0) {
            statDefense = 1;
        }

        double damage = ((double)((11*puissance*statAttack)*(25*statDefense)) +2)  * coef * avantage(skill, target);

        return (int)damage;
    }

    /**
     * @brief Allow to calculate the advantage in depend on type of skill and type of monster target
     * @param skill : Competence use
     * @param target : Monster Target
     * @return number of advantage.
     */
    public static double avantage(Competence skill, Monstre target) {
        // Gestion du type eau
        if (skill.getType().equals(Types.Eau.toString())) {
            if (target.getType().equals(Types.Feu.toString())) {
                return 2;
            }
            if (target.getType().equals(Types.Foudre.toString())) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type feu
        if (skill.getType().equals(Types.Feu.toString())) {
            if (target.getType().equals(Types.Nature.toString())) {
                return 2;
            }
            if (target.getType().equals(Types.Eau.toString())) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type nature
        if (skill.getType().equals(Types.Nature.toString())) {
            if (target.getType().equals(Types.Terre.toString())) {
                return 2;
            }
            if (target.getType().equals(Types.Feu.toString())) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type terre
        if (skill.getType().equals(Types.Terre.toString())) {
            if (target.getType().equals(Types.Foudre.toString())) {
                return 2;
            }
            if (target.getType().equals(Types.Nature.toString())) {
                return 0.5;
            }
            return 1;
        }

        // Gestion du type foudre
        if (skill.getType().equals(Types.Foudre.toString())) {
            if (target.getType().equals(Types.Eau.toString())) {
                return 2;
            }
            if (target.getType().equals(Types.Terre.toString())) {
                return 0.5;
            }
            return 1;
        }

        // Par défaut en cas d'erreur on return 1
        // On ne doit jamais arriver ici
        return 1;
    }
}
