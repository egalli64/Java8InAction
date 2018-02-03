package dd.ch06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lambdasinaction.chap4.Dish;

public class P2ReduceSum {
    public static void main(String[] args) {
        System.out.println(Dish.menu);
        // count the number of dishes in the menu
        System.out.println("# dishes: " + Dish.menu.stream().collect(Collectors.counting()));
        System.out.println("# dishes: " + Dish.menu.stream().count());

        // maximum and minimum in a stream of values
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalDish = Dish.menu.stream()
                .collect(Collectors.maxBy(dishCaloriesComparator));
        mostCalDish.ifPresent(System.out::println);

        // Summarization
        int totCal = Dish.menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println("Total number of calories in menu: " + totCal);

        double avgCal = Dish.menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.printf("Average calories for dish in menu: %.2f%n", avgCal);

        IntSummaryStatistics menuStats = Dish.menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(menuStats);

        // Joining Strings
        String m1 = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println(m1);
        // this shortcut won't work
        // String m2 = Dish.menu.stream().collect(Collectors.joining());
        String m3 = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println(m3);

        // Generalized summarization with reduction
        int totCal2 = Dish.menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println("Total number of calories in menu: " + totCal2);

        Optional<Dish> mostCalDish2 = Dish.menu.stream().collect(
                Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        mostCalDish2.ifPresent(System.out::println);

        // collect vs reduce
        // reduce could work like a collector (but don't do it, it's a misuse)

        List<Integer> someNbrs = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> numbers = someNbrs.stream().reduce(new ArrayList<Integer>(),
                (List<Integer> lst, Integer e) -> {
                    lst.add(e);
                    return lst;
                }, (List<Integer> lhs, List<Integer> rhs) -> {
                    lhs.addAll(rhs);
                    return lhs;
                });
        System.out.println(numbers);

        // Collection framework flexibility
        int tc3 = Dish.menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        System.out.println("Total number of calories in menu: " + tc3);

        Optional<Integer> tc4 = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum);
        System.out.println("Total number of calories in menu: " + tc4.orElse(0));

        int tc5 = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("Total number of calories in menu: " + tc5);

        // quiz 6.1
        String sm = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println(sm);

        // 1.
        String sm1 = Dish.menu.stream().map(Dish::getName)
                .collect(Collectors.reducing((s1, s2) -> s1 + s2)).get();
        System.out.println(sm1);

        String sm1b = Dish.menu.stream().map(Dish::getName)
                .collect(Collectors.reducing(String::concat)).get();
        System.out.println(sm1b);

        // 2. mismatch at compile time
        // String sm2 = Dish.menu.stream()
        // .collect(Collectors.reducing( (d1, d2) -> d1.getName() + d2.getName() )
        // ).get();

        // 3.
        String sm3 = Dish.menu.stream().collect(
                Collectors.reducing("", Dish::getName, (s1, s2) -> s1 + s2));
        System.out.println(sm3);

        String sm3b = Dish.menu.stream().collect(
                Collectors.reducing("", Dish::getName, String::concat));
        System.out.println(sm3b);
    }
}
