package priv.io.path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassPathDemo {
    private static List<Class<?>> getClassesFromJar(String jarPath, String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (name.endsWith(".class") && name.startsWith(packageName.replace('.', '/'))) {
                String className = name.substring(0, name.length() - 6).replace('/', '.');
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            }
        }
        jarFile.close();
        return classes;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        for (Class<?> clz : getClassesFromJar("D:\\WorkSpace\\Demo\\JavaDemo\\target\\javaDemo-1.0-SNAPSHOT.jar", "priv.io")) {
            System.out.println(clz.getName());
        }
    }
}
