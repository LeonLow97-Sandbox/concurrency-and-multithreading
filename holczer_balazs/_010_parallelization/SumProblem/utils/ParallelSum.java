package utils;

public class ParallelSum {
    
    private ParallelWorker[] workers;
    private int numOfThreads;

    public ParallelSum(int numOfThreads) {
        this.numOfThreads = numOfThreads;
        this.workers = new ParallelWorker[numOfThreads];
    }

    public int sum(int[] nums) {

        int size = (int) Math.ceil(nums.length * 1.0 / numOfThreads) ;

        for (int i = 0; i < numOfThreads; ++i) {
            workers[i] = new ParallelWorker(nums, i*size, (i+1)*size);
            workers[i].start();
        }

        try {
            // ensure that threads wait for each other to complete (use sequential approach here)
            for (ParallelWorker worker: this.workers) {
                worker.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // sum up the sub-results
        int total = 0;

        for (ParallelWorker worker: workers) {
            total += worker.getPartialSum();
        }

        return total;
    }

}
