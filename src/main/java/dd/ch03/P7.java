package dd.ch03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import dd.ch02.Apple;

public class P7 {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple("green", 80), new Apple("green", 155),
                new Apple("red", 120));
        System.out.println(inventory);

        // 3.7.1
        inventory.sort(new AppleComparator());
        System.out.println("Sorting w/ full-fledged comparator: " + inventory);

        // 3.7.2
        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2) {
                return a1.getColor().compareTo(a2.getColor());
            }
        });
        System.out.println("Anonymous class: " + inventory);

        // 3.7.3
        inventory.sort((lhs, rhs) -> lhs.getWeight().compareTo(rhs.getWeight()));
        System.out.println("Lambda: " + inventory);

        inventory.sort(Comparator.comparing(a -> a.getColor()));
        System.out.println("Comparator: " + inventory);

        // 3.7.4
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println("Comparator w/ method reference: " + inventory);
    }

    private static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }
}
