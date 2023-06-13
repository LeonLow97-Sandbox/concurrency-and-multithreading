package holczer_balazs._007_concurrent_collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {

    /**
     * It implements the BlockingQueue interface
     * 
     * - unbounded concurrent queue
     * - it uses the same ordering rules as the `PriorityQueue` class and have to
     * implement the `Comparable` interface.
     * 
     * it determines what will the order be in the queue.
     * 
     * priority can be the same compare() == 0 case
     * 
     * - no null items are allowed
     * 
     */

    public static void main(String[] args) {

        BlockingQueue<String> queue = new PriorityBlockingQueue<>();

        FirstWorker first = new FirstWorker(queue);
        SecondWorker second = new SecondWorker(queue);

        new Thread(first).start();
        new Thread(second).start();

    }

}

class FirstWorker implements Runnable {

    private BlockingQueue<String> queue;

    public FirstWorker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("B");
            queue.put("H");
            queue.put("F");
            Thread.sleep(2000);
            queue.put("A");
            Thread.sleep(1000);
            queue.put("Z");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class SecondWorker implements Runnable {

    private BlockingQueue<String> queue;

    public SecondWorker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(queue.take());
            Thread.sleep(1000);
            System.out.println(queue.take());
            Thread.sleep(2000);
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}