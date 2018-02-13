package dd.ch06;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lambdasinaction.chap6.Dish;

public class P5Collector {
    public static void main(String[] args) {
        System.out.println("Implementing a list collector");
        List<Dish> d0 = Dish.menu.stream().collect(Collectors.toList());
        System.out.println(d0);
        List<Dish> d1 = Dish.menu.stream().collect(new ToListCollector<Dish>());
        System.out.println(d1);
        // shortcut for an IDENTITY_FINISH, CONCURRENT (not UNORDERED) collector
        List<Dish> d2 = Dish.menu.stream().collect(ArrayList::new, List::add, List::addAll);
        System.out.println(d2);
    }
}

class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    @Override
    public Supplier<List<T>> supplier() {
        // returns a function that creates an instance of an empty accumulator
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        // returns a function that adds the current item to the list containing the
        // already traversed ones
        return List::add;
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        // returns a function that transforms the accumulator object into the result
        // no need of a transformation here
        return Function.identity();
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        // how to combines partial results in case of parallel processing
        return (lhs, rhs) -> {
            lhs.addAll(rhs);
            return lhs;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }
}
