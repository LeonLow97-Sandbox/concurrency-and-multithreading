package holczer_balazs._012_streams.ExternalAndInternalIteration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;
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

        // External Iteration (Collections)
        List<String> titles = new ArrayList<>();

        Iterator<Book> iterator = books.iterator();

        // inherently sequential as we get the items 1 by 1
        // [item1, item2, item3, item4]
        // no parallelization
        while (iterator.hasNext()) {
            titles.add(iterator.next().getTitle());
        }

        // stream API - Internal Iteration
        // items next to each other like list will work very fast with stream APi
        // parallel quite easily
        List<String> titles2 = books.stream().map(Book::getTitle).collect(Collectors.toList());
        titles2.forEach(e -> System.out.println(e));
    }

}
