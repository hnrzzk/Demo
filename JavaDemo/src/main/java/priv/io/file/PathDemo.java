package priv.io.file;

import java.net.URL;

public class PathDemo {
    public static void main(String[] args) {
        System.out.println("###Get user.dir: "+System.getProperty("user.dir"));
        System.out.println("###Get java.class.path: "+System.getProperty("java.class.path"));
        System.out.println("###Get Resource: " + PathDemo.class.getResource(""));
        System.out.println("###Get Root Resource: " + PathDemo.class.getResource("/"));
        System.out.println("###Get ClassLoader Resource: " + PathDemo.class.getClassLoader().getResource(""));
        System.out.println("###Get Root ClassLoader Resource: " + PathDemo.class.getClassLoader().getResource("/"));
        System.out.println("###Get ContextClassLoader Resource: " + Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println("###Get Root ContextClassLoader Resource: " + Thread.currentThread().getContextClassLoader().getResource("/"));
    }

    static final String JAVA_INNER_SEPARATOR = "!/";
    static final String JAVA_SEPARATOR = "/";
    public static String getRootPath() {
        URL rootURL = Thread.currentThread().getContextClassLoader().getResource("");
        if (null == rootURL) {
            return null;
        }
        String rootPath = rootURL.getPath();
        int innerSeparatorIndex = rootPath.indexOf(JAVA_INNER_SEPARATOR);
        if (-1 == innerSeparatorIndex) {
            return rootPath;
        }

        String jarPath = rootPath.substring(0, innerSeparatorIndex);
        int lastFileSeparatorIndex = jarPath.lastIndexOf(JAVA_SEPARATOR);
        if (-1 == lastFileSeparatorIndex) {
            return null;
        }
        return jarPath.substring(0, lastFileSeparatorIndex);
    }
}
