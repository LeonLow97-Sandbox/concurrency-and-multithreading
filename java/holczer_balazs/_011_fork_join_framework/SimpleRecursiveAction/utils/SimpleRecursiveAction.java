package utils;

import java.util.concurrent.RecursiveAction;

public class SimpleRecursiveAction extends RecursiveAction {

    private int simulatedWork;
    
    public SimpleRecursiveAction(int simulatedWork) {
        this.simulatedWork = simulatedWork;
    }

    @Override
    protected void compute() {
        
        // if the task is too large, split it and execute the tasks in parallel
        if (simulatedWork > 100) {
            System.out.println("Parallel execution and split the tasks... " + simulatedWork);

            SimpleRecursiveAction action1 = new SimpleRecursiveAction(simulatedWork / 2);
            SimpleRecursiveAction action2 = new SimpleRecursiveAction(simulatedWork / 2);

            // action1.fork(); // executes the given task asynchronously
            // action2.fork();

            // action1.join(); // ensure that the given actions are executed
            // action2.join();

            // executed by 2 different threads in a parallel manner
            invokeAll(action1, action2); // waits for the tasks to finish execution
        } else {
            System.out.println("The task is rather small so sequential execution is fine...");
            System.out.println("Size of the task: " + simulatedWork);
        }

    }

}
