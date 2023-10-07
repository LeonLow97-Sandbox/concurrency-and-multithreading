package utils;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class PrintingIntegers extends RecursiveAction {

    private List<Integer> nums;

    public PrintingIntegers(List<Integer> nums) {
        this.nums = nums;
    }
    
    @Override
    protected void compute() {
        if (nums.size() < 2) {
            for (Integer num: nums) {
                System.out.println(num);
            }
        } else {
            List<Integer> left = nums.subList(0, nums.size()/2);
            List<Integer> right = nums.subList(nums.size()/2, nums.size());

            PrintingIntegers action1 = new PrintingIntegers(left);
            PrintingIntegers action2 = new PrintingIntegers(right);

            invokeAll(action1, action2);
        }
    }

}
