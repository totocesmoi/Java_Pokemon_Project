package Shared;

import java.util.Scanner;

public class ReadInt {
    
    public static int readInt(Scanner scanner, int min, int max) {
        while (true) {
            String line = scanner.nextLine();
            try {
                int val = Integer.parseInt(line.trim());
                if (val < min || val > max) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre entre " + min + " et " + max + " :");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier :");
            }
        }
    }
}
