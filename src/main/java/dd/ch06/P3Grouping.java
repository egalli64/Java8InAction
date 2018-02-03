package dd.ch06;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import lambdasinaction.chap6.Dish;

public class P3Grouping {
    enum CaloricLevel {
        DIET, NORMAL, FAT
    };

    public static void main(String[] args) {
        // Collectors.groupingBy /1
        System.out.println("Dishes grouped by type:");
        Map<Dish.Type, List<Dish>> dishes1 = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishes1);

        // Collectors.groupingBy /2 adhoc classificator
        System.out.println("Dishes grouped by caloric level:");
        Function<Dish, CaloricLevel> calClassificator = dish -> {
            if (dish.getCalories() <= 400)
                return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700)
                return CaloricLevel.NORMAL;
            else
                return CaloricLevel.FAT;
        };

        Map<CaloricLevel, List<Dish>> dishes2 = Dish.menu.stream()
                .collect(Collectors.groupingBy(calClassificator));
        System.out.println(dishes2);

        // Multilevel grouping
        System.out.println("Dishes grouped by type and caloric level:");
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishes3 = Dish.menu.stream().collect(
                Collectors.groupingBy(Dish::getType, Collectors.groupingBy(calClassificator)));
        System.out.println(dishes3);

        // Collecting data in subgroups
        // collect - groupingBy - counting
        System.out.println("Count dishes in groups:");
        Map<Dish.Type, Long> dishCounters = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println(dishCounters);

        // collect - groupingBy - reducing
        System.out.println("Most caloric dishes by type:");
        BinaryOperator<Dish> mostCalDish = (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1
                : d2;
        Map<Dish.Type, Optional<Dish>> dishes4 = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.reducing(mostCalDish)));
        System.out.println(dishes4);

        // collect - groupingBy - specialized reducer (maxBy)
        Comparator<Dish> dishCalCmp = Comparator.comparingInt(Dish::getCalories);
        Map<Dish.Type, Optional<Dish>> dishes4a = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(dishCalCmp)));
        System.out.println(dishes4a);

        // Adapting the collector result to a different type
        // collectingAndThen - reducing then optional get
        Map<Dish.Type, Dish> dishes5 = Dish.menu.stream().collect(Collectors.groupingBy(
                Dish::getType,
                Collectors.collectingAndThen(Collectors.reducing(mostCalDish), Optional::get)));
        System.out.println(dishes5);

        // collectingAndThen - specialized reducer (maxBy) then optional get
        Map<Dish.Type, Dish> dishes5a = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.collectingAndThen(Collectors.maxBy(dishCalCmp), Optional::get)));
        System.out.println(dishes5a);

        // groupingBy + summingInt
        System.out.println("Sum calories by type:");
        Map<Dish.Type, Integer> typeCals = Dish.menu.stream().collect(
                Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
        System.out.println(typeCals);

        // groupingBy + mapping
        System.out.println("Caloric levels by type:");
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.mapping(calClassificator, Collectors.toSet())));
        System.out.println(caloricLevelsByType);

        // same, but ensuring actual set type returned
        Map<Dish.Type, TreeSet<CaloricLevel>> calLev2 = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.mapping(calClassificator, Collectors.toCollection(TreeSet::new))));
        System.out.println(calLev2);
    }
}
