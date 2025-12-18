import Competences.*;
// javac ./Main.java && java Main
public class Main {
    public static void main(String[] args) {
        Tests.TestsMonstre tests = new Tests.TestsMonstre();
        tests.testChargementMonstres();

        System.out.println("\n--------------------------------------------------\n");

        Tests.TestsObjects testsObjects = new Tests.TestsObjects();
        testsObjects.testObjets();
    }
}