package dd.ch07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class P2ForkJoin {
    static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public static void main(String[] args) {
        System.out.println(ForkJoinSumCalculator.forkJoinSum(2_000_000L));
    }
}

// task for the fork/join framework
class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private static final long serialVersionUID = -6330635753590003044L;

    public static final long THRESHOLD = 100_000; // no task split below this limit

    private final long[] numbers; // to be summed
    private final int start; // each subtask has its onw start/end
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    // used to recursively create subtasks
    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        // split a left task
        int pivot = start + length / 2;
        ForkJoinSumCalculator left = new ForkJoinSumCalculator(numbers, start, pivot);
        left.fork();

        // right task computed synchronously
        ForkJoinSumCalculator right = new ForkJoinSumCalculator(numbers, pivot, end);

        return left.join() + right.compute();
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return P2ForkJoin.FORK_JOIN_POOL.invoke(task);
    }
}