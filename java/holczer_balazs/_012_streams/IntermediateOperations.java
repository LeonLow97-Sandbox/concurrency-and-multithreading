package holczer_balazs._012_streams;

import java.util.stream.IntStream;

public class IntermediateOperations {
    
    public static void main(String[] args) {
        
        // IntStream.range(0,10).filter(x -> x > 4).forEach(x -> System.out.print(x + " "));

        IntStream.range(0,10).filter(x -> x % 2 != 0).forEach(x -> System.out.print(x + " "));

    }

}
