package holczer_balazs._004_thread_memory_synchronization;

public class Main {

    public static int counter1 = 0;
    public static int counter2 = 0;

    // because App object has a single lock: this is why the methods
    // cannot be executed at the same time (time-slicing algorithm)
    // usually it is not a good practice to use synchronized keyword
    public static void increment1() {

        // class level locking
        // rule of thumb: we synchronize blocks that are 100% necessary
        synchronized (Main.class) {
            counter1++;
        }

    }

    public static void increment2() {
        synchronized (Main.class) {
            counter2++;
        }
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    increment1();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    increment2();
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

        System.out.println("The counter1 is " + counter1);
        System.out.println("The counter2 is " + counter2);
    };

    public static void main(String[] args) {

        process();

    }

}
