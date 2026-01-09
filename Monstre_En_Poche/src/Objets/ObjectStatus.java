package Objets;

import Monstres.Monstre;
import Shared.Effects;

public class ObjectStatus extends Object {
    private Effects statusSoigne;

    public ObjectStatus(String name, Effects statusSoigne) {
        super(name);
        this.statusSoigne = statusSoigne;
    }

    @Override
    public void useObject(Monstre monstre) {
        if (monstre.getStatus() == statusSoigne) {
            monstre.setStatus(Effects.NONE);
            System.out.println(monstre.getName() + " n'est plus " + statusSoigne);
        } else {
            System.out.println("Cela n'a aucun effet sur " + monstre.getName());
        }
    }

    @Override
    public String toString() {
        return "Name=" + name + " (Type=Status)\nEffect=" + statusSoigne;
    }
}
