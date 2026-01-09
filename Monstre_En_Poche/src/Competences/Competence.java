package Competences;

import Competences.Enum.*;
import Shared.Effects;
import Shared.Types;
public class Competence {
    private String name;
    private Types type;
    private Categories category;
    private Integer pp;
    private Effects effect = Effects.NONE;
    private Integer effectRate = 0;
    private Integer power = 0;
    private Integer accuracy = 100;

    public void setName (String value) {
        this.name = value;
    }

    public void setType (String value) {
        // Les type sont definis, donc transformer
        switch (value) {
            case "Feu":
                this.type = Types.FEU;
                break;
        
            case "Eau":
                this.type = Types.EAU;
                break;
        
            case "Nature":
                this.type = Types.NATURE;
                break;
        
            case "Foudre":
                this.type = Types.FOUDRE;
                break;
        
            case "Terre":
                this.type = Types.TERRE;
                break;
        
            default:
                break;
        }
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

    public void setPP (Integer value) {
        this.pp = value;
    }

    public void setEffect (String value) {
        // Les effets sont definis, donc transformer
        switch (value) {
            case "Poison":
                this.effect = Effects.POISON;
                break;

            case "Frozen":
                this.effect = Effects.FROZEN;
                break;
                
            case "Burned":
                this.effect = Effects.BURNED;
                break;
        }
    }

    public void setEffectRate (Integer value) {
        this.effectRate = value;
    }

    public void setPower (Integer value) {
        this.power = value;
    }

    public void setAccuracy (Integer value) {
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

    public Integer getEffectRate() {
        return effectRate;
    }

    public Integer getPower() {
        return power;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        String out = "Name=" + this.name + "\nType=" + this.type.toString() + "\nPP=" + this.pp + "\nPower=" + this.power + "\nAccuracy=" + this.accuracy;
        if (this.effect != Effects.NONE) out = out + "\nEffect=" + this.effect.toString() + "\nEffectRate=" + this.effectRate;
        return out; 
    }
}
