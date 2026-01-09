package Shared;

public class Extension {
    public static Types setType(String value) {
        return switch (value) {
            case "Feu" -> Types.FEU;
            case "Eau" -> Types.EAU;
            case "Nature" -> Types.PLANTE;
            case "Plante" -> Types.PLANTE;
            case "Insecte" -> Types.INSECTE;
            case "Foudre" -> Types.FOUDRE;
            default -> null;
        };
    }
}
