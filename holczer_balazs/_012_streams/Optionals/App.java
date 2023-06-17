import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import utils.*;

public class App {

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Being and Time", "Heidegger", 560, Type.PHILOSOPHY));
        books.add(new Book("The Trial", "Franz Kafka", 240, Type.NOVEL));
        books.add(new Book("Death on The Nile", "Agatha Christie", 370, Type.THRILLER));
        books.add(new Book("Ancient Greece", "Robert F.", 435, Type.HISTORY));
        books.add(new Book("Ancient Rome", "Robert F.", 860, Type.HISTORY));
        books.add(new Book("Death of Virgil", "Hermann Broch", 590, Type.NOVEL));
        books.add(new Book("The Stranger", "Albert Camus", 560, Type.NOVEL));

        // total number of books
        // System.out.println(books.stream().count());

        List<Integer> nums = Arrays.asList(1,2,3,4);
        // do not want to get a NullPointerException
        // Optional<Integer> result = nums.stream().reduce(Integer::max);
        // result.ifPresent(System.out::println);

        // get the maximum number of pages
        books.stream().map(Book::getPages).reduce(Integer::max).ifPresent(System.out::println);

        // get the longest book
        Optional<Book> longest = books.stream().reduce((b1, b2) -> b1.getPages() > b2.getPages() ? b1 : b2);
        longest.ifPresent(System.out::println);

        // sum all the pages of all books
        // have to transform the Stream<Integer> into an integer stream "list of integers"
        IntStream s = books.stream().mapToInt(Book::getPages);
        Stream<Integer> ss = s.boxed();

        
    }

}
