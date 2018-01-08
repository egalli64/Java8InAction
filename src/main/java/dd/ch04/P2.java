package dd.ch04;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lambdasinaction.chap4.Dish;

public class P2 {
    public static void main(String[] args) {
        List<String> amongTop = Dish.menu.stream()
                // getting 3 dishes from high calories
                .filter(d -> d.getCalories() > 300).limit(3)
                // interested just in the names
                .map(Dish::getName).collect(Collectors.toList());
        
        System.out.println(amongTop);

        List<String> topThree = Dish.menu.stream()
                // looking for top calories
                .sorted(Comparator.comparing(Dish::getCalories).reversed()).limit(3)
                // just in the names
                .map(Dish::getName).collect(Collectors.toList());
        System.out.println(topThree);
    }
}
