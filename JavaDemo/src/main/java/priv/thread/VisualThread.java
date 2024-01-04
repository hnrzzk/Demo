package priv.thread;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class VisualThread {
    public static void main(String[] args) {
        try {
            System.out.println("newFixedThreadPool====");
            print(Executors.newFixedThreadPool(256));
            System.out.println("\nnewVirtualThreadPerTaskExecutor====");
            print(Executors.newVirtualThreadPerTaskExecutor());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static AtomicInteger a = new AtomicInteger(0);
    static List<Future<Integer>> futures = new ArrayList<>();
    private static void print(ExecutorService executorService) throws ExecutionException, InterruptedException {
        a.set(0);
        futures.clear();
        var begin = System.currentTimeMillis();
        for (int i = 0; i< 1000 ; i++) {
            var future = executorService.submit(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(1));
                    return a.addAndGet(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            futures.add(future);
        }

        for (var future : futures) {
            var i = future.get();
            if (i % 100 == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println("Exec finished.");
        System.out.printf("Exec time: %dms.%n", System.currentTimeMillis() - begin);
    }
}
