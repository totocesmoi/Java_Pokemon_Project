package Joueurs;

import Competences.Competence;
import Monstres.Monstre;
import Monstres.MonstreVM;
import Shared.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class IA extends Joueur {
    protected Random rand;

    public IA(String nom, List<MonstreVM> availableMonsters, List<Competence> availableSkills) {
        super(nom);
        this.rand = new Random();
        initTeam(availableMonsters, availableSkills);
    }

    public abstract Competence choisirCompetence(Monstre cible);

    private void initTeam(List<MonstreVM> monsters, List<Competence> skills) {
        if (monsters == null || monsters.isEmpty()) {
            System.out.println("Aucun monstre disponible pour l'IA.");
            return;
        }

        // Générer 3 monstres aléatoires parmi ceux disponibles
        for(int i=0; i<3; i++) {
            MonstreVM vm = monsters.get(rand.nextInt(monsters.size()));
            Monstre m = createMonsterFromVM(vm);
            fillCompetences(m, skills);
            this.addMonster(m);
        }
    }

    private Monstre createMonsterFromVM(MonstreVM vm) {
        // Génération des stats aléatoires entre Min et Max
        int hp = randomStat(vm.getHpMin(), vm.getHpMax());
        int atk = randomStat(vm.getAttackMin(), vm.getAttackMax());
        int atkSpe = randomStat(vm.getAttackSpeMin(), vm.getAttackSpeMax());
        int def = randomStat(vm.getDefenseMin(), vm.getDefenseMax());
        int defSpe = randomStat(vm.getDefenseSpeMin(), vm.getDefenseSpeMax());
        int speed = randomStat(vm.getSpeedMin(), vm.getSpeedMax());

        return new Monstre(vm.getName(), vm.getType(), hp, atk, atkSpe, def, defSpe, speed, new ArrayList<>());
    }
    
    private int randomStat(int min, int max) {
        if (min >= max) return min;
        return min + rand.nextInt(max - min + 1);
    }

    private void fillCompetences(Monstre m, List<Competence> skills) {
        if (skills == null || skills.isEmpty()) return;

        // Filtrer les compétences compatibles
        List<Competence> compatibleSkills = new ArrayList<>();
        for (Competence c : skills) {
            // Check compatible type (Same Type or Normal)
            if (c.getType() == Types.NORMAL || c.getType().getFamilleId() == m.getType().getFamilleId()) {
                compatibleSkills.add(c);
            }
        }

        if (compatibleSkills.isEmpty()) return;

        // Choisir jusqu'à 4 compétences
        int nbSkills = Math.min(4, compatibleSkills.size());
        List<Competence> chosen = new ArrayList<>();
        
        // Essayer de remplir avec des compétences uniques
        int attempts = 0;
        while (chosen.size() < nbSkills && attempts < 20) {
            Competence c = compatibleSkills.get(rand.nextInt(compatibleSkills.size()));
            // Vérification basique pour éviter les doublons exacts (même objet ou même nom)
            boolean already = false;
            for(Competence cc : chosen) {
                if(cc.getName().equals(c.getName())) already = true;
            }
            
            if (!already) {
                chosen.add(copyCompetence(c)); 
            }
            attempts++;
        }

        for (Competence c : chosen) {
            m.ajouterCompetence(c);
        }
    }

    private Competence copyCompetence(Competence original) {
        Competence c = new Competence();
        c.setName(original.getName());
        c.setType(original.getType());
        c.setCategory(original.getCategory());
        c.setPower(original.getPower());
        c.setAccuracy(original.getAccuracy());
        c.setPP(original.getPp());
        c.setEffect(original.getEffect());
        c.setEffectRate(original.getEffectRate());
        return c;
    }
}
