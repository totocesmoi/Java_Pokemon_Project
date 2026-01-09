package Monstres;

import Competences.Competence;
import PhaseJeu.Combat.CalculateurDegats;
import Shared.Effects;
import Shared.Pair;
import Shared.Types;
import java.util.ArrayList;

public class Monstre {
    private String name;
    private Types type;
    private Effects status;
    private int ptnVie;
    private int attack;
    private int attackSpe;
    private int defense;
    private int defenseSpe;
    private int speed;

    public ArrayList<Competence> competences;

    public Monstre(String name, Types type,int ptnVie, int attack, int attackSpe,
                   int defense, int defenseSpe, int speed, ArrayList<Competence> competences) {
        this.name = name;
        this.type = type;
        this.status = Effects.NONE;
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

    public Effects getStatus() {
        return status;
    }
    public void setStatus(Effects status) {
        this.status = status;
    }

    public Types getType() {
        return type;
    }
    public void setType(Types type) {
        this.type = type;
    }

    /**
     * @brief : Méthode allow to attack other monster
     * @param target : monster target
     * @param competence : skill use to attack the monster
     */
    public void attackMonster(Monstre target, Competence competence) {
        String attackName = (competence != null) ? competence.getName() : "Attaque à main nue";
        System.out.println(this.name + " use " + attackName + " on " + target.getName() + " !");

        Pair<Double, Effects> damages = CalculateurDegats.calculerDegats(this, target, competence);

        target.receivedDamage((int) Math.round(damages.getKey()));
        // Appliquer l'effet si il y en a un
        if (!target.isKO() && damages.getValue() != Effects.NONE) {
            System.out.print(" and " + damages.getValue());
            target.setStatus(damages.getValue());
        }
        
        System.out.print("\n");
    }

    public boolean peutApprendre(Competence c) {
        if (competences.size() >= 4) {
            System.out.println("Le monstre a deja 4 competences.");
            return false;
        } 
        if (!(c.getType() == Types.NORMAL) && !(c.getType() == this.type)) {
            System.out.println("Type incompatible (Monstre: " + type + ", Competence: " + c.getType() + ")");
            return false;
        }
        return true;
    }

    public boolean ajouterCompetence(Competence c) {
        if (peutApprendre(c)) {
            competences.add(c);
            return true;
        }
        return false;
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
