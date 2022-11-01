package priv.clas;

public class InnerClass {
    int a = 1;
    private int b = 2;
    public int c = 3;
    public static int d = 4;
    class NormalInnerClass {
        void print() {
            System.out.println("NormalInnerClass print");
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            System.out.println(d);
        }
    }

    static class StaticInnerClass {
        void print() {
            System.out.println("NormalInnerClass print");
//            System.out.println(a);
//            System.out.println(b);
//            System.out.println(c);
            System.out.println(d);
        }
    }

    NormalInnerClass getNormalInnerClass() {
        return new NormalInnerClass();
    }


    public static void main(String[] args) {
        InnerClass innerClassObj = new InnerClass();
        innerClassObj.a *= -1;
        InnerClass.d += 1;
        NormalInnerClass normalInnerClass = innerClassObj.getNormalInnerClass();
        normalInnerClass.print();

        InnerClass.d += 1;
        StaticInnerClass staticInnerClass = new InnerClass.StaticInnerClass();
        staticInnerClass.print();
    }
}
