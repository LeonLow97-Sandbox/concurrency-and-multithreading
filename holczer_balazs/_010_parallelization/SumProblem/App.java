import java.util.Random;
import utils.*;

public class App {
    
    public static void main(String[] args) {

        Random random = new Random();

        // If dealing with large dataset, parallel sum is faster
        // If dealing with small dataset, sequential sum might be faster
        int[] nums = new int[1000000000];

        for (int i = 0; i < nums.length; ++i) {
            nums[i] = random.nextInt(100);
        }

        int n = Runtime.getRuntime().availableProcessors();

        // Parallel Algorithm
        ParallelSum parallel = new ParallelSum(n);
        long start = System.currentTimeMillis();
        System.out.println("Parallel Sum: " + parallel.parallelSum(nums));
        System.out.println("Time: " + (System.currentTimeMillis() - start));

        // Sequential Sum Algorithm
        SequentialSum sequential = new SequentialSum();
        start = System.currentTimeMillis();
        System.out.println("Sequential Sum: " + sequential.sum(nums));
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

}
