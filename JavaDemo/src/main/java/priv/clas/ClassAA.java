package priv.clas;

public class ClassAA extends ClassA{
    @Override
    protected void methodB() {
        super.methodB();
        System.out.println("ClassAA methodB");
    }
}
