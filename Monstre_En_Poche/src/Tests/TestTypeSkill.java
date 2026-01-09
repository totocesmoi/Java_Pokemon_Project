package Tests;

import Competences.Competence;
import Competences.Enum.Categories;
import Monstres.Monstre;
import PhaseJeu.Combat.CalculateurDegats;
import Shared.Effects;
import Shared.Pair;
import Shared.Types;
import java.util.ArrayList;

public class TestTypeSkill {
    public static void main(String[] args) {
        System.out.println("=== TEST UNITAIRE : EFFETS DE TYPE FEU ===");

        // 1. Création des monstres
        // Monstre Attaquant (FEU)
        ArrayList<Competence> comps1 = new ArrayList<>();
        Monstre attaquant = new Monstre("Salamèche", Types.FEU, 100, 50, 60, 40, 40, 50, comps1);

        // Monstre Défenseur (PLANTE) - Faible au feu
        ArrayList<Competence> comps2 = new ArrayList<>();
        Monstre defenseur = new Monstre("Bulbizarre", Types.PLANTE, 100, 40, 50, 40, 50, 40, comps2);

        // 2. Création de la compétence (FEU)
        Competence lanceFlammes = new Competence();
        lanceFlammes.setName("Lance-Flammes");
        lanceFlammes.setType(Types.FEU);
        lanceFlammes.setCategory(Categories.SPECIAL);
        lanceFlammes.setPower(90);
        lanceFlammes.setAccuracy(100);
        lanceFlammes.setPP(15);
        // Aucun effet de base, l'effet vient du passif de type
        lanceFlammes.setEffect(Effects.NONE); 
        lanceFlammes.setEffectRate(0);

        attaquant.getCompetences().add(lanceFlammes);

        System.out.println("Attaquant: " + attaquant.getName() + " (" + attaquant.getType() + ")");
        System.out.println("Défenseur: " + defenseur.getName() + " (" + defenseur.getType() + ")");
        System.out.println("Attaque utilisée: " + lanceFlammes.getName() + " (" + lanceFlammes.getType() + ")");
        System.out.println("------------------------------------------------");

        // 3. Simulation de pplusieurs attaques pour vérifier les probabilités
        int nombreEssais = 10;
        int nbBrulures = 0;
        int nbBoostDegats = 0;

        for (int i = 0; i < nombreEssais; i++) {
            System.out.println("Essai #" + (i + 1));
            
            // Réinitialisation du status pour tester l'application de l'effet
            defenseur.setStatus(Effects.NONE);

            // Calcul des dégâts
            Pair<Double, Effects> resultat = CalculateurDegats.calculerDegats(attaquant, defenseur, lanceFlammes);
            double degats = resultat.getKey();
            Effects effetApplique = resultat.getValue();

            System.out.print(" -> Dégâts infligés: " + String.format("%.2f", degats));
            
            if (effetApplique == Effects.BURNED) {
                System.out.println(" | EFFET APPLIQUÉ : BRÛLURE !");
                nbBrulures++;
                // On applique le statut pour voir si le prochain coup fait plus mal ou ne réapplique pas
                // Mais pour le test statistique, on reset à chaque tour souvent.
                // Ici on reset au début de la boucle.
            } else {
                System.out.println(" | Pas d'effet supplémentaire (dégâts boostés)");
                nbBoostDegats++;
            }
        }

        System.out.println("------------------------------------------------");
        System.out.println("RÉSULTATS SUR " + nombreEssais + " ESSAIS :");
        System.out.println("Nombre de brûlures : " + nbBrulures);
        System.out.println("Nombre de boosts dégâts : " + nbBoostDegats);
    }
}
