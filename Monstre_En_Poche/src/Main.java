import Competences.*;

// javac ./Main.java && java Main

public class Main {
    public static void main(String[] args) {
        Competence comp = new Competence();
        comp.parser();

        System.out.print("Name : " + comp.getName() + ", Type : " + comp.getType() + ", Atk : " + comp.getAttack() + ", AtkSpe : " + comp.getAttackSpe());
    }
}