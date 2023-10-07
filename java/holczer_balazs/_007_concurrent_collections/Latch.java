package holczer_balazs._007_concurrent_collections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Latch
 * 
 * Used to synchronize 1 or more tasks by forcing them to wait for the completion of a set of
 * operations being performed by other tasks.
 * 
 * - give an initial count to a CountDownLatch object, and any task that calls await() on that
 * object will be blocked until the count reaches 0
 * 
 * - Other tasks may call countDown() on the object to reduce the count, presumably when a task
 * finishes its job
 * 
 * - CountDownLatch is designed to be used in a one-shot fashion and the count cannot be reset. - If
 * you need a version that resets the count, use a `CyclicBarrier` instead
 * 
 * - Tasks that call countDown() are not blocked when they make that call. Only the call to await()
 * is blocked until the count reaches 0.
 * 
 * A typical use case is to divide a problem into N independently solvable tasks and create a
 * CountDownLatch with a value of N.
 * 
 * When each task is finished, it calls countDown() on the latch. Tasks waiting for the problem to
 * be solved calls await() on the latch to hold themselves back until it is completed.
 */

public class Latch {

    public static void main(String[] args) {
        // the `count` value is either equal to or smaller than the number of tasks
        // if the count = 10 and the number of tasks is 5, then the latch count will never reach 0
        // so `latch.await()` freezes and waits forever.
        CountDownLatch latch = new CountDownLatch(5); // count = 5
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            service.execute(new Worker(i, latch));
        }

        try {
            // main thread waits for the latch count to reach 0 before continuing execution
            // main thread waits for other threads
            latch.await(); // if the count is not 0, then the latch is going to wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks have been finished...");

        service.shutdown();
    }

}


class Worker implements Runnable {

    private int id;
    private CountDownLatch latch;

    public Worker(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        doWork();
        latch.countDown(); // worker decrements the value of the count in the latch by 1
    }

    public void doWork() {
        try {
            System.out.println("Thread with ID " + this.id + " starts working...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
