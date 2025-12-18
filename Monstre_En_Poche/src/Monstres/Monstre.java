package Monstres;

import Competences.Competence;
import PhaseJeu.Combat.CalculateurDegats;
import java.util.ArrayList;

public class Monstre {
    private String name;
    private String type;
    private String category;
    private Status status;
    private int ptnVie;
    private int attack;
    private int attackSpe;
    private int defense;
    private int defenseSpe;
    private int speed;

    public ArrayList<Competence> competences;

    public Monstre(String name, String type, String category,int ptnVie, int attack, int attackSpe,
                   int defense, int defenseSpe, int speed, ArrayList<Competence> competences) {
        this.name = name;
        this.type = type;
        this.status = null;
        this.category = category;
        this.ptnVie = ptnVie;
        this.attack = attack;
        this.attackSpe = attackSpe;
        this.defense = defense;
        this.defenseSpe = defenseSpe;
        this.speed = speed;
        this.competences = competences;
    }

    public int getPtnVie() {
        return ptnVie;
    }
    public void setPtnVie(int ptnVie) {
        this.ptnVie = ptnVie;
    }

    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttackSpe() {
        return attackSpe;
    }
    public void setAttackSpe(int attackSpe) {
        this.attackSpe = attackSpe;
    }

    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefenseSpe() {
        return defenseSpe;
    }
    public void setDefenseSpe(int defenseSpe) {
        this.defenseSpe = defenseSpe;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @brief : Méthode allow to attack other monster
     * @param target : monster target
     * @param competence : skill use to attack the monster
     */
    public void attackMonster(Monstre target, Competence competence) {
        System.out.println(this.name + " use " + competence.getName() + " on " + target.getName() + " !");
        
        int degats = CalculateurDegats.calculerDegats(this, target, competence);
        
        target.receivedDamage(degats);
    }

    /**
     * @brief : Allow to receive any damage for the current monster
     * @param damage : number indicate how many damages, the monster received
     */
    public void receivedDamage(int damage) {
        this.ptnVie -= damage;
        if (this.ptnVie < 0) {
            this.ptnVie = 0;
        }
        System.out.println(this.name + " received " + damage + " damage. Current HP : " + this.ptnVie);
        
        if (this.isKO()) {
            System.out.println(this.name + " is K.O. !");
        }
    }

    public boolean isKO() {
        return this.ptnVie <= 0;
    }

    public String getType() {
        return type;
    }
    private void setType(String type) {
        this.type = type;
    }

    public ArrayList<Competence> getCompetences() {
        return competences;
    }
    private void setCompetences(ArrayList<Competence> competences) {
        this.competences = competences;
    }

    @Override
    public String toString() {
        return "Monstre{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status.toString() + '\'' +
                ", ptnVie=" + ptnVie +
                ", attack=" + attack +
                ", attackSpe=" + attackSpe +
                ", defense=" + defense +
                ", defenseSpe=" + defenseSpe +
                ", speed=" + speed +
                '}';
    }
}
