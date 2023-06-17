import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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

        // books.stream().forEach(System.out::println);
        List<String> result = books.stream().filter(book -> book.getType() == Type.NOVEL)
                .sorted(Comparator.comparing(Book::getPages))
                .map(Book::getAuthor)
                .collect(Collectors.toList());
        result.stream().forEach(System.out::println);
    }

}
