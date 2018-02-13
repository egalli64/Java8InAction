package dd.ch07;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class P1ParallelStreams {
    public static void main(String[] args) {
        System.out.println(iterativeSum(10));
        System.out.println(sequentialSum(10));
        System.out.println(parallelSum(10));
        System.out.println(rangedSum(10));

        System.out.printf("Iterative sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::iterativeSum, 2_000_000L));
        System.out.printf("Sequential sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::sequentialSum, 2_000_000L));
        System.out.printf("Parallel sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::parallelSum, 2_000_000L));
        System.out.printf("Ranged sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::rangedSum, 2_000_000L));
        System.out.printf("Parallel ranged sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::parallelRangedSum, 2_000_000L));
        System.out.printf("SideEffect sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::sideEffectSum, 2_000_000L));
        System.out.printf("SideEffect parallel sum done in %.2f ms%n",
                measureSumPerf(P1ParallelStreams::sideEffectParallelSum, 2_000_000L));
        // see P2
        System.out.printf("ForkJoin sum done in %.2f ms%n",
                measureSumPerf(ForkJoinSumCalculator::forkJoinSum, 2_000_000L));
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }
    
    // intrinsically sequential
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    // broken!
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static double measureSumPerf(Function<Long, Long> adder, long n) {
        double fastest = Double.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long result = adder.apply(n);
            double duration = (System.nanoTime() - start) / 1_000_000F;
            System.out.printf("Result: %d in %.2f ms%n", result, duration);
            if (duration < fastest)
                fastest = duration;
        }
        return fastest;
    }
}

class Accumulator {
    long total = 0;

    public void add(long value) {
        total += value;
    }
}
