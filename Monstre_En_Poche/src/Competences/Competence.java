package Competences;

import Competences.Enum.*;
import Shared.Effects;
import Shared.Types;
public class Competence {
    private String name;
    private Types type;
    private Categories category;
    private int pp;
    private Effects effect = Effects.NONE;
    private int effectRate = 0;
    private int power = 0;
    private int accuracy = 100;

    public void setName (String value) {
        this.name = value;
    }

    public void setType (Types value) {
        this.type = value;
    }

    public void setCategory (Categories value) {
        this.category = value;
    }

    public void setCategory (String value) {
        // Il faut que le string devienne une categorie
        if (value == "Status") 
            this.category = Categories.STATUS;
        if (value == "Physique") 
            this.category = Categories.PHYSIQUE;
        if (value == "Special") 
            this.category = Categories.SPECIAL;
    }

    public void setPP (int value) {
        this.pp = value;
    }

    public void setEffect (Effects value) {
        this.effect = value;
    }

    public void setEffect (String value) {
        // Les effets sont definis, donc transformer
        switch (value) {
            case "Hidden":
                this.effect = Effects.HIDDEN;
                break;

            case "Paralyze":
                this.effect = Effects.PARALYZE;
                break;

            case "Flooded" :
                this.effect = Effects.FLOODED;
                break;

            case "Restore" :
                this.effect = Effects.RESTORE;
                break;
                
            case "Burned":
                this.effect = Effects.BURNED;
                break;
        }
    }

    public void setEffectRate (int value) {
        this.effectRate = value;
    }

    public void setPower (int value) {
        this.power = value;
    }

    public void setAccuracy (int value) {
        this.accuracy = value;
    }

    public String getName() {
        return name;
    }

    public Types getType() {
        return type;
    }

    public Categories getCategory() {
        return category;
    }

    public Effects getEffect() {
        return effect;
    }

    public int getEffectRate() {
        return effectRate;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getPp() {return pp;}

    @Override
    public String toString() {
        String out = "Name=" + this.name + "\nType=" + this.type.toString() + "\nPP=" + this.pp + "\nPower=" + this.power + "\nAccuracy=" + this.accuracy;
        if (this.effect != Effects.NONE) out = out + "\nEffect=" + this.effect.toString() + "\nEffectRate=" + this.effectRate;
        return out; 
    }
}
