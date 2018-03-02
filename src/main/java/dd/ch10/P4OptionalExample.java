package dd.ch10;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class P4OptionalExample {
    public static void main(String[] args) {
        System.out.println("Wrapping a potentially null value in an optional");
        Map<String, String> map = new HashMap<>();
        String value = map.get("key");
        System.out.println("We should avoid nulls: " + value);
        Optional<String> ov = Optional.ofNullable(map.get("key"));
        System.out.println("We should avoid nulls: " + ov);

        System.out.println("Converting a String into an Integer returning an optional");
        Optional<Integer> notAnInt = stringToInt("whatever");
        System.out.println("Not an int in the string: " + notAnInt);

        System.out.println("Reading duration from a property imperatively");
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        System.out.print(readDuration(props, "a") + " ");
        System.out.print(readDuration(props, "b") + " ");
        System.out.print(readDuration(props, "c") + " ");
        System.out.println(readDuration(props, "d"));

        System.out.println("Reading duration from a property by optional");
        System.out.print(durationX(props, "a").orElse(0) + " ");
        System.out.print(durationX(props, "b").orElse(0) + " ");
        System.out.print(durationX(props, "c").orElse(0) + " ");
        System.out.println(durationX(props, "d").orElse(0));

        System.out.print(duration(props, "a") + " ");
        System.out.print(duration(props, "b") + " ");
        System.out.print(duration(props, "c") + " ");
        System.out.println(duration(props, "d"));
    }

    public static int readDuration(Properties p, String name) {
        String value = p.getProperty(name);
        if (value != null) {
            try {
                int result = Integer.parseInt(value);
                if (result > 0) {
                    return result;
                }
            } catch (NumberFormatException nfe) {

            }
        }
        return 0;
    }

    public static Optional<Integer> durationX(Properties p, String name) {
        try {
            int value = Integer.parseInt(p.getProperty(name));
            return Optional.ofNullable(value > 0 ? value : null);
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }

    public static int duration(Properties p, String name) {
        return Optional.ofNullable(p.getProperty(name))
            .flatMap(P4OptionalExample::stringToInt).filter(i -> i > 0).orElse(0);
    }

    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }
}
