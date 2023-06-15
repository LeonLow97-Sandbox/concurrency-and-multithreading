package utils;

public class ParallelWorker extends Thread {

    private int[] nums;
    private int low;
    private int high;
    private int partialSum;

    public ParallelWorker(int[] nums, int low, int high) {
        this.nums = nums;
        this.low = low;
        this.high = Math.min(nums.length, high);
    }


    @Override
    public void run() {
        partialSum = 0;

        for (int i = 0; i < high; i++) {
            partialSum += nums[i];
        }
    }

    public int getPartialSum() {
        return partialSum;
    }

}
