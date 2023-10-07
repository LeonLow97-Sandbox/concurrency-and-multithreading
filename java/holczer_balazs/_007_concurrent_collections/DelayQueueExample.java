package holczer_balazs._007_concurrent_collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {

    /**
     * This is an unbounded BlockingQueue of objects that implement the Delayed
     * interface
     * 
     * - `DelayQueue` keeps the elements internally until a certain delay has
     * expired.
     * - an object can only be taken from the queue when its delay has expired.
     * 
     * Cannot put null items in the queue - the queue is sorted so that the object
     * at the
     * head has a delay that has expired for the longest time.
     * 
     * If no delay has expired, then there is no head element and `poll()` method
     * will return null.
     * 
     * `size()` returns the count of both expired and unexpired items.
     * 
     */

    public static void main(String[] args) {
        BlockingQueue<DelayedWorker> queue = new DelayQueue<>();

        try {
            queue.put(new DelayedWorker("This is the first message...", 2000));
            queue.put(new DelayedWorker("This is the second message...", 10000));
            queue.put(new DelayedWorker("This is the third message...", 4500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // we can get the messages
        while (!queue.isEmpty()) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Queue is empty with size of " + queue.size());
    }

}

class DelayedWorker implements Delayed {

    private long duration; // time to wait before getting the item from the blocking queue
    private String message; // message returned after the duration

    public DelayedWorker(String message, long duration) {
        this.message = message;
        this.duration = System.currentTimeMillis() + duration;
    }

    @Override
    public int compareTo(Delayed other) {
        // this is the method that can compare objects
        // returns -1, +1 or 0

        if (duration < ((DelayedWorker) other).getDuration()) {
            return -1; // actual duration is smaller than the other object's duration
        }

        if (duration > ((DelayedWorker) other).getDuration()) {
            return +1;
        }

        return 0; // both durations are the same
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                " duration='" + getDuration() + "'" +
                ", message='" + getMessage() + "'" +
                "}";
    }

}
