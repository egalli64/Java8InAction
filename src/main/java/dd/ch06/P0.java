package dd.ch06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class P0 {
    public static void main(String[] args) {
        System.out.println("Transactions");
        System.out.println(Transaction.items);
        System.out.println("Grouped by currency");
        System.out.println(classicGrouping(Transaction.items));
        System.out.println("Same, Java 8 style");
        System.out.println(modernGrouping(Transaction.items));
    }

    private static Map<Transaction.Currency, List<Transaction>> classicGrouping(
            List<Transaction> transactions) {
        Map<Transaction.Currency, List<Transaction>> result = new HashMap<>();
        for (Transaction transaction : transactions) {
            Transaction.Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency = result.get(currency);
            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                result.put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }

        return result;
    }
    
    private static Map<Transaction.Currency, List<Transaction>> modernGrouping(List<Transaction> transactions) {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::getCurrency));
    }
}
