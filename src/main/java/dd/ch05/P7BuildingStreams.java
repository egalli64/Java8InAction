package dd.ch05;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class P7BuildingStreams {
    public static void main(String[] args) {
        // Streams from values
        Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        Stream<String> empty = Stream.empty();
        empty.findAny().ifPresent(System.out::println);

        // Streams from arrays
        int[] numbers = { 2, 3, 5, 7, 11, 13 };
        System.out.println(Arrays.stream(numbers).sum() + " == 41");

        // Streams from files
        try {
            String resource = "/lambdasinaction/chap5/data.txt";
            URI uri = P7BuildingStreams.class.getResource(resource).toURI();
            long uniqueWords = Files.lines(Paths.get(uri))
                    .flatMap(line -> Arrays.stream(line.split(" "))).map(String::toLowerCase)
                    .distinct().count();
            System.out.println("There are " + uniqueWords + " unique words in data.txt");
        } catch (IOException | URISyntaxException e) {
            System.out.println("Exception " + e.getClass() + ", message: " + e.getMessage());
        }

        // creating infinite streams w/ iterate
        List<Integer> li = Stream.iterate(0, n -> n + 2).limit(10).collect(Collectors.toList());
        System.out.println(li);

        // Quiz 5.4: Fibonacci tuples series
        List<int[]> fib = Stream.iterate(new int[] { 0, 1 }, f -> new int[] { f[1], f[0] + f[1] })
                .limit(20).collect(Collectors.toList());

        System.out.print("{ ");
        fib.forEach(f -> System.out.print(Arrays.toString(f) + " "));
        System.out.println("}");

        // creating infinite streams w/ generate
        List<Double> ld = Stream.generate(Math::random).limit(5).collect(Collectors.toList());
        System.out.println(ld);

        // Fibonacci generator
        // Notice: mutable supplier is no good (forget about parallel computation)
        IntSupplier fibSupplier = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        int[] fibonacci = IntStream.generate(fibSupplier).limit(10).toArray();
        System.out.println(Arrays.toString(fibonacci));

    }
}
