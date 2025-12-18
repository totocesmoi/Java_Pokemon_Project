package Objets;

import Monstres.Monstre;

public abstract class Object {
    protected String name;

    public Object(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void useObject(Monstre monstre);
}
