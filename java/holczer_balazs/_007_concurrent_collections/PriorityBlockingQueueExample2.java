package holczer_balazs._007_concurrent_collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample2 {

    public static void main(String[] args) {

        BlockingQueue<Person> queue = new PriorityBlockingQueue<>();

        FirstWorker first = new FirstWorker(queue);
        SecondWorker second = new SecondWorker(queue);

        new Thread(first).start();
        new Thread(second).start();

    }
}

// Using the Comparable interface
class Person implements Comparable<Person> {

    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int compareTo(Person person) {
        // return Integer.compare(this.age, person.getAge()); // comparing by the age
        return name.compareTo(person.getName()); // comparing by the name
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
            " age='" + getAge() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}

class FirstWorker implements Runnable {

    private BlockingQueue<Person> queue;

    public FirstWorker(BlockingQueue<Person> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put(new Person(31, "Benjamin"));
            queue.put(new Person(25, "Tess"));
            queue.put(new Person(26, "Leon"));
            Thread.sleep(2000);
            queue.put(new Person(21, "Joan"));
            Thread.sleep(1000);
            queue.put(new Person(18, "Ashley"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class SecondWorker implements Runnable {

    private BlockingQueue<Person> queue;

    public SecondWorker(BlockingQueue<Person> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(queue.take());
            Thread.sleep(1000);
            System.out.println(queue.take());
            Thread.sleep(2000);
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
