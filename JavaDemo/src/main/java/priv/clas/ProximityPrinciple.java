package priv.clas;

/**
 * 就近原则
 */
public class ProximityPrinciple {

    public static void main(String[] args) {
        ClassA classA = new ClassAA();
        classA.methodA();

        ClassAA classAA = new ClassAA();
        classAA.methodA();
    }
}
