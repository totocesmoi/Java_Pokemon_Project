package PhaseJeu.Combat;
import Competences.Competence;
import Joueurs.Joueur;
import Monstres.Monstre;

public class AttackAction implements IAction {
    public Monstre attacker;
    public Joueur target;
    public Competence competence;

    public AttackAction(Monstre attacker, Joueur target, Competence competence) {
        this.attacker = attacker;
        this.target = target;
        this.competence = competence;
    }

    @Override public int getPriority() {
        return 3; // ou une valeur par défaut si absent
    }

    @Override public void execute() {
        attacker.attackMonster(target.getActifMonster(), competence);
    }
}