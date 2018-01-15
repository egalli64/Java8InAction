package dd.ch05;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lambdasinaction.chap4.Dish;

public class P6NumericStream {
    public static void main(String[] args) {
        System.out.print("Map-reduce that boxes: ");
        Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum)
                .ifPresent(System.out::println);

        // mapping to numeric stream
        int cals = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("Mapping to IntStream: " + cals);

        // Boxing a numeric stream
        Stream<Integer> si = Dish.menu.stream().mapToInt(Dish::getCalories).boxed();
        System.out.println(si.collect(Collectors.toList()));

        // Optional primitive
        OptionalInt maxCalories = Dish.menu.stream().mapToInt(Dish::getCalories).max();
        maxCalories.ifPresent(System.out::println);

        // Numeric ranges
        long count = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0).count();
        System.out.printf("In [1..100] there are %d even numbers%n", count);

        count = IntStream.range(1, 100).filter(n -> n % 2 == 0).count();
        System.out.printf("In [1..100) there are %d even numbers%n", count);

        // Pythagorean triple
        Stream<int[]> pt = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> isPerfectSquare(a * a + b * b))
                        .mapToObj(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));

        pt.limit(5).forEach(t -> System.out.println(Arrays.toString(t)));

        Stream<double[]> pt2 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[] { a, b, Math.sqrt(a * a + b * b) })
                        .filter(t -> t[2] % 1 == 0));

        pt2.limit(5).forEach(t -> System.out.println(Arrays.toString(t)));
    }

    public static boolean isPerfectSquare(int value) {
        return Math.sqrt(value) % 1 == 0;
    }
}
