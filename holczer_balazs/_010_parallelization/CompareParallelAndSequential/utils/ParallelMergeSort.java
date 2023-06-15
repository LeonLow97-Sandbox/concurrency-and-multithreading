package utils;

public class ParallelMergeSort {
    private int[] nums;
    // merge sort is not an in-place algorithm
    private int[] tempArray;

    public ParallelMergeSort(int[] nums) {
        this.nums = nums;
        this.tempArray = new int[nums.length];
    }

    public void parallelMergeSort(int low, int high, int numOfThreads) {
        // not enough processor cores to run in parallel, so have to switch to sequential approach
        if (numOfThreads <= 1) {
            mergeSort(low, high);
            return;
        }

        int middleIndex = (low + high) / 2;

        Thread leftSorter = createThread(low, middleIndex, numOfThreads);
        Thread rightSorter = createThread(middleIndex + 1, high, numOfThreads);

        leftSorter.start();
        rightSorter.start();

        try {
            leftSorter.join();
            rightSorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // the merging has to be in sequential approach
        merge(low, middleIndex, high);
    }

    // numOfThreads depends on computer's number of processor (processor core)
    private Thread createThread(int low, int high, int numOfThreads) {
        return new Thread() {
            @Override
            public void run() {
                parallelMergeSort(low, high, numOfThreads / 2);
            }
        };
    }

    public void sort() {
        mergeSort(0, nums.length - 1);
    }

    // DIVIDE AND CONQUER APPROACH
    private void mergeSort(int low, int high) {
        // base case (when there is just a single item in the underlying array)
        if (low >= high) {
            return;
        }

        // middle item
        int middleIndex = (low + high) / 2;

        // call mergeSort recursively on both sub-arrays
        // keep splitting the problem into smaller sub-problems until a given array contains 1 item
        mergeSort(low, middleIndex);
        mergeSort(middleIndex + 1, high);

        // combine the sub-solutions
        merge(low, middleIndex, high);
    }

    private void merge(int low, int middle, int high) {
        // copy the items into the temp array
        for (int i = low; i <= high; ++i) {
            tempArray[i] = nums[i];
        }

        int i = low; // left sub-array index
        int j = middle + 1; // right sub-array index
        int k = low; // temp sub-array index

        // consider the temp array and copy the items into the nums
        while (i <= middle && j <= high) {
            if (tempArray[i] < tempArray[j]) {
                nums[k] = tempArray[i];
                ++i;
            } else {
                nums[k] = tempArray[j];
                ++j;
            }
            ++k;
        }

        // copy the items from the left sub-array (if there are any)
        while (i <= middle) {
            nums[k] = tempArray[i];
            ++k;
            ++i;
        }

        // copy the items from the right sub-array (if there are any)
        while (j <= high) {
            nums[k] = tempArray[j];
            ++k;
            ++j;
        }
    }

    public void showArray() {
        for (int i = 0; i < nums.length; ++i) {
            System.out.println(nums[i] + " ");
        }
    }

    private void swap(int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
