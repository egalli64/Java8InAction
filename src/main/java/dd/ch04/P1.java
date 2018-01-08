package dd.ch04;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lambdasinaction.chap4.Dish;

public class P1 {
    public static void main(String[] args) {
        System.out.println(getLowCaloricDishesNamesClassic(Dish.menu));
        System.out.println(getLowCaloricDishesNamesModern(Dish.menu));
    }

    private static List<String> getLowCaloricDishesNamesClassic(List<Dish> dishes) {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }

        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2) {
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });

        List<String> result = new ArrayList<>();
        for (Dish d : lowCaloricDishes) {
            result.add(d.getName());
        }
        return result;
    }

    private static List<String> getLowCaloricDishesNamesModern(List<Dish> dishes) {
        return dishes.stream().filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories)).map(Dish::getName).collect(toList());
    }
}
