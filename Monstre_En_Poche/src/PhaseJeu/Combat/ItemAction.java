package PhaseJeu.Combat;
import Joueurs.Joueur;

public class ItemAction implements IAction {
    private final Joueur player;
    private final int item; // class existante: Object or ObjectStat/ObjectStatus
    
    public ItemAction(Joueur joueur, int item) {
        this.player = joueur;
        this.item = item;
    }
    
    @Override public int getPriority() {
        return 2; // ou une valeur par défaut si absent
    }

    @Override public void execute() {
        player.useObject(item, player.getActifMonster());
    }
}