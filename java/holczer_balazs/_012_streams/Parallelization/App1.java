package holczer_balazs._012_streams.Parallelization;

import java.util.stream.IntStream;

public class App1 {
    
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();

        // sequential stream
        long numOfPrimes = IntStream.rangeClosed(2, Integer.MAX_VALUE/100).filter(App1::isPrime).count();
        System.out.println("Number of primes (sequential): " + numOfPrimes);
        System.out.println("Time taken: " + (System.currentTimeMillis() - currentTime));

        // parallel stream
        currentTime = System.currentTimeMillis();
        numOfPrimes = IntStream.rangeClosed(2, Integer.MAX_VALUE/100).parallel().filter(App1::isPrime).count();
        System.out.println("Number of primes (parallel): " + numOfPrimes);
        System.out.println("Time taken: " + (System.currentTimeMillis() - currentTime));
    }

    private static boolean isPrime(long num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;

        // check the numbers in the range [0, sqrt(N)]
        long maxDivisor = (long) Math.sqrt(num);

        for (int i = 3; i < maxDivisor; i +=2) {
            if (num % i == 0) return false;
        }

        return true;
    }

}
