package PhaseJeu.Combat;
import Joueurs.Joueur;

public class SwitchAction implements IAction {
    private final Joueur player;
    private final int newMonstre;
    
    public SwitchAction(Joueur joueur, int swapTo) {
        this.player = joueur;
        this.newMonstre = swapTo;
    }
    
    @Override public int getPriority() {
        return 1; // ou une valeur par défaut si absent
    }
    
    @Override public void execute() {
        player.changeMonster(newMonstre);
    }
}