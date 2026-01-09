package Joueurs;

import Competences.Competence;
import Monstres.Monstre;
import Monstres.MonstreVM;
import PhaseJeu.Combat.CalculateurDegats;
import Shared.Effects;
import Shared.Pair;
import java.util.List;

public class IAExpert extends IA {

    public IAExpert(String nom, List<MonstreVM> monsters, List<Competence> skills) {
        super(nom, monsters, skills);
    }

    @Override
    public Competence choisirCompetence(Monstre cible) {
        // Logique "Intelligente" -> Maximiser les dégâts
        Monstre actif = getActifMonster();
        if (actif == null || actif.getCompetences().isEmpty()) return null;
        
        List<Competence> skills = actif.getCompetences();
        
        Competence bestSkill = null;
        double maxDamage = -1.0;

        System.out.println(this.getName() + " (Expert) analyse les faiblesses de " + cible.getName() + "...");

        for (Competence skill : skills) {
            Pair<Double, Effects> simulation = CalculateurDegats.calculerDegats(actif, cible, skill);
            double damage = simulation.getKey();

            if (damage > maxDamage) {
                maxDamage = damage;
                bestSkill = skill;
            }
        }
        
        if (bestSkill != null) {
             System.out.println("... et choisit " + bestSkill.getName() + " pour infliger " + (int)maxDamage + " dégâts théoriques !");
        }

        return bestSkill;
    }
}
