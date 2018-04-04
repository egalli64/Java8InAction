package dd.ch14;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public class P1Everywhere {
    public static void main(String[] args) {
        Function<String, Integer> stringToInt = Integer::parseInt;
        System.out.println(stringToInt.apply("42"));

        currying();
    }

    private static void currying() {
        DoubleUnaryOperator c2f = curriedConverter(9.0 / 5, 32);
        DoubleUnaryOperator usd2gbp = curriedConverter(0.6, 0);
        DoubleUnaryOperator km2mi = curriedConverter(0.6214, 0);

        System.out.println("Celsius to Fahrenheit");
        System.out.println("Plain: " + converter(24, 9.0 / 5, 32));
        System.out.println("Curry: " + c2f.applyAsDouble(24));
        
        System.out.println("USD to GBP: " + usd2gbp.applyAsDouble(100));
        System.out.println("Km to Mi: " + km2mi.applyAsDouble(20));
    }

    static double converter(double x, double f, double b) {
        return x * f + b;
    }

    static DoubleUnaryOperator curriedConverter(double y, double z) {
        return (double x) -> x * y + z;
    }
}
