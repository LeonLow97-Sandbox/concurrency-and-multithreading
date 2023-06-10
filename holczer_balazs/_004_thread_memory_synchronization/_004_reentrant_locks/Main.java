package holczer_balazs._004_thread_memory_synchronization._004_reentrant_locks;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    
    public static void main(String[] args) {
        Counter counter = new Counter();
        counter.increment();
        int count = counter.getCount();
        System.out.println("Counter: " + count);
    }

}

class Counter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // acquire the lock
        try {
            count++;
        } finally {
            lock.unlock(); // release the lock
        }
    }

    public int getCount() {
        lock.lock(); // acquire the lock
        try {
            return count;
        } finally {
            lock.unlock(); // release the lock
        }
    }
}
