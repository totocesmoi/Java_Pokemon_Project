package PhaseJeu.Combat;

public interface IAction {
    int getPriority();
    void execute(); // ou ManageCombat selon votre architecture
}