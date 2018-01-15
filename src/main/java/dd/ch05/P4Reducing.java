package dd.ch05;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lambdasinaction.chap4.Dish;

public class P4Reducing {
    public static void main(String[] args) {
        // Summing the elements
        List<Integer> numbers = Arrays.asList(1, 2, 2, 1);
        int sum = numbers.stream().reduce(0, Integer::sum);
        int prod = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(numbers + ": " + sum + ", " + prod);

        List<Integer> noValue = Collections.emptyList();
        Optional<Integer> result = noValue.stream().reduce(Integer::sum);
        System.out.println(result.isPresent());

        // Maximum and minimum
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        max.ifPresent(System.out::println);
        min.ifPresent(System.out::println);

        // Reducing quiz
        // count the number of dishes in a stream using the map and reduce
        Dish.menu.stream().map(d -> 1).reduce(Integer::sum).ifPresent(System.out::print);
        System.out.println(" == " + Dish.menu.stream().count());
    }
}
