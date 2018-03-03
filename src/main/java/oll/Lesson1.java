/**
 * Copyright © 2016, Oracle and/or its affiliates. All rights reserved.
 *
 * JDK 8 MOOC Lesson 1 homework
 * 
 * Solution by Manny egalli64@gmail.com
 */
package oll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Speakjava (Simon Ritter)
 * @author Manny framework slightly "improved" just to have more fun
 */
public class Lesson1 {

    public static void main(String[] args) {
        System.out.println("JDK 8 Lambdas and Streams MOOC Lesson 1");
        System.out.print("Running exercise 1 solution... ");

        System.out.println(exercise1().equals("abcdef") ? "OK" : "Failure");

        List<String> r2 = exercise2();
        boolean ok2 = r2.size() == 1 && r2.contains("echo");
        System.out.print("Running exercise 2 solution... ");
        System.out.println(ok2 ? "OK" : "Failure");

        List<String> i3 = Arrays.asList("alpha", "bravo", "charlie", "delta", "echo", "foxtrot");
        List<String> r3 = exercise3(i3);
        System.out.print("Running exercise 3 solution... ");
        boolean ok3 = r3.size() == i3.size();
        for (int i = 0; i < i3.size(); i++) {
            if (!i3.get(i).toUpperCase().equals(r3.get(i))) {
                ok3 = false;
                break;
            }
        }
        System.out.println(ok3 ? "OK" : "Failure");

        System.out.print("Running exercise 4 solution... ");
        System.out.println(exercise4().equals("a1b2c3") ? "OK" : "Failure");

        System.out.println("Running exercise 5 solution...");
        exercise5();
        System.out.println("OK, if \"12345678910\" in the line above");
    }

    /**
     * All exercises should be completed using Lambda expressions and the new
     * methods added to JDK 8 where appropriate. There is no need to use an explicit
     * loop in any of the code. Use method references rather than full lambda
     * expressions wherever possible.
     */
    /**
     * Exercise 1
     *
     * Create a string that consists of the first letter of each word in the list of
     * Strings provided.
     */
    public static String exercise1() {
        List<String> list = Arrays.asList("alpha", "bravo", "charlie", "delta", "echo", "foxtrot");

        StringBuilder sb = new StringBuilder();
        list.forEach(s -> {
            if (!s.isEmpty()) {
                sb.append(s.charAt(0));
            }
        });
        return sb.toString();
    }

    /**
     * Exercise 2
     *
     * Remove the words that have odd lengths from the list.
     */
    public static List<String> exercise2() {
        List<String> list = new ArrayList<>(
                Arrays.asList("alpha", "bravo", "charlie", "delta", "echo", "foxtrot"));

        list.removeIf(s -> s.length() % 2 != 0);
        return list;
    }

    /**
     * Exercise 3
     *
     * Replace every word in the list with its upper case equivalent.
     */
    public static List<String> exercise3(List<String> input) {
        List<String> list = new ArrayList<>(input);
        list.replaceAll(String::toUpperCase);
        return list;
    }

    /**
     * Exercise 4
     *
     * Convert every key-value pair of the map into a string and append them all
     * into a single string, in iteration order.
     */
    public static String exercise4() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 3);
        map.put("b", 2);
        map.put("a", 1);

        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            sb.append(k);
            sb.append(v);
        });
        
        return sb.toString();
    }

    /**
     * Exercise 5
     *
     * Create a new thread that prints the numbers from the list.
     */
    public static void exercise5() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Thread t = new Thread(() -> list.forEach(System.out::print));
        t.run();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
