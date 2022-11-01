package priv.io.apacheCommonIo.monitor;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileReloadListener implements FileAlterationListener {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        System.out.println("listener start.");
    }

    @Override
    public void onDirectoryCreate(File file) {
        System.out.println("dir " + file.getName() + " create.");
    }

    @Override
    public void onDirectoryChange(File file) {
        System.out.println("dir " + file.getName() + " change.");
    }

    @Override
    public void onDirectoryDelete(File file) {
        System.out.println("dir " + file.getName() + " delete.");
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("file " + file.getName() + " create.");
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("file " + file.getName() + " change.");
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("file " + file.getName() + " delete.");
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        System.out.println("listener stop.");
    }
}
