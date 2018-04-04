package dd.ch14;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class P3Lazy {
    public static void main(String[] args) {
        System.out.println(primes(10).collect(Collectors.toList()));

        System.out.println("First 10 candidates: " + Arrays.toString(numbers().limit(10).toArray()));
        System.out.println("First candidate: " + head(numbers()));

        // IntStream numbers = numbers();
        // int head = head(numbers);
        // IntStream filtered = tail(numbers).filter(n -> n % head != 0);

        LazyList<Integer> numbers = from(2);
        
        System.out.println(primes(numbers).head());
    }

    private static IntStream numbers() {
        return IntStream.iterate(2, n -> n + 1);
    }

    static int head(IntStream numbers) {
        return numbers.findFirst().getAsInt();
    }

    static IntStream tail(IntStream numbers) {
        return numbers.skip(1);
    }

    public static LazyList<Integer> from(int n) {
        return new LazyList<Integer>(n, () -> from(n + 1));
    }

    interface MyList<T> {
        T head();

        MyList<T> tail();

        default boolean isEmpty() {
            return true;
        }

        MyList<T> filter(Predicate<T> p);
    }

    public static MyList<Integer> primes(MyList<Integer> numbers) {
        return new LazyList<>(numbers.head(),
                () -> primes(numbers.tail().filter(n -> n % numbers.head() != 0)));
    }

    static class LazyList<T> implements MyList<T> {
        final T head;
        final Supplier<MyList<T>> tail;

        public LazyList(T head, Supplier<MyList<T>> tail) {
            this.head = head;
            this.tail = tail;
        }

        public T head() {
            return head;
        }

        public MyList<T> tail() {
            return tail.get();
        }

        public boolean isEmpty() {
            return false;
        }

        public MyList<T> filter(Predicate<T> p) {
            return isEmpty() ? this
                    : p.test(head()) ? new LazyList<>(head(), () -> tail().filter(p)) : tail().filter(p);
        }
    }

    public static Stream<Integer> primes(int n) {
        return Stream.iterate(2, i -> i + 1).filter(P3Lazy::isPrime).limit(n);
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }
}
