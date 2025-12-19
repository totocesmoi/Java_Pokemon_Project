package Objets;

import Monstres.Monstre;
import Monstres.Enum.Stats;

public class ObjectStat extends Object {
    private Stats stats;
    private int value;

    public ObjectStat(String name, Stats stats, int valeur) {
        super(name);
        this.stats = stats;
        this.value = valeur;
    }

    @Override
    public void useObject(Monstre monstre) {
        switch (stats) {
            case PV:
                monstre.setPtnVie(monstre.getPtnVie() + value);
                System.out.println(monstre.getName() + " restore " + value + " PV");
                break;
            case ATTACK:
                monstre.setAttack(monstre.getAttack() + value);
                System.out.println(monstre.getName() + " gagne " + value + " en Attaque");
                break;
            case ATTACK_SPE:
                monstre.setAttackSpe(monstre.getAttackSpe() + value);
                System.out.println(monstre.getName() + " increase " + value + " in AttackSpe");
                break;
            case DEFENSE:
                monstre.setDefense(monstre.getDefense() + value);
                System.out.println(monstre.getName() + " increase " + value + " in Defense");
                break;
            case DEFENSE_SPE:
                monstre.setDefenseSpe(monstre.getDefenseSpe() + value);
                System.out.println(monstre.getName() + " increase " + value + " in DefenseSpe");
                break;
            case SPEED:
                monstre.setSpeed(monstre.getSpeed() + value);
                System.out.println(monstre.getName() + " increase " + value + " in Speed");
                break;
        }
    }
}
