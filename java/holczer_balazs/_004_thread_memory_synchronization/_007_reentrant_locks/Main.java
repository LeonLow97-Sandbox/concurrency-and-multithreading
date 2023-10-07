package holczer_balazs._004_thread_memory_synchronization._007_reentrant_locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    private static void increment() {

        lock.lock(); // given thread acquires the lock

        try {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        } finally {
            lock.unlock(); // given thread releases the lock
            // unlock();
        }

    }

    // public static void unlock() {
    //     lock.unlock();
    // }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
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

        System.out.println("Counter: " + counter);

    }
}
