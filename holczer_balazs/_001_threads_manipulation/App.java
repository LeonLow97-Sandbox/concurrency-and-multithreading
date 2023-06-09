package holczer_balazs._001_threads_manipulation;

public class App {

    public static void main(String[] args) {

        // Multithreading - time-slicing algorithm (using the same single processor)
        // NOT parallel execution !!!!!
        Thread t1 = new Thread(new Runner1());
        Thread t2 = new Thread(new Runner2());

        t1.start();
        t2.start();
    }

}


// Runnable Interface
class Runner1 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner1: " + i);
        }
    }

}

class Runner2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner2: " + i);
        }
    }

}
