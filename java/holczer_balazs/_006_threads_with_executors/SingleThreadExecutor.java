package holczer_balazs._006_threads_with_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleThreadExecutor {

    public static void main(String[] args) {
        // it is a single thread that will execute the tasks sequentially
        // so one after another, the thread id is the same.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Simulating 5 tasks
        for (int i = 0; i < 5; i++) {
            executor.execute(new Task(i));
        }

        // Application is not terminated because we need to shutdown the executor
    }

}

class Task implements Runnable {
    private int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with id " + id + " is in work - thread id: "
                + Thread.currentThread().getName());
        long duration = (long) (Math.random() * 5);

        // Another way to sleep threads
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
