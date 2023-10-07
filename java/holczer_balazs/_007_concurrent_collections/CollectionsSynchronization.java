package holczer_balazs._007_concurrent_collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsSynchronization {

    public static void main(String[] args) {

        // List<Integer> nums = new ArrayList<>(); // not synchronized for this collection
        // intrinsic lock - not that efficient because threads have
        // to wait for each other even when they want to execute independent methods (operations)
        // Efficient Solution: CONCURRENT COLLECTION!!
        List<Integer> nums = Collections.synchronizedList(new ArrayList<>()); // synchronized - add() and remove() methods are synchronized

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    nums.add(i);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    nums.add(i);
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }

        System.out.println("Size of array: " + nums.size());

    }

}
