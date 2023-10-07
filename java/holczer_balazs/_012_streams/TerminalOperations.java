package holczer_balazs._012_streams;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TerminalOperations {
    
    public static void main(String[] args) {
        int[] nums = {1,5,3,-2,9,12};

        // lambda expression
        // :: operator or method reference
        // Arrays.stream(nums).forEach(System.out::println);

        // int sum = Arrays.stream(nums).sum();
        // System.out.println(sum);

        // IntStream.range(0,5).forEach(x -> System.out.print(x + " "));
    }

}
