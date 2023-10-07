import utils.*;

public class App {

    public static void main(String[] args) {
        int[] nums = {5, -1, 0, 7, 2, 3, 2, 1, 0, 1, 2};

        ParallelMergeSort sort = new ParallelMergeSort(nums);
        sort.sort();
        sort.showArray();
    }

}
