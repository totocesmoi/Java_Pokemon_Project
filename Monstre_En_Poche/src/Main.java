import Competences.*;
// javac ./Main.java && java Main
public class Main {
    public static void main(String[] args) {
        CollectionCompetence comp = new CollectionCompetence();
        comp.parser();
        for (Competence competence : comp.competences) {
            System.out.println(competence.toString() + "\n-----");
        }
    }
}