package holczer_balazs._005_multithreading_concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexExample {
    private static Lock mutex = new ReentrantLock(); // Creating a mutex (ReentrantLock)
    private static int counter = 0; // Shared resource

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Worker(), "Thread 1");
        Thread thread2 = new Thread(new Worker(), "Thread 2");

        thread1.start();
        thread2.start();
    }

    static class Worker implements Runnable {
        @Override
        public void run() {
            mutex.lock(); // Acquiring the mutex

            try {
                for (int i = 0; i < 5; i++) {
                    counter++; // Accessing the shared resource
                    System.out.println(Thread.currentThread().getName() + ": Counter = " + counter);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.unlock(); // Releasing the mutex
            }
        }
    }
}

