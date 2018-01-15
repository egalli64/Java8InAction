package dd.ch05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lambdasinaction.chap5.Trader;
import lambdasinaction.chap5.Transaction;

public class P5Practice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950));

        System.out.println(transactions);

        System.out.println("1. Find all transactions in the year 2011 and sort them by asc value.");
        List<Transaction> result = transactions.stream().filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println(result);

        System.out.println("2. What are all the unique cities where the traders work?");
        List<String> cities = transactions.stream().map(t -> t.getTrader().getCity()).distinct()
                .collect(Collectors.toList());
        Set<String> ciSet = transactions.stream().map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());
        System.out.println(cities + " or " + ciSet);

        System.out.println("3. Find all traders from Cambridge and sort them by name.");
        List<Trader> traders = transactions.stream().map(Transaction::getTrader).distinct()
                .filter(t -> t.getCity() == "Cambridge")
                .sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println(traders);

        System.out.println("4. Return a string of all traders’ names sorted alphabetically.");
        Optional<String> tradOpt = transactions.stream().map(t -> t.getTrader().getName())
                .distinct().sorted().reduce(String::concat);
        tradOpt.ifPresent(System.out::println);
        String tradJ = transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted()
                .collect(Collectors.joining());
        System.out.println(tradJ);

        System.out.println("5. Are any traders based in Milan?");
        boolean milan = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(milan);

        System.out
                .println("6. Print all transactions’ values from the traders living in Cambridge.");
        List<Integer> values = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println(values);

        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue).forEach(System.out::println);

        System.out.println("7. What’s the highest value of all the transactions?");
        Optional<Integer> highest = transactions.stream().map(Transaction::getValue)
                .reduce(Integer::max);
        highest.ifPresent(System.out::println);

        System.out.println("8. Find the transaction with the smallest value.");
        Optional<Transaction> t = transactions.stream()
                .reduce((u, v) -> (u.getValue() < v.getValue()) ? u : v);
        t.ifPresent(System.out::println);
    }
}
