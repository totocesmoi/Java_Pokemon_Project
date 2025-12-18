import Tests.*;

// javac ./Main.java && java Main
public class Main {
    public static void main(String[] args) {
        TestsMonstre tests = new TestsMonstre();
        tests.testChargementMonstres();

        TestsCompetence testsCompetence = new TestsCompetence();
        testsCompetence.testChargementCompetence();

        /*
        System.out.println("\n--------------------------------------------------\n");

        TestsObjects testsObjects = new TestsObjects();
        testsObjects.testObjets();
        */
    }
}