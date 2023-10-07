import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.*;

public class App {

    /**
     * The aim of the simulation is that it is possible to avoid thread starvation - all the threads
     * are going to be executed by the executor service - able to avoid deadlocks because we use
     * tryLock()
     */

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = null;
        Philosopher[] philosophers = null;
        Chopstick[] chopSticks = null;

        try {
            philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
            chopSticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICKS];

            for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; ++i) {
                chopSticks[i] = new Chopstick(i);
            }

            executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);

            for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; ++i) {
                philosophers[i] = new Philosopher(i, chopSticks[i],
                        chopSticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
                executorService.execute(philosophers[i]); // executes run() method of Philosopher
            }

            Thread.sleep(Constants.SIMULATION_RUNNING_TIME);

            for (Philosopher philosopher : philosophers) {
                philosopher.setFull(true);
            }
        } finally {
            executorService.shutdown();

            while (!executorService.isTerminated()) {
                Thread.sleep(1000);
            }

            for (Philosopher philosopher : philosophers) {
                System.out.println(
                        philosopher + " eat # " + philosopher.getEatingCounter() + " times");
            }
        }
    }

}
