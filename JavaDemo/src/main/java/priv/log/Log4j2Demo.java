package priv.log;

import java.util.concurrent.CountDownLatch;

public class Log4j2Demo {
    public static void printLog() {
        Constant.logger.info("this is a test");
    }

    public static void performance(boolean preheat) {
        int messageSize = 1000000;
        int threadSize = 2;
        final int everySize = messageSize / threadSize;

        final CountDownLatch cdl = new CountDownLatch(threadSize);
        long startTime = System.currentTimeMillis();
        for (int ts = 0; ts < threadSize; ts++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true){
                        for (int es = 0; es < everySize; es++) {
                            printLog();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    cdl.countDown();
                }
            }).start();
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!preheat) {
            long endTime = System.currentTimeMillis();
            System.out.println("log4j1:messageSize = " + messageSize
                    + ",threadSize = " + threadSize + ",costTime = "
                    + (endTime - startTime) + "ms");
        }
    }

    public static void main(String[] args) {
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        System.setProperty("AsyncLogger.SynchronizeEnqueueWhenQueueFull", "false");
        performance(true);
        performance(true);
        performance(true);
        performance(false);
    }
}
