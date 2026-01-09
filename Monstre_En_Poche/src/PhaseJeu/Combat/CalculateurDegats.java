package PhaseJeu.Combat;

import Competences.Competence;
import Competences.Enum.Categories;
import Monstres.Monstre;
import Shared.*;
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

        if (forward.getStatus() == Effects.PARALYZE) {
            if (ThreadLocalRandom.current().nextInt(4) != 0) {
                System.out.println(forward.getName() + " est paralysé et ne peut pas bouger !");
                return new Pair<>(0.0, Effects.NONE);
            }
        }

        if (forward.getStatus() == Effects.FLOODED && forward.getType() != Types.EAU) {
            double chanceChute = 0.20; 
            if (ThreadLocalRandom.current().nextDouble() < chanceChute) {
                System.out.println(forward.getName() + " glisse sur le terrain inondé !");
                double selfDamage = forward.getAttack() / 4.0; // 
                forward.setPtnVie((int)(forward.getPtnVie() - selfDamage));
                return new Pair<>(0.0, Effects.NONE);
            }
        }
        
        // Si il n'a pas de compétence, il tape à main nue, donc on considère que c'est des dommages physique
        if (skill == null) {
            double rawDamage = 20 * ((double) forward.getAttack() / target.getDefense()) * coef;
            return new Pair<>(damages, effect);
        }

        // Gestion de la précision
        int randomAcc = ThreadLocalRandom.current().nextInt(1, 101);
        if (randomAcc > skill.getAccuracy()) {
            System.out.println("L'attaque a échoué !");
            return new Pair<>(0.0, Effects.NONE); // [cite: 104] Effet spécial annulé si raté
        }
        
        int puissance = skill.getPower();
        int statAttack = skill.getCategory() == Categories.SPECIAL ? forward.getAttackSpe() : forward.getAttack();
        int statDefense = skill.getCategory() == Categories.SPECIAL ? target.getDefenseSpe() : target.getDefense();

        // Effet de statut pour le type terre
        if (target.getStatus() == Effects.HIDDEN) {
            statDefense *= 2;
        }

        // Eviter la division par zéro
        if (statDefense == 0) statDefense = 1;

        double effectivness = avantage(skill, target);
        if (effectivness >= 2) System.out.println("It's super effective !");
        else if (effectivness <= 0.5) System.out.println("It's not very effective...");

        damages = ((double)((11*puissance*statAttack)/(25*statDefense)) +2)  * coef * effectivness;

        if (target.getStatus() == Effects.NONE && (skill.getCategory() == Categories.STATUS || skill.getEffect() != Effects.NONE)
            && target.getType() != skill.getType()) {
            // Calcule de si on applique un effet ou pas
            int random = ThreadLocalRandom.current().nextInt(1, 101);
            if (random <= skill.getEffectRate())
                target.setStatus(effect = skill.getEffect());
        }

        // Gestion des effets speciaux de type (STAB)
        if (skill.getCategory() == Categories.SPECIAL && forward.getType() == skill.getType()) {
        switch (forward.getType()) {
            case FEU:
                // [cite: 74, 75] Chance de brûler
                if (target.getStatus() == Effects.NONE && ThreadLocalRandom.current().nextInt(100) < 20) { 
                    effect = Effects.BURNED;
                }
                break;
            
            case INSECTE:
                //  1 chance sur 3 d'empoisonner
                if (target.getStatus() == Effects.NONE && ThreadLocalRandom.current().nextInt(3) == 0) {
                    effect = Effects.POISON;
                }
                break;

            case PLANTE:
                 if (ThreadLocalRandom.current().nextInt(100) < 20) {
                     forward.setStatus(Effects.NONE);
                 }
                 break;

            case EAU:
                 if (ThreadLocalRandom.current().nextInt(100) < 25) { 
                     effect = Effects.FLOODED; 
                 }
                 break;

            case TERRE:
                 if (ThreadLocalRandom.current().nextInt(100) < 5) {
                     forward.setStatus(Effects.HIDDEN);
                 }
                 break;
                 
             case FOUDRE:
                 if (target.getStatus() == Effects.NONE && ThreadLocalRandom.current().nextInt(100) < 10) { // % arbitraire ou stat
                     effect = Effects.PARALYZE;
                 }
                 break;
            default: break;
        }
    }
        
        return new Pair<>(damages, effect);
    }

    private static final double[][] efficiency = {
        // DEF: EAU(1), FEU(2), FOUDRE(3), NATURE(4), TERRE(5), NORMAL(6)
        /* VIDE */    {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
        /* ATK EAU */ {1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0},
        /* ATK FEU */ {0.5, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0},
        /* ATK FOU */ {2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0},
        /* ATK NAT */ {1.0, 0.5, 1.0, 1.0, 1.0, 2.0, 1.0},
        /* ATK TER */ {1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0},
        /* ATK NOR */ {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}
    };

    /**
     * @brief Allow to calculate the advantage in depend on type of skill and type of monster target
     * @param skill : Competence use
     * @param target : Monster Target
     * @return number of advantage.
     */
    public static double avantage(Competence skill, Monstre target) {
        return efficiency[skill.getType().getFamilleId()][target.getType().getFamilleId()];
    }
}
