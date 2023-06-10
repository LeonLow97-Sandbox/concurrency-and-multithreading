package holczer_balazs._004_thread_memory_synchronization;

public class Main {

    public static int counter = 0;

    // Have to ensure that this method is executed only by a single thread at a given time
    // use the keyword `synchronized`
    public static synchronized void increment() {
        counter++;
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The counter is " + counter);
    };

    public static void main(String[] args) {

        process();

    }

}
