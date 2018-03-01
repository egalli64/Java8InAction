package dd.ch08;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lambdasinaction.chap6.Dish;

public class P1RefactoringReadability {
    public static void main(String[] args) {
        anonymousToLambda();
        lambdaToMethodReference();
        imperativeToStream();
    }

    public static void anonymousToLambda() {
        // From anonymous classes to lambda expressions
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class");
            }
        };
        new Thread(r1).start();
        new Thread(() -> System.out.println("Hello from lambda")).start();

        // no shadowing in lambda (is this a problem?)
        int a = 10;

        new Thread(() -> {
            // int a = 42; // not allowed
            int b = 42;
            System.out.println("Hello from lambda " + a + ", " + b);
        }).start();

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 42;
                System.out.println("Hello from anonymous class " + a);
            }
        };
        new Thread(r2).start();
    }

    public static void lambdaToMethodReference() {
        // methods are more readable than lambda
        // use helpers to improve readability
        int cals = Dish.menu.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1 + c2);
        int cals2 = Dish.menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(cals + " == " + cals2);
    }

    public static void imperativeToStream() {
        List<String> dishes = new ArrayList<>();
        for (Dish dish : Dish.menu) {
            if (dish.getCalories() > 300) {
                dishes.add(dish.getName());
            }
        }
        System.out.println(dishes);

        dishes = Dish.menu./* parallel */stream().filter(d -> d.getCalories() > 300)
                .map(Dish::getName).collect(Collectors.toList());
        System.out.println(dishes);

    }
}
