package dd.ch10;

import java.util.Optional;

public class P3cRejectWFilter {
    static final String INSURANCE_NAME = "Such and Such";

    public static void main(String[] args) {
        Insurance insurance = new Insurance(INSURANCE_NAME);
        if (insurance != null && INSURANCE_NAME == insurance.getName()) {
            System.out.println("OK");
        }

        Optional<Insurance> oi = Optional.of(new Insurance("Such and Such"));
        oi.filter(i -> INSURANCE_NAME == i.getName()).ifPresent(x -> System.out.println("OK"));

        System.out.println("Quiz: filtering an optional");
        Optional<PersonPlus> opp = Optional.of(new PersonPlus());
        System.out.println(getCarInsuranceName(opp, 42));
    }

    public static String getCarInsuranceName(Optional<PersonPlus> person, int minAge) {
        return person.filter(p -> p.getAge() > minAge).flatMap(PersonPlus::getCar)
                .flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }
}

class PersonPlus extends Person {
    public int getAge() {
        return 43;
    }
}
