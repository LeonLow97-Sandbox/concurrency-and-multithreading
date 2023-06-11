package holczer_balazs._004_thread_memory_synchronization._008_rentrant_locks_with_producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    static LockWorker lockWorker = new LockWorker();

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lockWorker.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lockWorker.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class LockWorker {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock(); // acquire the lock
        System.out.println("Producer method...");

        condition.await(); // wait()

        System.out.println("Again the producer method...");
    }

    public void consume() throws InterruptedException {
        // make sure that we start with the producer()
        Thread.sleep(2000);

        lock.lock(); // acquire the lock
        System.out.println("Consumer method...");

        Thread.sleep(3000);

        condition.signal(); // notify() - wakes up the waiting thread
        lock.unlock(); // releases the given lock
    }
}