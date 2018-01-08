package dd.ch03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dd.ch02.Apple;

public class P8 {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple("green", 80), new Apple("green", 155),
                new Apple("red", 120), new Apple("green", 120));
        System.out.println(inventory);

        // 3.8.1. Composing Comparators
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println("Plain comparator: " + inventory);

        inventory.sort(Comparator.comparing(Apple::getWeight).reversed());
        System.out.println("Reversed comparator: " + inventory);

        inventory.sort(
                Comparator.comparing(Apple::getColor).reversed().thenComparing(Apple::getWeight));
        System.out.println("By reversed color then by weight: " + inventory);

        inventory.sort(
                Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        System.out.println("By reversed weight then by color: " + inventory);
        
        // 3.8.2. Composing Predicates
        Predicate<Apple> red = a -> a.getColor() == "red";
        System.out.println(filterByPredicate(inventory, red));
        System.out.println(filterByPredicate(inventory, red.negate()));

        Predicate<Apple> light = a -> a.getWeight() < 100;
        System.out.println(filterByPredicate(inventory, red.negate().and(light)));
        
        System.out.println(filterByPredicate(inventory, red.negate().and(light).or(red)));
        
        // 3.8.3. Composing Functions
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        System.out.println(f.andThen(g).apply(0) + ", " + f.compose(g).apply(0));
    }
    
    private static <T> List<T> filterByPredicate(List<T> list, Predicate<T> p) {
        return list.stream().filter(p).collect(Collectors.toList());
    }
}
