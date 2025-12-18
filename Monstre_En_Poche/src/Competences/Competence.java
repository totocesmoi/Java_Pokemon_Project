package Competences;

public class Competence {
    private String name;
    private String type;
    private Integer pp;
    private Integer attack = 0;
    private Integer attackSpe = 0;
    private Integer accuracy = 100;

    public void setName (String value) {
        this.name = value;
    }

    public void setType (String value) {
        this.type = value;
    }

    public void setPP (Integer value) {
        this.pp = value;
    }

    public void setAttack (Integer value) {
        this.attack = value;
    }

    public void setAttackSpe (Integer value) {
        this.attackSpe = value;
    }

    public void setAccuracy (Integer value) {
        this.accuracy = value;
    }

    @Override
    public String toString() {
        return "Name=" + this.name + "\nType=" + this.type + "\nPP=" + this.pp + "\nAttack=" + this.attack + "\nAttackSpe=" + this.attackSpe + "\nAccuracy=" + this.accuracy; 
    }
}
