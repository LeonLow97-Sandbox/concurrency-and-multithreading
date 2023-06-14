package holczer_balazs._007_concurrent_collections;

import java.util.concurrent.Exchanger;

public class ExchangerExample {

    /**
     * With the help of Exchanger -> 2 threads can exchange objects
     * 
     * `exchange()` -> exchanging objects is done via 1 of the 2 exchange() methods
     */
    
    public static void main(String[] args) {
        
        Exchanger<Integer> exchanger = new Exchanger<>();

        FirstThread first = new FirstThread(exchanger);
        SecondThread second = new SecondThread(exchanger);

        new Thread(first).start();
        new Thread(second).start();
    }

}

class FirstThread implements Runnable {

    private int counter;
    private Exchanger<Integer> exchanger;

    public FirstThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        // if will run forever (infinite loop)
        while (true) {
            counter++;
            System.out.println("FirstThread incremented the counter: " + counter);

            try {
                counter = exchanger.exchange(counter);
                System.out.println("FirstThread get the counter: " + counter);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        }
    }
}

class SecondThread implements Runnable {

    private int counter;
    private Exchanger<Integer> exchanger;

    public SecondThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        // if will run forever (infinite loop)
        while (true) {
            counter--;
            System.out.println("SecondThread decremented the counter: " + counter);

            try {
                counter = exchanger.exchange(counter);
                System.out.println("SecondThread get the counter: " + counter);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }   
        }
    }

}