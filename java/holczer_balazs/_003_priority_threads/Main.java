package holczer_balazs._003_priority_threads;

public class Main {

    public static void main(String[] args) {

        // Thread with priority 10
        Thread t = new Thread(new WorkerThread());

        t.setPriority(Thread.MAX_PRIORITY);
        t.start();

        // Main thread with priority 5
        System.out.println("This is in the main thread...");

        // System.out.println(Thread.currentThread().getName()); // main
        // System.out.println(Thread.currentThread().getPriority()); // 5

        // Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        // System.out.println(Thread.currentThread().getPriority()); // 10
    }

}

class WorkerThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
