package holczer_balazs._005_multithreading_concepts;

public class Volatile {

    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread t1 = new Thread(worker);
        t1.start();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.setTerminated(true);
        System.out.println("Algorithm is terminated...");
    }

}

class Worker implements Runnable {

    // the variable will be stored in the main memory
    // 1.) variables can be stored on the main memory without the volatile keyword
    // 2.) both of the threads may use the same cache (as they share the same CPU or CPU core)
    // `volatile` guarantees pushing to RAM, but pushing to RAM can happen even without volatile.
    private volatile boolean terminated;

    @Override
    public void run() {

        while (!terminated) {
            System.out.println("Working class is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
}
