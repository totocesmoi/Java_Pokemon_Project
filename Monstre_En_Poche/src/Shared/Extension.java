package Shared;

public class Extension {
    public static Types setType(String value) {
        return switch (value) {
            case "Feu" -> Types.FEU;
            case "Eau" -> Types.EAU;
            case "Nature" -> Types.NATURE;
            case "Foudre" -> Types.FOUDRE;
            case "Terre" -> Types.TERRE;
            default -> null;
        };
    }
}
