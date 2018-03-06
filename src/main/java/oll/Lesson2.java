/**
 * Copyright © 2016, Oracle and/or its affiliates. All rights reserved.
 *
 * JDK 8 MOOC Lesson 2 homework
 * 
 * Solution by Manny egalli64@gmail.com
 */
package oll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Speakjava (Simon Ritter)
 * @author Manny 
 *      framework slightly "improved" for some extra fun
 */
public class Lesson2 {
    private static final String WORD_REGEXP = "[- .:,]+";
    private static final String FILE_SONNET = Lesson2.class.getResource("/oll/SonnetI.txt").getPath();

    public static void main(String[] args) {
        System.out.println("JDK 8 Lambdas and Streams MOOC Lesson 2");

        System.out.print("Running exercise 1 solution... ");
        List<String> inputListA = Arrays.asList("The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The",
                "LAZY", "DOG");
        List<String> o1 = exercise1(inputListA);
        boolean ok1 = true;
        if (inputListA.size() != o1.size()) {
            ok1 = false;
        } else {
            for (int i = 0; i < inputListA.size(); i++) {
                if (!inputListA.get(i).toLowerCase().equals(o1.get(i))) {
                    ok1 = false;
                    break;
                }
            }
        }
        System.out.println(ok1 ? "OK" : "Failure");

        System.out.print("Running exercise 2 solution... ");
        List<String> o2 = exercise2(inputListA);
        boolean ok2 = true;
        if (o2.size() != 6) {
            ok1 = false;
        } else {
            if (!o2.contains("The") || !o2.contains("Quick") || !o2.contains("BROWN") || !o2.contains("Fox")
                    || !o2.contains("DOG")) {
                ok2 = false;
            }
        }
        System.out.println(ok2 ? "OK" : "Failure");

        System.out.print("Running exercise 3 solution... ");
        List<String> inputListB = Arrays.asList("The", "quick", "brown", "fox", "jumped", "over", "the",
                "lazy", "dog");

        String o3 = exercise3(inputListB);
        boolean ok3 = o3.equals("quick-brown-fox");
        System.out.println(ok3 ? "OK" : "Failure");

        System.out.print("Running exercise 4 solution... ");
        boolean ok4 = exercise4() == 14;
        System.out.println(ok4 ? "OK" : "Failure");

        System.out.print("Running exercise 5 solution... ");
        List<String> o5 = exercise5();
        System.out.println(o5.size() == 81 ? "OK" : "Failure");
        o5.sort(Comparator.naturalOrder());
        System.out.println(o5);

        System.out.print("Running exercise 6 solution...");
        List<String> o6 = exercise6();
        System.out.println(o6.equals(o5) ? "OK" : "Failure");

        System.out.print("Running exercise 7 solution...");
        List<String> o7 = exercise7();
        boolean ok7 = o7.size() == o6.size();
        for (int i = 1; i < o7.size(); i++) {
            if (o7.get(i).length() < o7.get(i - 1).length()) {
                ok7 = false;
                break;
            }
        }
        System.out.println(ok7 ? "OK" : "Failure");
    }

    /**
     * Exercise 1
     *
     * Create a new list with all the strings from original list converted to lower
     * case and print them out.
     */
    public static List<String> exercise1(List<String> input) {
        return input.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * Exercise 2
     *
     * Modify exercise 1 so that the new list only contains strings that have an odd
     * length
     */
    public static List<String> exercise2(List<String> input) {
        return input.stream().filter(s -> s.length() % 2 == 1).collect(Collectors.toList());
    }

    /**
     * Exercise 3
     *
     * Join the second, third and forth strings of the list into a single string,
     * where each word is separated by a hyphen (-). Print the resulting string.
     */
    public static String exercise3(List<String> input) {
        return input.stream().skip(1).limit(3).collect(Collectors.joining("-"));
    }

    /**
     * Count the number of lines in the file using the BufferedReader provided
     * 
     * @return
     */
    public static long exercise4() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_SONNET))) {
            return reader.lines().count();
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    /**
     * Using the BufferedReader to access the file, create a list of words with no
     * duplicates contained in the file. Print the words.
     * 
     * HINT: A regular expression, WORD_REGEXP, is already defined for your use.
     */
    public static List<String> exercise5() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_SONNET))) {
            return reader.lines().flatMap(s -> Stream.of(s.split(WORD_REGEXP))).map(String::toLowerCase)
                    .distinct().collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    /**
     * Using the BufferedReader to access the file create a list of words from the
     * file, converted to lower-case and with duplicates removed, which is sorted by
     * natural order. Print the contents of the list.
     */
    public static List<String> exercise6() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_SONNET))) {
            return reader.lines().flatMap(s -> Stream.of(s.split(WORD_REGEXP))).map(String::toLowerCase)
                    .distinct().sorted().collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    /**
     * Modify exercise6 so that the words are sorted by length
     */
    public static List<String> exercise7() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_SONNET))) {
            return reader.lines().flatMap(s -> Stream.of(s.split(WORD_REGEXP))).map(s -> s.toLowerCase())
                    .distinct().sorted((lhs, rhs) -> Integer.compare(lhs.length(), rhs.length()))
                    .collect(Collectors.toList());
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }
}
