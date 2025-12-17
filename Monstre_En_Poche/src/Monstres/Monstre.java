package Monstres;

import Competences.Competence;
import java.util.ArrayList;

public class Monstre {
    private String name;
    private String type;
    private String category;
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
    private void setPtnVie(int ptnVie) {
        this.ptnVie = ptnVie;
    }

    public int getAttack() {
        return attack;
    }
    private void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttackSpe() {
        return attackSpe;
    }
    private void setAttackSpe(int attackSpe) {
        this.attackSpe = attackSpe;
    }

    public int getDefense() {
        return defense;
    }
    private void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefenseSpe() {
        return defenseSpe;
    }
    private void setDefenseSpe(int defenseSpe) {
        this.defenseSpe = defenseSpe;
    }

    public int getSpeed() {
        return speed;
    }
    private void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    private void setCategory(String category) {
        this.category = category;
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
                "type='" + type + '\'' +
                "category='" + category + '\'' +
                ", ptnVie=" + ptnVie +
                ", attack=" + attack +
                ", attackSpe=" + attackSpe +
                ", defense=" + defense +
                ", defenseSpe=" + defenseSpe +
                ", speed=" + speed +
                '}';
    }
}
