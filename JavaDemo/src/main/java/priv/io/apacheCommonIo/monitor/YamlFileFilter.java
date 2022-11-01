package priv.io.apacheCommonIo.monitor;

import java.io.File;
import java.io.FileFilter;

public class YamlFileFilter implements FileFilter {

    private String name = null;

    public YamlFileFilter() {

    }

    public YamlFileFilter(String name) {
        this.name = name;
    }

    @Override
    public boolean accept(File pathname) {
        System.out.println("YamlFileFilter accept " + pathname);
        if (pathname.isDirectory()) {
            return false;
        }
        String[] nameSplits = pathname.getName().split(File.separator);
        String name = nameSplits[nameSplits.length -1];
        return name.endsWith(".yaml") && name.contains("name");
    }
}
