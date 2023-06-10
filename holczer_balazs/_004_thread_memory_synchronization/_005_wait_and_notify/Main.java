package holczer_balazs._004_thread_memory_synchronization._005_wait_and_notify;

public class Main {

    public static void main(String[] args) {
        Process process = new Process();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

}


class Process {

    public void produce() throws InterruptedException {
        // acquiring the intrinsic lock of Process object
        synchronized (this) {
            System.out.println("Running the produce method...");
            wait(); // Releases the lock and waits until another thread calls notify() or notifyAll()
            System.out.println("Again in the producer method...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);

        // acquiring the same intrinsic lock of Process object
        synchronized (this) {
            System.out.println("Consume method is executed...");
            notify(); // notifies the waiting thread that it can continue its execution by acquiring the intrinsic lock
            // notify is not going to handle the lock: we can make further operations
            // Java will execute whatever operations below in this synchronized block before
            // going back to the waiting thread
            Thread.sleep(5000);
        }
    }

}
