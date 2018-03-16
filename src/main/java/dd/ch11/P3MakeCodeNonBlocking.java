package dd.ch11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class P3MakeCodeNonBlocking {
    private static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("ShopEasy"), new Shop("A"),
            new Shop("B"), new Shop("C"), new Shop("D"));

    private final static Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }

    public static void main(String[] args) {
        execute("sequential", () -> P3MakeCodeNonBlocking.findPrices("myPhone27S"));
        execute("parallel", () -> P3MakeCodeNonBlocking.findPricesParallel("myPhone27S"));
        execute("future", () -> P3MakeCodeNonBlocking.findPricesFuture("myPhone27S"));
        execute("future ex", () -> P3MakeCodeNonBlocking.findPricesFuture("myPhone27S"));
    }

    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture
                        .supplyAsync(() -> String.format("%s: %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());

        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static List<String> findPricesFutureEx(String product) {
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s: %.2f", shop.getName(), shop.getPrice(product)), executor))
                .collect(Collectors.toList());

        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
