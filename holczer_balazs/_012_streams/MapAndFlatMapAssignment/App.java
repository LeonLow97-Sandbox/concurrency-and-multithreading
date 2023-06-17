package holczer_balazs._012_streams.MapAndFlatMapAssignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    /**
     * Task: Give 2 lists of numbers [1,2,3] and [4,5].
     *      Generate all pairs of possible numbers.
     * 
     * Result: (1,4), (1,5), (2,4), (2,5), (3,4), (3,5)
     */
    
    public static void main(String[] args) {
        
        List<Integer> nums1 = Arrays.asList(1,2,3);
        List<Integer> nums2 = Arrays.asList(4,5);

        List<List<Integer>> pairs = nums1.stream()
            .flatMap(num1 -> nums2.stream().map(num2 -> Arrays.asList(num1, num2))).collect(Collectors.toList());

        System.out.println(Arrays.toString(pairs.toArray()));

    }

}
