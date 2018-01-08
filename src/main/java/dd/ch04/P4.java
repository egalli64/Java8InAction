package dd.ch04;

import java.util.List;
import java.util.stream.Collectors;

import lambdasinaction.chap4.Dish;

public class P4 {
    public static void main(String[] args) {
        List<String> names = Dish.menu.stream()
                // log each filtered dishes
                .filter(d -> {
                    System.out.println("filtering " + d.getName());
                    return d.getCalories() > 300;
                }).limit(3)
                // log names when mapped
                .map(d -> {
                    System.out.println("mapping " + d.getName());
                    return d.getName();
                }).collect(Collectors.toList());

        System.out.println(names);
    }
}
