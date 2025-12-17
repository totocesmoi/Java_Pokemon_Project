package Monstres;

public class MonstreVM {
    private String name;
    private String type;
    private int hpMin;
    private int hpMax;
    private int attackMin;
    private int attackMax;
    private int attackSpeMin;
    private int attackSpeMax;
    private int defenseMin;
    private int defenseMax;
    private int defenseSpeMin;
    private int defenseSpeMax;
    private int speedMin;
    private int speedMax;

    public MonstreVM(String name, String type, int hpMin, int hpMax, int attackMin, int attackMax, int attackSpeMin,
                     int attackSpeMax, int defenseMin, int defenseMax, int defenseSpeMin,
                     int defenseSpeMax, int speedMin, int speedMax) {
        this.name = name;
        this.type = type;
        this.hpMin = hpMin;
        this.hpMax = hpMax;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        this.attackSpeMin = attackSpeMin;
        this.attackSpeMax = attackSpeMax;
        this.defenseMin = defenseMin;
        this.defenseMax = defenseMax;
        this.defenseSpeMin = defenseSpeMin;
        this.defenseSpeMax = defenseSpeMax;
        this.speedMin = speedMin;
        this.speedMax = speedMax;
    }

    public MonstreVM() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHpMin(int hpMin) {
        this.hpMin = hpMin;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public void setAttackMin(int attackMin) {
        this.attackMin = attackMin;
    }

    public void setAttackMax(int attackMax) {
        this.attackMax = attackMax;
    }

    public void setAttackSpeMin(int attackSpeMin) {
        this.attackSpeMin = attackSpeMin;
    }

    public void setAttackSpeMax(int attackSpeMax) {
        this.attackSpeMax = attackSpeMax;
    }

    public void setDefenseMin(int defenseMin) {
        this.defenseMin = defenseMin;
    }

    public void setDefenseMax(int defenseMax) {
        this.defenseMax = defenseMax;
    }

    public void setDefenseSpeMin(int defenseSpeMin) {
        this.defenseSpeMin = defenseSpeMin;
    }

    public void setDefenseSpeMax(int defenseSpeMax) {
        this.defenseSpeMax = defenseSpeMax;
    }

    public void setSpeedMin(int speedMin) {
        this.speedMin = speedMin;
    }

    public void setSpeedMax(int speedMax) {
        this.speedMax = speedMax;
    }

    @Override
    public String toString() {
        return "MonstreVM{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", hp=" + hpMin + "-" + hpMax +
                ", attack=" + attackMin + "-" + attackMax +
                ", attackSpe=" + attackSpeMin + "-" + attackSpeMax +
                ", defense=" + defenseMin + "-" + defenseMax +
                ", defenseSpe=" + defenseSpeMin + "-" + defenseSpeMax +
                ", speed=" + speedMin + "-" + speedMax +
                '}';
    }
}
