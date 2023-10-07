package utils;

import java.util.concurrent.RecursiveTask;

public class FibonacciNumbers extends RecursiveTask<Integer> {

    private int num;

    public FibonacciNumbers(int num) {
        this.num = num;
    }

    @Override
    protected Integer compute() {
        if (num <= 1) {
            return num;
        }

        FibonacciNumbers fib1 = new FibonacciNumbers(num - 1);
        FibonacciNumbers fib2 = new FibonacciNumbers(num - 2);

        // fib1.fork();
        fib2.fork();

        // the actual thread executes fib1 and
        // we create another thread (insert it into the pool)
        // associated with fib2
        return fib1.compute() + fib2.join();
    }

}
