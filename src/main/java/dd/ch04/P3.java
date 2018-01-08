package dd.ch04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class P3 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.forEach(System.out::println);

        try {
            s.forEach(System.out::println);
        } catch (IllegalStateException ise) {
            System.out.println("IllegalStateException, as expected: " + ise.getMessage());
        }
    }
}
