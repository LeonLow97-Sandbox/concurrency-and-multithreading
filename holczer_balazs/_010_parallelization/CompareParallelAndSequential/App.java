import utils.*;
import java.util.Random;

public class App {

    public static void main(String[] args) {
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        // System.out.println("Number of Processors: " + numOfThreads);

        int[] numbers1 = createArray(100000000);
        int[] numbers2 = new int[numbers1.length];

        for (int i = 0; i < numbers1.length; i++) {
            numbers2[i] = numbers1[i];
        }

        // Parallel Merge Sort
        ParallelMergeSort parallerSorter = new ParallelMergeSort(numbers1);

        long startTime1 = System.currentTimeMillis();
        parallerSorter.parallelMergeSort(0, numbers1.length - 1, numOfThreads);
        long endTime1 = System.currentTimeMillis();
        System.out.printf("Time taken with parallel: %6d ms\n", endTime1 - startTime1);

        // Sequential Merge Sort
        MergeSort mergeSorter = new MergeSort(numbers2);

        startTime1 = System.currentTimeMillis();
        mergeSorter.sort();
        endTime1 = System.currentTimeMillis();
        System.out.printf("Time taken with sequential: %6d ms\n", endTime1 - startTime1);
    }

    private static int[] createArray(int n) {
        Random random = new Random();
        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = random.nextInt(n);
        }

        return a;
    }

}
