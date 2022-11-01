package priv.io.apacheCommonIo;

import priv.io.apacheCommonIo.monitor.FileMonitor;

public class FileDemo {
    public static void main(String[] args) throws InterruptedException {
        String resourceDir = "E:\\WorkSpace\\Java\\JavaDemo\\src\\main\\resources";
        FileMonitor monitor = new FileMonitor(resourceDir, "test.yaml");
        monitor.start();
        Thread.sleep(60 * 1000);
    }
}
