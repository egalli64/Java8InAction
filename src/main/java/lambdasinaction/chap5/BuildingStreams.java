package lambdasinaction.chap5;

import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;

public class BuildingStreams {
    private static final URL FILENAME = BuildingStreams.class
            .getResource("/lambdasinaction/chap5/data.txt");

    public static void main(String... args) {
        System.out.println("hello");

        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();
        emptyStream.findAny().ifPresent(System.out::println);

        // Arrays.stream
        int[] numbers = { 2, 3, 5, 7, 11, 13 };
        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);

        // fibonacci with iterate
        Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }).limit(10)
                .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

        Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }).limit(10)
                .map(t -> t[0]).forEach(System.out::println);

        // random stream of doubles with Stream.generate
        Stream.generate(Math::random).limit(10).forEach(System.out::println);

        // stream of 1s with Stream.generate
        IntStream.generate(() -> 1).limit(5).forEach(System.out::println);

        IntStream.generate(new IntSupplier() {
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            public int getAsInt() {
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return this.previous;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

        try {
            long uniqueWords = Files.lines(Paths.get(FILENAME.toURI()), Charset.defaultCharset())
                    .flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println("There are " + uniqueWords + " unique words in data.txt");
        } catch (IOException | URISyntaxException e) {
            System.out.println("Exception " + e.getClass() + ", message: " + e.getMessage());
        }
    }
}
