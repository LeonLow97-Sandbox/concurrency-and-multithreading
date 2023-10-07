package holczer_balazs._005_multithreading_concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();

        // Java 8: can create threads like this
        new Thread(deadlock::worker1, "worker1").start();
        new Thread(deadlock::worker2, "worker2").start();
    }

    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 acquires lock1...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock2.lock(); // unable to acquire lock2 because worker2 has acquired lock2
        System.out.println("Worker1 acquired lock2...");

        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        lock2.lock();
        // lock1.lock(); // use this instead to avoid cyclic dependency
        System.out.println("Worker2 acquires lock2...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock1.lock(); // unable to acquire lock1 because worker1 has acquired lock1
        // lock2.lock(); // use this instead to avoid cyclic dependency
        System.out.println("Worker2 acquired lock1...");

        lock1.unlock();
        lock2.unlock();
    }

}
