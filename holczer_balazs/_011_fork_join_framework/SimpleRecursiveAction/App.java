import java.util.concurrent.ForkJoinPool;
import utils.*;

public class App {

    /**
     * fork() - asynchronously executes the given tasks in the pool
     *              can call it when using RecursiveTask<T> or RecursiveAction
     * 
     * join() - asynchronously returns the result of the computation when it is finished.
     * invoke() - executes the given task + WAIT + return the result upon completion.
     */
    
    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();

        SimpleRecursiveAction action = new SimpleRecursiveAction(800);
        action.invoke();
    }

}
