package Tests;

import Monstres.Monstre;
import Monstres.Enum.Status;
import Monstres.Enum.Stats;
import Objets.ObjectStat;
import Objets.ObjectStatus;
import java.util.ArrayList;

public class TestsObjects {
    public void testObjets() {
        System.out.println("Début des tests des objets...");

        Monstre monstre = new Monstre("TestMonstre", "Normal", "Test", 100, 50, 50, 50, 50, 50, new ArrayList<>());
        System.out.println("Monstre initial: " + monstre.getName() + " PV=" + monstre.getPtnVie() + " Status=" + (monstre.getStatus() == null ? "NONE" : monstre.getStatus()));

        System.out.println("\n--- Test Potion (+20 PV) ---");
        ObjectStat potion = new ObjectStat("Potion", Stats.PV, 20);
        potion.useObject(monstre);
        System.out.println("Après Potion: PV=" + monstre.getPtnVie());

        System.out.println("\n--- Test Attack Boost (+10 Atk) ---");
        ObjectStat atkBoost = new ObjectStat("Atk+", Stats.ATTACK, 10);
        atkBoost.useObject(monstre);
        System.out.println("Après Boost: Atk=" + monstre.getAttack());

        System.out.println("\n--- Test Antidote (Soigne Poison) ---");
        monstre.setStatus(Status.POISON);
        System.out.println("Monstre empoisonné: Status=" + monstre.getStatus());
        
        ObjectStatus antidote = new ObjectStatus("Antidote", Status.POISON);
        antidote.useObject(monstre);
        System.out.println("Après Antidote: Status=" + monstre.getStatus());

        System.out.println("\n--- Test Antidote sur Brûlure (Ne doit pas marcher) ---");
        monstre.setStatus(Status.BURNT);
        System.out.println("Monstre brûlé: Status=" + monstre.getStatus());
        antidote.useObject(monstre);
        System.out.println("Après Antidote sur Brûlure: Status=" + monstre.getStatus());

        System.out.println("\nFin des tests des objets.");
    }
}
