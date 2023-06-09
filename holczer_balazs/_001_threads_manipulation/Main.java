package holczer_balazs._001_threads_manipulation;

public class Main {

    public static void main(String[] args) {

        Thread t1 = new Runner1();
        Thread t2 = new Thread(new Runner2());

        t1.start();
        t2.start();
    }

}


// Extends Thread class, means Runner1 is a Thread
class Runner1 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runner1: " + i);
        }
    }

}


// Runnable Interface
class Runner2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Runner2: " + i);
        }
    }

}
