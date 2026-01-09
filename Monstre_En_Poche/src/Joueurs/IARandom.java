package Joueurs;

import Competences.Competence;
import Monstres.Monstre;
import Monstres.MonstreVM;
import java.util.List;

public class IARandom extends IA {

    public IARandom(String nom, List<MonstreVM> monsters, List<Competence> skills) {
        super(nom, monsters, skills);
    }

    @Override
    public Competence choisirCompetence(Monstre cible) {
        Monstre actif = getActifMonster();
        if (actif == null || actif.getCompetences().isEmpty()) return null;
        
        List<Competence> skills = actif.getCompetences();
        System.out.println(this.getName() + " (Aléatoire) réfléchit... et prend une carte au hasard !");
        return skills.get(rand.nextInt(skills.size()));
    }
}
