package holczer_balazs._007_concurrent_collections;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Latch -> a single thread can wait for other threads
 * CyclicBarrier -> multiple threads can wait for each other
 * 
 * A CyclicBarrier is used in situations where you want to create a group of tasks
 * to perform work in a concurrent manner and wait until they are all finished before
 * moving on to the next step
 *          -> something like join()
 *          -> something like CountDownLatch()
 * 
 * CountDownLatch: one-shot event
 * CyclicBarrier: can be reused over and over again
 * 
 *          + cyclicBarrier has a barrier action: a runnable, that will run
 *              automatically when the count reaches 0
 * 
 * new CyclicBarrier(N) -> N threads will wait for each other
 * 
 * Cannot reuse latches but can reuse CyclicBarriers --> reset(), not recommended
 * as it often throws the BrokenBarrierException
 */

public class CyclicBarrierExample {
    
    public static void main(String[] args) {
        
        ExecutorService service = Executors.newFixedThreadPool(5);
        // 5 is the number of threads
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
           @Override
           public void run() {
                // CyclicBarrier runs this when all the 5 tasks have finished running
               System.out.println("All tasks have been finished...");
           } 
        });

        for (int i = 0; i < 5; i++) {
            service.execute(new Worker(i+1, barrier)); // executes the run() method of the Worker instance
        }

        service.shutdown();

    }

}

class Worker implements Runnable {

    private int id;
    private Random random;
    private CyclicBarrier barrier;

    public Worker(int id, CyclicBarrier barrier) {
        this.id = id;
        this.random = new Random();
        this.barrier = barrier;
    }
    
    @Override
    public void run() {
        doWork();
    }

    private void doWork() {
        System.out.println("Thread with ID " + this.id + " starts the work...");

        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // when all the threads call await(), this is when
            // the "barrier" is broken
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("After await()...");
    }

}