package holczer_balazs._005_multithreading_concepts;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Livelock {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {

        Livelock liveLock = new Livelock();

        new Thread(liveLock::worker1, "worker1").start();;
        new Thread(liveLock::worker2, "worker2").start();

    }

    private void worker1() {

        while(true) {
            try {
                lock1.tryLock(50, TimeUnit.MILLISECONDS);
                System.out.println("worker1 acquires lock1...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("worker1 tries to acquire lock2...");

            if (lock2.tryLock()) {
                System.out.println("worker1 acquires lock2...");
                lock2.unlock();
            } else {
                System.out.println("worker1 cannot acquire lock2...");
                continue;
            }
            break;
        }

        lock1.unlock();
        lock2.unlock();

    }

    private void worker2() {

        while(true) {
            try {
                lock2.tryLock(50, TimeUnit.MILLISECONDS);
                System.out.println("worker2 acquires lock2...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("worker2 tries to acquire lock1...");

            if (lock1.tryLock()) {
                System.out.println("worker2 acquires lock1...");
                lock1.unlock();
            } else {
                System.out.println("worker2 cannot acquire lock1...");
                continue;
            }
            break;
        }

        lock1.unlock();
        lock2.unlock();

    }

}
