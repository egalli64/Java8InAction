package dd.ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class P4 {
    public static void main(String[] args) {
        // 3.2 Working with a Predicate
        List<String> listOfStrings = Arrays.asList("", "hello", "");
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
        System.out.println(nonEmpty);

        // 3.3 Working with a Consumer
        forEach(listOfStrings, s -> {
            if(! s.isEmpty())
                System.out.println(s);
        });
        
        // 3.4. Working with a Function
        List<Integer> listOfLengths = map(listOfStrings, s -> s.length());
        System.out.println(listOfLengths);
    }

    // 3.2 b
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T s : list) {
            if (p.test(s)) {
                results.add(s);
            }
        }
        return results;
    }
    
    // 3.3 b
    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for(T element: list) {
            c.accept(element);
        }
    }
    
    // 3.4 b
    public static <T, R> List<R> map(List<T> list, Function<T, R> f){
        List<R> result = new ArrayList<>();
        for(T element: list) {
            result.add(f.apply(element));
        }
        return result;
    }
}
