package holczer_balazs._006_threads_with_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutdownExecutorService {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10); 

        // Simulating 10 tasks
        for (int i = 0; i < 100; i++) {
            executor.execute(new Work1(i));
        }

        // Application is not terminated because we need to shutdown the executor
        // executor service will not execute any further tasks
        executor.shutdown(); 

        // terminate actual (running) tasks
        try {
            // Giving the executor 1 second to gracefully shutdown all actively executing tasks
            // if pass 1 second, it will just shutdown
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                // executor.shutdownNow(); // stops all actively executing tasks
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

}

class Work1 implements Runnable {
    private int id;

    public Work1(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with id " + id + " is in work - thread id: "
                + Thread.currentThread().getId());
        long duration = (long) (Math.random() * 5);

        // Another way to sleep threads
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // interrupts the given thread
        }
    }
}

