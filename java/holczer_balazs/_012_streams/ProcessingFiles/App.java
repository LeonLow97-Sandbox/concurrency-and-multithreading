import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws IOException {
        
        String path = "/Users/leonlow/Desktop/udemy/java-concurrency-multithreading-parallel-computing/holczer_balazs/_012_streams/ProcessingFiles/utils/names";

        // using java streams to access the given file
        Stream<String> namesStream = Files.lines(Paths.get(path));
        
        List<String> names = namesStream.filter(x -> x.startsWith("A")).collect(Collectors.toList());

        names.forEach(System.out::println);

    }
}