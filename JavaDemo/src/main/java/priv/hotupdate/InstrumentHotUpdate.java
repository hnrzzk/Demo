package priv.hotupdate;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

public final class InstrumentHotUpdate {

    private static Instrumentation INSTRUMENT = null;
    public static void premain(String agentArgs, Instrumentation ins) {
        System.out.println("init INSTRUMENT");
        INSTRUMENT = ins;
    }

    public static void reload(List<ClassDefinition> classDefinitions) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        reload(classDefinitions, classLoader);
    }

    public static void reload(List<ClassDefinition> classDefinitions, ClassLoader classLoader) {
        if (INSTRUMENT == null) {
            throw new RuntimeException("reload failed! Instrumentation instance not found.");
        }
        if (classDefinitions == null || classDefinitions.isEmpty()) {
            return;
        }

        try {
            INSTRUMENT.redefineClasses(classDefinitions.toArray(new ClassDefinition[0]));
        } catch (ClassNotFoundException | UnmodifiableClassException e) {
            throw new RuntimeException(e);
        }
    }
}
