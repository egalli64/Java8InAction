package dd.ch05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lambdasinaction.chap4.Dish;

public class P1Filtering {
    public static void main(String[] args) {
        // Filtering with a predicate
        List<Dish> veggie = Dish.menu.stream()
                // filtering to get just the vegetarian dishes
                .filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(veggie);

        // Filtering unique elements
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        List<Integer> evens = numbers.stream().filter(i -> i % 2 == 0).distinct()
                .collect(Collectors.toList());
        System.out.println(numbers + " --> " + evens);

        // Truncating a stream
        List<Dish> headDishes = Dish.menu.stream().filter(d -> d.getCalories() > 300).limit(3)
                .collect(Collectors.toList());
        System.out.println("Three hi-cal dishes (no order): " + headDishes);

        // Skipping elements
        List<Dish> otherDishes = Dish.menu.stream().filter(d -> d.getCalories() > 300).skip(3)
                .collect(Collectors.toList());
        System.out.println("Other hi-cal dishes (no order): " + otherDishes);

        // Filtering quiz: use streams to filter the first two meat dishes
        Dish.menu.stream().filter(d -> (d.getType() == Dish.Type.MEAT)).limit(2)
                .forEach(System.out::println);
    }
}
