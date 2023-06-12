package holczer_balazs._006_threads_with_executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableInterface {
    
    public static void main(String[] args) {
        
        // to create multiple threads
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<String>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<String> future = service.submit(new Processor(i + 1));
            list.add(future);
        }

        for (Future<String> f : list) {
            try {
                System.out.println(f.get()); // retrieves the result of the thread
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

}

class Processor implements Callable<String> {

    private int id;

    public Processor(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "Id: " + id;
    }

}