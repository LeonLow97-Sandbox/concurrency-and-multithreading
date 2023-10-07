package holczer_balazs._007_concurrent_collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Queue has a FIFO structure (First In First Out) but it is not a
 * synchronized data structure
 * 
 * BlockingQueue -> an interface that represents a queue that is thread safe
 *  Put items or take items from it ...
 * 
 * For example, 1 thread putting items into the queue and another thread taking items from it at the same time.
 * 
 *  Can do the Producer-Consumer pattern
 * 
 * put() inserts items into the queue
 * take() removes items from the queue
 */

public class BlockingQueueExample {
    
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10); // storing 10 items in the underlying data structure

        FirstWorker firstWorker = new FirstWorker(queue);
        SecondWorker secondWorker = new SecondWorker(queue);

        new Thread(firstWorker).start();
        new Thread(secondWorker).start();
    }

}

class FirstWorker implements Runnable {

    private BlockingQueue<Integer> queue;

    public FirstWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    
    @Override
    public void run() {
        
        int counter = 0;

        while (true) {
            try {
                queue.put(counter);
                System.out.println("Putting item into the queue... " + counter);
                counter++;
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class SecondWorker implements Runnable {

    private BlockingQueue<Integer> queue;
    
    public SecondWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    
    @Override
    public void run() {

        while (true) {
            try {
                int counter = queue.take(); // this line is different
                System.out.println("Taking item from the queue... " + counter);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}