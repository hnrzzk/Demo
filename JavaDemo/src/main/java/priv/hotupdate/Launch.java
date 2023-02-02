package priv.hotupdate;

import priv.hotupdate.analyzer.ClassAnalyzeResult;
import priv.hotupdate.analyzer.IClassAnalyzer;
import priv.hotupdate.analyzer.Jdk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.List;

public class Launch {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            HotUpdateSimpleObj simpleObj = new HotUpdateSimpleObj();
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    if (count == 3) {
                        String classPath = "F:\\HotUpdateSimpleObj.class";
                        System.out.println("load file from " + classPath);
                        reloadClass(classPath);
                    }
                    System.out.println("####################");
                    System.out.print("old obj:");
                    simpleObj.print();

                    System.out.print("new obj:");
                    HotUpdateSimpleObj newObj = new HotUpdateSimpleObj();
                    newObj.print();
                    count++;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
        thread.join();
    }

    public static void reloadClass(String classPath) {
        File file = new File(classPath);
        if (!file.exists()) {
            return;
        }

        List<ClassDefinition> classDefinitionList = new ArrayList<>();
        if (file.isFile()) {
            classDefinitionList.add(generateClassDefinition(file));
        } else if (file.isDirectory()) {
            File[] classFiles = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File tmpFile) {
                    return tmpFile.isFile() && tmpFile.getName().endsWith(".class");
                }
            });
            if (classFiles == null) {
                return;
            }
            for (File dirFile : classFiles) {
                classDefinitionList.add(generateClassDefinition(file));
            }
        }
        InstrumentHotUpdate.reload(classDefinitionList);
    }

    private static ClassDefinition generateClassDefinition(File file) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            IClassAnalyzer classAnalyzer = Jdk.getCurClassAnalyzer();
            if (null == classAnalyzer) {
                return null;
            }
            ClassAnalyzeResult classAnalyzeResult = classAnalyzer.analyze(file);
            String className = classAnalyzeResult.getQualifiedName();
            Class<?> clasz = classLoader.loadClass(className);
            byte[] bs = toByteArray(file);
            return new ClassDefinition(clasz, bs);
        } catch (Exception e) {
            System.out.println("load class failed!");
            return null;
        }
    }

    /**
     * 读取文件数据
     *
     * @param file
     * @return
     */
    public static byte[] toByteArray(File file) {
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[(int) file.length()];
            input.read(buffer);
            return buffer;
        } catch (Exception e) {
            System.out.println("read file failed!");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
