package Objets;

import Monstres.Monstre;
import Monstres.Status;

public class ObjectStatus extends Object{
    private Status statusSoigne;

    public ObjectStatus(String name, Status statusSoigne) {
        super(name);
        this.statusSoigne = statusSoigne;
    }

    @Override
    public void useObject(Monstre monstre) {
        if (monstre.getStatus() == statusSoigne) {
            monstre.setStatus(Status.NONE);
            System.out.println(monstre.getName() + " n'est plus " + statusSoigne);
        } else {
            System.out.println("Cela n'a aucun effet sur " + monstre.getName());
        }
    }
}
