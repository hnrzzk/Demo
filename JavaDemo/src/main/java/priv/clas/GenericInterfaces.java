package priv.clas;

import java.lang.reflect.Type;
import java.util.Arrays;

public class GenericInterfaces {
    public static void main(String[] args) {
        Type[] types = ClassA.class.getGenericInterfaces();
        System.out.println(Arrays.toString(types));
    }
}
