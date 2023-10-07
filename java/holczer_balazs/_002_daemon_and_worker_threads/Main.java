package holczer_balazs._002_daemon_and_worker_threads;

public class Main {
    
    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        System.out.println(name); // main

        Thread t1 = new Thread(new DaemonWorker()); // daemon thread
        Thread t2 = new Thread(new NormalWorker()); // worker thread

        t1.setDaemon(true);

        t1.start();
        t2.start();
    }

}

class NormalWorker implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Normal thread finishes execution...");  
    }
}

class DaemonWorker implements Runnable {
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Daemon thread is running...");
        }
    }
}
