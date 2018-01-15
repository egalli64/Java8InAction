package dd.ch05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lambdasinaction.chap4.Dish;

public class P2Mapping {
    public static void main(String[] args) {
        // Applying a function to each element of a stream
        List<String> names = Dish.menu.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println(names);

        // strings to lengths
        List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");
        List<Integer> lengths = words.stream().map(String::length).collect(Collectors.toList());
        System.out.println(words + " -> " + lengths);

        // dishes to lengths
        lengths = Dish.menu.stream().map(Dish::getName).map(String::length)
                .collect(Collectors.toList());
        System.out.println(lengths);

        // Flattening streams - get all the unique characters from a list of words

        // don't work: map-split generates a stream of String[]
        words.stream().map(word -> word.split("")).distinct().collect(Collectors.toList());

        // we need to flat-map them!
        List<String> uStr = words.stream().map(w -> w.split("")).flatMap(Arrays::stream).distinct()
                .collect(Collectors.toList());
        System.out.println(uStr);
        List<Character> uChr = words.stream().map(w -> w.split("")).flatMap(Arrays::stream)
                .map(w -> w.charAt(0)).distinct().collect(Collectors.toList());
        System.out.println(uChr);

        // Mapping quizzes
        // 1. Given a list of numbers, return a list of the square of each number
        System.out.println("Quiz 1");
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = input.stream().map(i -> i * i).collect(Collectors.toList());
        System.out.println(input + " -> " + squares);

        // 2. Given two lists of numbers, return all pairs of numbers
        System.out.println("Quiz 2");
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(3, 4);

        List<int[]> pairs = a.stream().flatMap(i -> b.stream().map(j -> new int[] { i, j }))
                .collect(Collectors.toList());
        pairs.forEach(item -> System.out.println(Arrays.toString(item)));

        // 3. As 2, but return only pairs whose sum is divisible by 3
        System.out.println("Quiz 3");
        pairs = a.stream().flatMap(i -> b.stream()
                // filtering only the "good" ones
                .filter(j -> (i + j) % 3 == 0)
                // then mapping as before
                .map(j -> new int[] { i, j })).collect(Collectors.toList());
        pairs.forEach(item -> System.out.println(Arrays.toString(item)));

    }
}
