package dd.ch02;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FilteringApples {
    // 2.1.1 - classic filtering
    static List<Apple> filterGreenApples(List<Apple> apples) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getColor().equals("green")) {
                result.add(apple);
            }
        }

        return result;
    }

    // 2.1.2 - classic filtering w/ parameter
    static List<Apple> filterApplesByColor(List<Apple> apples, String color) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }

        return result;
    }

    // filter by weight ... etc
    static List<Apple> filterApplesByWeight(List<Apple> apples, int weight) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (apple.getWeight() >= weight) {
                result.add(apple);
            }
        }

        return result;
    }

    // 2.2.1 DRY using a predicate
    static List<Apple> filterApples(List<Apple> apples, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : apples) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    // make it generic
    static <T> List<T> filter(List<T> items, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T item : items) {
            if (p.test(item)) {
                result.add(item);
            }
        }

        return result;
    }

    // quiz 2.1 (slightly changed 'coz not good printing to standard out)
    public static List<String> prettyPrintApple(List<Apple> inventory, AppleFormatter af) {
        List<String> result = new ArrayList<String>();

        for (Apple apple : inventory) {
            result.add(af.apply(apple));
        }

        return result;
    }
}

interface AppleFormatter extends Function<Apple, String> {
}

class SimpleAppleFormatter implements AppleFormatter {
    @Override
    public String apply(Apple apple) {
        return "A " + apple.getWeight() + "g. apple";
    }
}

class FancyAppleFormatter implements AppleFormatter {
    @Override
    public String apply(Apple apple) {
        return "A " + apple.getColor() + " " + (apple.getWeight() < 150 ? "light" : "heavy")
                + " apple";
    }
}
