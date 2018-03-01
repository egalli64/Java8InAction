package dd.ch08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class P2RefactoringPatterns {
    public static void main(String[] args) {
        strategy();
        template();
        observer();
        chainOfReponsability();
        factory();
    }

    private static void strategy() {
        System.out.println("Strategy");
        // old school
        String target = "aaaa";
        // Validator v1 = new Validator(new IsNumeric());
        // System.out.println(target + " is numeric? " + v1.validate(target));
        // Validator v2 = new Validator(new IsAllLowerCase());
        // System.out.println(target + " is all lowercase? " + v2.validate(target));

        // with lambdas
        Validator v3 = new Validator(s -> s.matches("\\d+"));
        System.out.println(target + " is numeric? " + v3.validate(target));
        Validator v4 = new Validator(s -> s.matches("[a-z]+"));
        System.out.println(target + " is all lowercase? " + v4.validate(target));
    }

    private static void template() {
        System.out.println("Template method");
        // classic
        OnlineBanking ob = new MyOnlineBanking();
        ob.processCustomer(42);

        // lambda
        OnlineBankingModern obm = new OnlineBankingModern();
        obm.processCustomer(42, c -> System.out.println("Hello " + c.getName() + "!"));
    }

    private static void observer() {
        System.out.println("Observer");

        // classic
        // Feed feed = new Feed();
        // feed.registerObserver(new NYTimes());
        // feed.registerObserver(new Guardian());
        // feed.registerObserver(new LeMonde());
        // feed.notifyObservers("The queen favourite book is Java 8 in Action!");

        // modern with lambda
        Feed feed = new Feed();

        // NYTimes
        feed.register(tweet -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        // Guardian
        feed.register(tweet -> {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });

        feed.notify("Money money money, give me money!");
    }

    private static void chainOfReponsability() {
        System.out.println("Chain of responsibility");

        // classic
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2);
        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);

        // lambda
        UnaryOperator<String> headerProcessing = s -> "From Raoul, Mario and Alan: " + s;
        UnaryOperator<String> spellCheckerProcessing = s -> s.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }

    private static void factory() {
        // classic
        System.out.println("Factory");
        Product p = ProductFactory.createProduct("loan");
        System.out.println(p.getClass().getSimpleName());
        
        // lambda
        Product p2 = ProductFactory.createProductLambda("loan");
        System.out.println(p2.getClass().getSimpleName());        
    }
}

/**
 * strategy
 */

// interface ValidationStrategy {
// public boolean execute(String s);
// }
//
// class IsAllLowerCase implements ValidationStrategy {
// public boolean execute(String s) {
// return s.matches("[a-z]+");
// }
// }
//
// class IsNumeric implements ValidationStrategy {
// public boolean execute(String s) {
// return s.matches("\\d+");
// }
// }

class Validator {
    // private final ValidationStrategy strategy;
    private final Predicate<String> strategy;

    // public Validator(ValidationStrategy strategy) {
    public Validator(Predicate<String> strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        // return strategy.execute(s);
        return strategy.test(s);
    }
}

/**
 * Template method
 */

abstract class OnlineBanking {
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);

}

class OnlineBankingModern {
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }
}

class Customer {
    public String getName() {
        return "Tom";
    }
}

class Database {
    static Customer getCustomerWithId(int id) {
        return new Customer();
    }
}

class MyOnlineBanking extends OnlineBanking {

    @Override
    public void makeCustomerHappy(Customer c) {
        System.out.println("Hello " + c.getName() + "!");
    }
}

/**
 * observer
 */

// interface Observer {
// void inform(String tweet);
// }

interface Subject {
    // void registerObserver(Observer o);
    void register(Consumer<String> c);

    void notify(String tweet);
}

// class NYTimes implements Observer {
// @Override
// public void inform(String tweet) {
// if (tweet != null && tweet.contains("money")) {
// System.out.println("Breaking news in NY!" + tweet);
// }
// }
// }
//
// class Guardian implements Observer {
// @Override
// public void inform(String tweet) {
// if (tweet != null && tweet.contains("queen")) {
// System.out.println("Yet another news in London... " + tweet);
// }
// }
// }
//
// class LeMonde implements Observer {
// @Override
// public void inform(String tweet) {
// if (tweet != null && tweet.contains("wine")) {
// System.out.println("Today cheese, wine and news! " + tweet);
// }
// }
// }

class Feed implements Subject {
    private final List<Consumer<String>> observers = new ArrayList<>();

    // public void registerObserver(Observer o) {
    // this.observers.add(o);
    // }

    @Override
    public void register(Consumer<String> observer) {
        this.observers.add(observer);
    }

    @Override
    public void notify(String tweet) {
        observers.forEach(o -> o.accept(tweet));
    }
}

/**
 * Chain of responsibility
 */
abstract class ProcessingObject<T> {
    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handle(T input) {
        T r = handleWork(input);
        if (successor != null) {
            return successor.handle(r);
        }
        return r;
    }

    abstract protected T handleWork(T input);
}

class HeaderTextProcessing extends ProcessingObject<String> {
    public String handleWork(String text) {
        return "From Raoul, Mario and Alan: " + text;
    }
}

class SpellCheckerProcessing extends ProcessingObject<String> {
    public String handleWork(String text) {
        return text.replaceAll("labda", "lambda");
    }
}

/**
 * Factory
 */

interface Product {
}

class Loan implements Product {
}

class Stock implements Product {
}

class Bond implements Product {
}

class ProductFactory {
    public static Product createProduct(String name) {
        switch (name) {
        case "loan":
            return new Loan();
        case "stock":
            return new Stock();
        case "bond":
            return new Bond();
        default:
            throw new RuntimeException("No such product " + name);
        }
    }

    public static Product createProductLambda(String name) {
        Supplier<Product> p = map.get(name);
        if (p != null)
            return p.get();
        throw new RuntimeException("No such product " + name);
    }

    final static private Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }
}