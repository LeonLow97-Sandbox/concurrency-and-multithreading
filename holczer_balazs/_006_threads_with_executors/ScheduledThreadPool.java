package holczer_balazs._006_threads_with_executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
    public static void main(String[] args) {
        
        // 1 scheduled thread to look for updated stock data
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        /**
         * initialDelay - delay before calling the service
         * period - intervals in which it will call the stock market updater
         */
        executor.scheduleAtFixedRate(new StockMarketUpdater(), 1000, 5000, TimeUnit.MILLISECONDS);
    }
}

class StockMarketUpdater implements Runnable {
    @Override
    public void run() {
        System.out.println("Updating and downloading stock related data from web...");
    }
}
