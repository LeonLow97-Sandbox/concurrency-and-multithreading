package holczer_balazs._006_threads_with_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool {

    public static void main(String[] args) {

        // n threads in the thread pool for executing tasks
        ExecutorService executor = Executors.newFixedThreadPool(3); 

        // Simulating 10 tasks
        for (int i = 0; i < 10; i++) {
            executor.execute(new Work(i));
        }

        // Application is not terminated because we need to shutdown the executor
    }

}

class Work implements Runnable {
    private int id;

    public Work(int id) {
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
            e.printStackTrace();
        }
    }
}
