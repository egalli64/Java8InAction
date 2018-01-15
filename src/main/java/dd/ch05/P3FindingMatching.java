package dd.ch05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lambdasinaction.chap4.Dish;

public class P3FindingMatching {
    public static void main(String[] args) {
        // match at least one element
        if (Dish.menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        // match all elements
        if (Dish.menu.stream().allMatch(d -> d.getCalories() < 1000)) {
            System.out.println("All dishes in the menu are relatively low-cal");
        }

        // do not match any element
        if (Dish.menu.stream().noneMatch(d -> d.getCalories() >= 1000)) {
            System.out.println("No dish in the menu is relatively hi-cal");
        }

        // Finding an element
        Optional<Dish> dish = Dish.menu.stream().filter(Dish::isVegetarian).findAny();
        dish.ifPresent(d -> System.out.println(d.getName()));

        // Finding the first element
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream().map(x -> x * x)
                .filter(x -> x % 3 == 0).findFirst();
        System.out.println(firstSquareDivisibleByThree.get() == 9);
    }
}
