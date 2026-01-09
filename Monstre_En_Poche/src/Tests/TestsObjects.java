package Tests;

import Monstres.Enum.Stats;
import Monstres.Monstre;
import Objets.ObjectStat;
import Objets.ObjectStatus;
import Shared.Effects;
import Shared.Types;
import java.util.ArrayList;

public class TestsObjects {
    public static void main(String[] args) {
        System.out.println("=== DEBUT DES TESTS JEU ===");

        testObjets();

        System.out.println("=== FIN DES TESTS ===");
    }

    public static void testObjets() {
        System.out.println("Début des tests des objets...");

        Monstre monstre = new Monstre("TestMonstre", Types.NORMAL, "Test", 100, 50, 50, 50, 50, 50, new ArrayList<>());
        System.out.println("Monstre initial: " + monstre.getName() + " PV=" + monstre.getPtnVie() + " Status=" + (monstre.getStatus() == null ? "NONE" : monstre.getStatus()));

        System.out.println("\n--- Test Potion (+20 PV) ---");
        ObjectStat potion = new ObjectStat("Potion", Stats.PV, 20);
        potion.useObject(monstre);
        System.out.println("Après Potion: PV=" + monstre.getPtnVie());

        System.out.println("\n--- Test Attack Boost (+10 Atk) ---");
        ObjectStat atkBoost = new ObjectStat("Atk+", Stats.ATTACK, 10);
        atkBoost.useObject(monstre);
        System.out.println("Après Boost: Atk=" + monstre.getAttack());

        // --- TESTS STATUS ---
        // 1. Test BURNED (Feu)
        System.out.println("\n--- Test Soin Brûlure (Feu) ---");
        monstre.setStatus(Effects.BURNED);
        System.out.println("Status initial: " + monstre.getStatus());
        
        ObjectStatus antiBrule = new ObjectStatus("Anti-Brûlure", Effects.BURNED);
        ObjectStatus antiPara = new ObjectStatus("Anti-Paralysie", Effects.PARALYZE);
        
        System.out.println("> Tentative avec Anti-Paralysie (Mauvais objet) :");
        antiPara.useObject(monstre);
        System.out.println("Status après mauvais objet: " + monstre.getStatus());

        System.out.println("> Tentative avec Anti-Brûlure (Bon objet) :");
        antiBrule.useObject(monstre);
        System.out.println("Status après bon objet: " + monstre.getStatus());

        // 2. Test FLOODED (Eau)
        System.out.println("\n--- Test Soin Innondation (Eau) ---");
        monstre.setStatus(Effects.FLOODED);
        ObjectStatus eponge = new ObjectStatus("Eponge", Effects.FLOODED);
        System.out.println("Status initial: " + monstre.getStatus());
        eponge.useObject(monstre);
        System.out.println("Status après Eponge: " + monstre.getStatus());

        // 3. Test PARALYZE (Foudre)
        System.out.println("\n--- Test Soin Paralysie (Foudre) ---");
        monstre.setStatus(Effects.PARALYZE);
        ObjectStatus cerise = new ObjectStatus("Cerise", Effects.PARALYZE);
        System.out.println("Status initial: " + monstre.getStatus());
        cerise.useObject(monstre);
        System.out.println("Status après Cerise: " + monstre.getStatus());

        System.out.println("\nFin des tests des objets.");
    }
}
