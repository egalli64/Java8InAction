package dd.ch10;

import java.util.Optional;

public class P3bCombiningOptionals {
    public static void main(String[] args) {
        Person p = new Person();
        Car c = new Car();
        System.out.println("Cheapest insurance is " + findCheapestInsurance(p, c));
        System.out.println("Cheapest insurance is "
                + safeFindCheapestInsurance(Optional.of(p), Optional.of(c)));
        System.out.println(
                "Cheapest insurance is " + simplerSafeFind(Optional.of(p), Optional.of(c)));
    }

    private static Insurance findCheapestInsurance(Person p, Car c) {
        return null;
    }

    private static Optional<Insurance> safeFindCheapestInsurance(Optional<Person> p,
            Optional<Car> c) {
        if (p.isPresent() && c.isPresent()) {
            return Optional.ofNullable(findCheapestInsurance(p.get(), c.get()));
        }

        return Optional.empty();
    }

    private static Optional<Insurance> simplerSafeFind(Optional<Person> person, Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }
}
