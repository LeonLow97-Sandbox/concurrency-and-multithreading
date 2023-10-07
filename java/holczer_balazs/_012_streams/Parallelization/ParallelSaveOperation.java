package holczer_balazs._012_streams.Parallelization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelSaveOperation {

    public static final String DIRECTORY = System.getProperty("user.dir") + "/test/";

    public static void main(String[] args) throws IOException {
        // create the directory
        Files.createDirectories(Paths.get(DIRECTORY));

        ParallelSaveOperation app = new ParallelSaveOperation();

        // generate a large number of Person objects
        List<Person> people = app.generatePeople(100000);

        // sequential algorithm
        long start = System.currentTimeMillis();
        people.stream().forEach(ParallelSaveOperation::save);
        System.out.println("Time taken sequential: " + (System.currentTimeMillis() - start));

        // parallel algorithm
        start = System.currentTimeMillis();
        people.parallelStream().forEach(ParallelSaveOperation::save);
        System.out.println("Time taken parallel: " + (System.currentTimeMillis() - start));
    }

    private static void save(Person person) {
        try (FileOutputStream fos =
                new FileOutputStream(new File(DIRECTORY + person.getPersonId() + ".txt"))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Person> generatePeople(int num) {
        // n -> n + 1, generating 1,2,3,4,5,...
        return Stream.iterate(0, n -> n + 1).limit(num).map(x -> {
            return new Person(x);
        }).collect(Collectors.toList());
    }

}


class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private int personId;

    public Person(int personId) {
        this.personId = personId;
    }

    public int getPersonId() {
        return this.personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

}
