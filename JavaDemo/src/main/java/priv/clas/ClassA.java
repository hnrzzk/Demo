package priv.clas;

abstract class ClassA {
    protected void methodA() {
        System.out.println("ClassA methodA");
        methodB();
    }

    protected void methodB() {
        System.out.println("ClassA methodB");
    }
}
