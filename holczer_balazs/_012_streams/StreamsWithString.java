package holczer_balazs._012_streams;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamsWithString {

    public static void main(String[] args) {
        String[] names = {"Adam", "Daniel", "Martha", "Kevin", "Ben", "Joe", "Brad", "Susan"};

        // Stream.of(names).sorted(Comparator.reverseOrder()).forEach(System.out::println);

        Stream.of(names).filter(x -> x.startsWith("B")).forEach(System.out::println);
    }

}
