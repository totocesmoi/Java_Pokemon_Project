package Shared;

public enum Types {
    EAU(1),
    FEU(2),
    FOUDRE(3),
    PLANTE(4),
    INSECTE(4),
    TERRE(5),
    NORMAL(6);

    private final int familleId;
    Types(int familleId) {
        this.familleId = familleId;
    }

    public int getFamilleId() {
        return this.familleId;
    }

    public static final int FAMILLE_EAU = 1;
    public static final int FAMILLE_FEU = 2;
    public static final int FAMILLE_FOUDRE = 3;
    public static final int FAMILLE_NATURE = 4;
    public static final int FAMILLE_TERRE= 5;
    public static final int FAMILLE_NORMAL= 6;
}
