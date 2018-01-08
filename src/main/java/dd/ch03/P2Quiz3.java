package dd.ch03;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

// valid uses of lambda expressions
public class P2Quiz3 {
    public static void main(String[] args) {
        // 1a
        execute(() -> System.out.println("hello"));

        // 2b
        try {
            System.out.println(fetch().call());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3
        // Predicate<Apple> p = (Apple a) -> a.getWeight();
        Predicate<String> p = s -> s.length() < 10;
        String name = "Tom";
        if(p.test(name)) {
            System.out.println("short name");
        }

    }

    // 1b
    public static void execute(Runnable r) {
        r.run();
    }

    // 2b
    public static Callable<String> fetch() {
        return () -> "Tricky example ;-)";
    }

}
