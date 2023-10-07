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

        fib1.fork();
        fib2.fork();

        return fib1.join() + fib2.join();
    }

}
