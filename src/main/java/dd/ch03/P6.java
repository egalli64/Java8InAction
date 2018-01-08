package dd.ch03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import dd.ch02.Apple;

public class P6 {
    public static void main(String[] args) {
        // plain comparing and w/ method reference
        List<Apple> inventory = Arrays.asList(new Apple("green", 80), new Apple("green", 155),
                new Apple("red", 120));
        System.out.println(inventory);
        inventory.sort((lhs, rhs) -> lhs.getWeight().compareTo(rhs.getWeight()));
        System.out.println("Sorted by weight: " + inventory);
        inventory.sort((lhs, rhs) -> lhs.getColor().compareTo(rhs.getColor()));
        System.out.println("Sorted by color: " + inventory);

        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println("Sorted by weight - method reference: " + inventory);

        // 1: method reference to a static method
        Function<String, Integer> stringToInteger = s -> Integer.parseInt(s);
        Function<String, Integer> s2i = Integer::parseInt;
        System.out.printf("%d == %d%n", stringToInteger.apply("42"), s2i.apply("42"));
        
        // 2: to an instance method
        ToIntFunction<String> stringToLength = s -> s.length();
        ToIntFunction<String> s2l = String::length;
        System.out.printf("%d == %d%n", stringToLength.applyAsInt("hello"), s2l.applyAsInt("hello"));
        
        // 3: to an instance method of an existing object
        final String existing = "A short string";
        Supplier<String> upperString = () -> existing.toUpperCase();
        Supplier<String> n2s = existing::toUpperCase;
        System.out.printf("%s == %s%n", upperString.get(), n2s.get());
        
        // 6.2 Constructor references
        Supplier<Apple> sa = Apple::new;
        Supplier<Apple> sa2 = () -> new Apple();
        System.out.printf("%s == %s%n", sa.get(), sa2.get());
        
        Function<Integer, Apple> fia = Apple::new;
        Function<Integer, Apple> fia2 = w -> new Apple(w);
        System.out.printf("%s == %s%n", fia.apply(42), fia2.apply(42));
        
        BiFunction<String, Integer, Apple> bfisa = Apple::new;
        BiFunction<String, Integer, Apple> bfisa2 = (c, w) -> new Apple(c, w);
        
        System.out.printf("%s == %s%n", bfisa.apply("red", 42), bfisa2.apply("red", 42));        
    }
}
