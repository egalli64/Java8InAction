package dd.ch10;

import java.util.NoSuchElementException;
import java.util.Optional;

public class P3aOptionalPatterns {
    public static void main(String[] args) {
        ClassicPerson cp = new ClassicPerson(new ClassicCar());
        System.out.println(cp.getCarInsuranceName());
        Person p = new Person();
        System.out.println(p.getCarInsuranceName());

        // Optional methods

        // get() - usually a bad idea
        Optional<Car> car = p.getCar();
        try {
            car.get();
        } catch (NoSuchElementException nse) {
            System.out.println("Do not get an optional if you can avoid it: " + nse);
        }

        // orElse()
        Car other = new Car();
        System.out.println("orElse to fallback: " + car.orElse(other));

        // orElseGet()
        System.out.println("orElseGet to lazily fallback: " + car.orElseGet(Car::new));

        // orElseThrow()
        try {
            car.orElseThrow(IllegalStateException::new);
        } catch (IllegalStateException ise) {
            System.out.println("Like get, but throws what you like: " + ise);
        }

        // ifPresent()
        car.ifPresent(System.out::println);
        Optional<Car> o2 = Optional.of(other);
        System.out.print("ifPresent consumes an optional, if present: ");
        o2.ifPresent(System.out::println);

        // flatMap()
        System.out.println("flatMap to get rid of extra optional while accessing fields");
        Optional<Insurance> ins = car.flatMap(Car::getInsurance);
        ins.ifPresent(System.out::println);

        // map()
        System.out.println("map to optionally extract a field: " + car.map(Car::getId));
        System.out.println(o2.map(Car::getId));
    }
}

class ClassicInsurance {
    private String name;

    public String getName() {
        return name;
    }
}

class Insurance {
    private String name;

    public Insurance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class ClassicCar {
    private ClassicInsurance insurance;

    public ClassicInsurance getInsurance() {
        return insurance;
    }
}

class Car {
    private Optional<Insurance> insurance = Optional.empty();
    private final int id = 42;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Car with " + (insurance.isPresent() ? "" : "no ") + "insurance";
    }
}

class ClassicPerson {
    private ClassicCar car;

    ClassicPerson(ClassicCar car) {
        this.car = car;
    }

    public String getCarInsuranceName() {
        if (car == null)
            return null;
        ClassicInsurance ci = car.getInsurance();
        if (ci == null)
            return null;
        return ci.getName();
    }
}

class Person {
    protected Optional<Car> car;

    public Person() {
        this.car = Optional.empty();
    }

    public Optional<Car> getCar() {
        return car;
    }

    public String getCarInsuranceName() {
        return car.flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }
}
