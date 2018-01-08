package dd.ch03;

import java.util.function.DoubleFunction;

public class P9 {
    public static void main(String[] args) {
        System.out.println(integrate(x -> x + 10, 3, 7));
    }

    public static double integrate(DoubleFunction<Double> f, double left, double right) {
        return (f.apply(left) + f.apply(right)) * (right - left) / 2;
    }
}
