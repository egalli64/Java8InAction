package dd.ch06;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lambdasinaction.chap6.Dish;

public class P4Partitioning {
    public static void main(String[] args) {
        // dishes partitioned against a partition function
        System.out.println("Dishes partitioned by vegetarian:");
        Map<Boolean, List<Dish>> dishes = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(dishes);

        // if interested in only "true" elements
        List<Dish> veggies = dishes.get(true);
        System.out.println(veggies);

        // filtering is sometimes a more straightforward choice
        List<Dish> veggies2 = Dish.menu.stream().filter(Dish::isVegetarian)
                .collect(Collectors.toList());
        System.out.println(veggies2);

        // sub-grouping
        System.out.println("Vegetarian Dishes by type:");
        Map<Boolean, Map<Dish.Type, List<Dish>>> dishes2 = Dish.menu.stream().collect(Collectors
                .partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        System.out.println(dishes2);

        // partition + other operations
        System.out.println("Most caloric dishes by vegetarian:");
        Map<Boolean, Dish> dishes3 = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));
        System.out.println(dishes3);

        // quiz 6.2 partition + sub-partition
        System.out.println("Partitioning quiz");
        // 1. veg + hi-lo cal
        Map<Boolean, Map<Boolean, List<Dish>>> q1 = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.partitioningBy(d -> d.getCalories() > 500)));
        System.out.println(q1);

        // 2. veg + type won't work, patched with type == FISH (slightly weird)
        Map<Boolean, Map<Boolean, List<Dish>>> q2 = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors
                        .partitioningBy(d -> d.getType() == Dish.Type.FISH/* Dish::getType */)));
        System.out.println(q2);

        // 3. veg + counter
        Map<Boolean, Long> q3 = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.counting()));
        System.out.println(q3);

        // Partitioning numbers into prime and nonprime
        System.out.println("Partition to primes");
        Map<Boolean, List<Integer>> pp = partitionPrimes(25);
        System.out.println(pp);
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(Collectors.partitioningBy(p -> isPrime(p)));
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }
}
