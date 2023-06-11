package holczer_balazs._005_multithreading_concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    /**
     * It is used to control access to a shared resource that uses a counter variable.
     * 
     * Semaphore maintains a set of permits.
     * 
     * - acquire() ... if a permit is available then takes it. - release() ... adds a permit
     * 
     * Semaphore just keeps a count of the number of permits available new Semaphore(int permits,
     * boolean fairness)
     */

    public static void main(String[] args) {
        // create multiple threads - executors
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 12; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    Downloader.INSTANCE.download();
                }
            });
        }
    }

}

// singleton pattern
enum Downloader {
    INSTANCE;

    private Semaphore semaphore = new Semaphore(3, true);

    public void download() {
        try {
            semaphore.acquire(); // given thread will acquire the permit if available
            downloadData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // return the permit to the semaphore
        }
    }

    private void downloadData() {
        try {
            System.out.println("Downloading data from the web...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
