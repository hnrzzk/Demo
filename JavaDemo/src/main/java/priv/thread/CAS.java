package priv.thread;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class CAS {
    public static void main(String[] args) {
        UnsafeCAS.run();
//        AtomicCAS.run();
    }
}

class UnsafeCAS {
    int a;
    static void run() {
        try {
            UnsafeCAS a = new UnsafeCAS();
            System.out.println(a.a);
            Class<?> unsafeClass = Class.forName("jdk.internal.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            Field aField = CAS.class.getField("a");
            aField.setAccessible(true);
            long offset = unsafe.objectFieldOffset(aField);
            if (unsafe.compareAndSwapInt(a.a, offset, 1, 2)) {
                System.out.println("CAS success");
                System.out.println(a.a);
            }else {
                System.out.println("CAS failed");
            }

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

class AtomicCAS {
    AtomicInteger atomicInteger = new AtomicInteger(0);
    static void run() {
        AtomicCAS atomicCAS = new AtomicCAS();
        System.out.println(atomicCAS.atomicInteger.get());
        atomicCAS.atomicInteger.compareAndSet(0, 1);
        System.out.println(atomicCAS.atomicInteger.get());
    }
}
