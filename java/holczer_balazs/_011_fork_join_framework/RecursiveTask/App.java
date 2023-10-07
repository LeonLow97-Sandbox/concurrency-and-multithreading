import java.util.concurrent.ForkJoinPool;
import utils.*;

public class App {
    
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        SimpleRecursiveTask task = new SimpleRecursiveTask(12302);
        System.out.println(pool.invoke(task));
    }

}
