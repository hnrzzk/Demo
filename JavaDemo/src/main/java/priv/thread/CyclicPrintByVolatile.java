package priv.thread;

/**
 *  循环打印的要点：
 *  1.通过一个信号n来判断是否打印。每次打印完后更改信号。两个线程可用boolean，多个线程可根据 n % thread.count ==0来判断
 *  2.信号的更改需要保证线程安全：
 *      volatile
 *      synchronized
 *      ReentrantLock
 */
public class CyclicPrintByVolatile {
    public static void main(String[] args) {
        Single single = new Single();
        new Thread(new TaskA(single), "taskA").start();
        new Thread(new TaskB(single), "taskB").start();
    }

}

class Single {
    volatile boolean single = false;

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}

class TaskA implements Runnable{
    final Single single;
    TaskA(Single single){
        this.single = single;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            synchronized (single){
                while (single.isSingle()){
                    try {
                        single.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(String.format("%s print %d", Thread.currentThread().getName(), i));
                single.setSingle(true);
                single.notify();
            }

        }
    }
}

class TaskB implements Runnable{
    final Single single;
    TaskB(Single single){
        this.single = single;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            synchronized (single) {
                while (!single.isSingle()) {
                    try {
                        single.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(String.format("%s print %d", Thread.currentThread().getName(), i));
                single.setSingle(false);
                single.notify();
            }

        }
    }
}
