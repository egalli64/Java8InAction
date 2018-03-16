package dd.ch11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {
    private final Random random = new Random();
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        System.out.println(314);
    }

    public double getPlainPrice(String product) {
        return calculatePrice(product);
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }


    public Future<Double> getPriceAsyncVerbose(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            future.complete(calculatePrice(product));
        }).start();
        return future;
    }

    public Future<Double> getPriceAsyncVerboseEx(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                future.complete(calculatePrice(product));
            } catch (Exception ex) {
                future.completeExceptionally(ex);
            }
        }).start();
        return future;
    }

    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        Util.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public String getName() {
        return name;
    }
}
