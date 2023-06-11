package holczer_balazs._004_thread_memory_synchronization._006_producer_and_consumer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Processor processor = new Processor();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

}


class Processor {

    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 2;
    private static final int LOWER_LIMIT = 0;
    private int value = 0;

    private final Object lock = new Object();

    public void producer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for removing items...");
                    // lock.wait() because we synchronized on the `lock`
                    lock.wait(); // if just wait() means we are waiting on the Processor itself
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    lock.notify();
                    // do other operations!
                }
                Thread.sleep(500);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting for adding items...");
                    value = 0;
                    lock.wait();
                } else {
                    System.out.println("Removing: " + list.remove(list.size() - 1));
                    lock.notify(); // does not call the producer thread immediately
                    // will perform further operations in the while loop
                    // only when it has called notify() and wait() then the other thread will be awake
                }
                Thread.sleep(500);
            }
        }
    }

}
