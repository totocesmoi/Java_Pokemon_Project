//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Tests.TestsMonstre tests = new Tests.TestsMonstre();
        tests.testChargementMonstres();

        System.out.println("\n--------------------------------------------------\n");

        Tests.TestsObjects testsObjects = new Tests.TestsObjects();
        testsObjects.testObjets();
    }
}