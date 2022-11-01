package priv.io.apacheCommonIo.monitor;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitor {
    FileAlterationMonitor fileAlterationMonitor ;

    public FileMonitor(String directorPath, String fileName) {
        fileAlterationMonitor = new FileAlterationMonitor();
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(directorPath, new NameFileFilter(fileName));
        fileAlterationObserver.addListener(new FileReloadListener());
        fileAlterationMonitor.addObserver(fileAlterationObserver);
    }

    public void start() {
        try {
            fileAlterationMonitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            fileAlterationMonitor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
