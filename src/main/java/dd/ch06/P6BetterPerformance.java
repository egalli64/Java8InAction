package dd.ch06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class P6BetterPerformance {
    public static void main(String[] args) {
        Map<Boolean, List<Integer>> primes = partitionPrimes(42);
        System.out.println(primes.get(true));

        Map<Boolean, List<Integer>> p2 = partitionPrimesEx(42);
        System.out.println(p2.get(true));

        // System.out.println("Done in: " + execute(P6BetterPerformance::partitionPrimes) + " ms");
        System.out.println("Done in: " + execute(P6BetterPerformance::partitionPrimesEx) + " ms");
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(Collectors.partitioningBy(candidate -> isPrime(candidate)));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesEx(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    private static long execute(Consumer<Integer> primePartitioner) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            primePartitioner.accept(1_000_000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest)
                fastest = duration;
            System.out.println("done in " + duration);
        }
        return fastest;
    }
}

class PrimeNumbersCollector
        implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    private boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return cutOff(primes, candidateRoot).stream().noneMatch(i -> candidate % i == 0);
    }

    /**
     * Cut off the list to the first too big element (to be improved in next
     * chapters)
     * 
     * @param values
     *            a list assumed monotonic growing
     * @param limit
     *            cutoff value
     * @return list of the first good values
     */
    private List<Integer> cutOff(List<Integer> values, int limit) {
        for (int i = 0, end = values.size(); i < end; i++) {
            if (values.get(i) > limit) {
                return values.subList(0, i);
            }
        }
        return values;
    }

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {
            private static final long serialVersionUID = -6138267726317837958L;

            { // initialize the map with two empty lists under the true and false keys
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }
        };
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            // pass to isPrime the list of the prime numbers found so far
            // and the current candidate
            acc.get(isPrime(acc.get(true), candidate)).add(candidate);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> lhs, Map<Boolean, List<Integer>> rhs) -> {
            throw new UnsupportedOperationException("This is a strictly sequential collector");
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
