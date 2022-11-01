package priv.collection;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockQueue blockQueue = new BlockQueue();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        new Thread(()->{
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < 10; i++){
                blockQueue.offer(i);
            }
        }).start();

        new Thread(()->{
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 10; i < 20; i++){
                blockQueue.offer(i);
            }
        }).start();

        new Thread(()->{
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 20; i < 30; i++){
                blockQueue.offer(i);
            }
        }).start();

        new Thread(()->{
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true){
                try {
                    System.out.println(Thread.currentThread().getName() + "read:" + blockQueue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "reader 1").start();

        new Thread(()->{
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true){
                try {
                    System.out.println(Thread.currentThread().getName() + "read:" + blockQueue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "reader 2").start();
    }
}
class BlockQueueCondition {
    ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    int[] data;
    AtomicInteger head = new AtomicInteger();
    AtomicInteger tail = new AtomicInteger();
    AtomicInteger size = new AtomicInteger();
    BlockQueueCondition(int size){
        data = new int[size];
    }

    public void offer(int a){
        try {
            lock.lock();
            while (isFull()){
                notFull.await();
            }
            data[tail.get()] = a;
            tail.set(tail.addAndGet(1) % data.length);
            size.addAndGet(1);
            notEmpty.signal();
        }catch (InterruptedException ignored) {
        }finally {
            lock.unlock();
        }
    }

    public int poll() throws InterruptedException {
        try {
            lock.lock();
            while (isEmpty()){
                notEmpty.await();
            }
            int result = data[head.get()];
            head.set(head.addAndGet(1)%data.length);
            size.decrementAndGet();
            notFull.signal();
            return result;
        }finally {
            lock.unlock();
        }
    }

    private boolean isFull(){
        return size.get() == data.length;
    }
    public boolean isEmpty(){
        return size.get() == 0;
    }
}

class BlockQueue{
    final Object offerLock = new Object();
    final Object popLock = new Object();
    int[] data;
    volatile int head, tail, size;
    BlockQueue(){
        data = new int[10];
    }

    /**
     * 添加数据
     * @param item
     */
    public boolean offer(int item){
        synchronized (offerLock){
            while(isFull()){
                try {
                    System.out.println("wait not full");
                    offerLock.wait();
                } catch (InterruptedException e) {
                    return false;
                }
            }
            data[tail] = item;
            tail = (tail + 1) % data.length;
            ++size;
        }
        synchronized (popLock){
            notify();
        }
        return true;
    }

    public int poll() throws InterruptedException {
        int result;
        synchronized (popLock){
            while (isEmpty()){
                System.out.println("wait not empty");
                popLock.wait();
            }
            result = data[head];
            head = (head + 1) % data.length;
            --size;
        }
        synchronized (offerLock){
            offerLock.notify();
        }
        return result;
    }

    private boolean isEmpty(){
        return size == 0;
    }

    private boolean isFull(){
        return size == data.length;
    }
}