import utils.*;

public class App {
    
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,123,1,1,4,5,7,8,435,645,234,9};

        int n = Runtime.getRuntime().availableProcessors();

        ParallelSum parallel = new ParallelSum(n);
        System.out.println(parallel.sum(nums));
    }

}
