package holczer_balazs._005_multithreading_concepts;

import java.util.concurrent.Semaphore;

class SemaphoreExample2 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1, true); // Create a semaphore with a permit count of 1

        // Create two threads
        Thread thread1 = new Thread(new Worker(semaphore, "Thread 1"));
        Thread thread2 = new Thread(new Worker(semaphore, "Thread 2"));

        // Start the threads
        thread1.start();
        thread2.start();
    }
}

class Worker implements Runnable {
    private Semaphore semaphore;
    private String name;

    public Worker(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is waiting for a permit.");
            semaphore.acquire(); // Acquire a permit from the semaphore

            System.out.println(name + " has acquired a permit.");
            Thread.sleep(2000); // Simulate some work being done

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(name + " has released the permit.");
            semaphore.release(); // Release the permit back to the semaphore
        }
    }
}

