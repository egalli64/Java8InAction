package dd.ch02;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsCollectionContaining.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class FilteringApplesTest {
    private final Apple red100 = new Apple("red", 100);
    private final Apple green200 = new Apple("green", 200);
    private final Apple red200 = new Apple("red", 200);
    private final List<Apple> apples = Arrays.asList(red100, green200, red200);

    @Test
    void testFilterGreenApples() {
        List<Apple> filtered = FilteringApples.filterGreenApples(apples);
        assertThat(filtered.size(), is(equalTo(1)));
        assertThat(filtered, hasItem(green200));
    }

    @Test
    void testFilterApplesByColor() {
        List<Apple> green = FilteringApples.filterApplesByColor(apples, "green");
        assertThat(green.size(), is(equalTo(1)));
        assertThat(green, hasItem(green200));

        List<Apple> red = FilteringApples.filterApplesByColor(apples, "red");
        assertThat(red.size(), is(equalTo(2)));
        assertThat(red, hasItem(red100));
        assertThat(red, hasItem(red200));
    }

    @Test
    void testFilterApplesByWeight() {
        List<Apple> filtered = FilteringApples.filterApplesByWeight(apples, 150);
        assertThat(filtered.size(), is(equalTo(2)));
        assertThat(filtered, hasItem(green200));
        assertThat(filtered, hasItem(red200));
    }

    @Test
    void testFilterApples() {
        Predicate<Apple> heavyRed = a -> a.getWeight() > 150 && a.getColor() == "red";

        List<Apple> filtered = FilteringApples.filterApples(apples, heavyRed);
        assertThat(filtered.size(), is(equalTo(1)));
        assertThat(filtered, hasItem(red200));
    }

    @Test
    void testGenericFilter() {
        Predicate<Apple> heavyRed = a -> a.getWeight() > 150 && a.getColor() == "red";

        List<Apple> filtered = FilteringApples.filter(apples, heavyRed);
        assertThat(filtered.size(), is(equalTo(1)));
        assertThat(filtered, hasItem(red200));
    }

    @Test
    void testFilteringByStream() {
        Predicate<Apple> heavyRed = a -> a.getWeight() > 150 && a.getColor() == "red";

        List<Apple> filtered = apples.stream().filter(heavyRed).collect(Collectors.toList());
        assertThat(filtered.size(), is(equalTo(1)));
        assertThat(filtered, hasItem(red200));
    }

    @Test
    void testPrettyPrintAppleSimple() {
        List<String> matches = Arrays.asList("A 100g. apple", "A 200g. apple", "A 200g. apple");

        AppleFormatter af = new SimpleAppleFormatter();
        List<String> descriptions = FilteringApples.prettyPrintApple(apples, af);
        assertThat(descriptions.size(), is(equalTo(apples.size())));
        for (String match : matches) {
            assertThat(descriptions, hasItem(match));
        }
    }

    @Test
    void testPrettyPrintAppleFancy() {
        List<String> matches = Arrays.asList("A red light apple", "A green heavy apple",
                "A red heavy apple");

        AppleFormatter af = new FancyAppleFormatter();
        List<String> descriptions = FilteringApples.prettyPrintApple(apples, af);
        assertThat(descriptions.size(), is(equalTo(apples.size())));
        for (String match : matches) {
            assertThat(descriptions, hasItem(match));
        }
    }

    @Test
    void testPrettyPrintAppleSimpleStream() {
        List<String> matches = Arrays.asList("A 100g. apple", "A 200g. apple", "A 200g. apple");

        Function<Apple, String> a2s = a -> "A " + a.getWeight() + "g. apple";
        List<String> descriptions = apples.stream().map(a2s).collect(Collectors.toList());
        for (String match : matches) {
            assertThat(descriptions, hasItem(match));
        }
    }

    @Test
    void testPrettyPrintAppleFancyStream() {
        List<String> matches = Arrays.asList("A red light apple", "A green heavy apple",
                "A red heavy apple");

        Function<Apple, String> a2s = a -> "A " + a.getColor() + " "
                + (a.getWeight() < 150 ? "light" : "heavy") + " apple";
        List<String> descriptions = apples.stream().map(a2s).collect(Collectors.toList());
        for (String match : matches) {
            assertThat(descriptions, hasItem(match));
        }
    }
}
