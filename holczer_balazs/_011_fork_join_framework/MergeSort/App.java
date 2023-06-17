import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import utils.*;

public class App {

    public static void main(String[] args) {

        int[] nums1 = createNumbers(10000000);
        int[] nums2 = new int[nums1.length];

        for (int i = 0; i < nums1.length; ++i) {
            nums2[i] = nums1[i];
        }

        System.out.println("**********SEQUENTIAL MERGE SORT**********");
        SequentialMergeSort sequential = new SequentialMergeSort(nums1);
        long start = System.currentTimeMillis();
        sequential.mergeSort(nums1);
        System.out.println("Time taken: " + (System.currentTimeMillis() - start) + "ms");

        System.out.println("**********PARALLEL MERGE SORT**********");
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        ParallelMergeSort parallel = new ParallelMergeSort(nums2);
        start = System.currentTimeMillis();
        pool.invoke(parallel);
        System.out.println("Time take: " + (System.currentTimeMillis() - start) + "ms");

    }

    private static int[] createNumbers(int n) {
        Random random = new Random();
        int[] nums = new int[n];

        for (int i = 0; i < nums.length; ++i) {
            nums[i] = random.nextInt(10000);
        }

        return nums;
    }

}
