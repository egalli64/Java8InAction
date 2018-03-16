package dd.ch11;

public class Util {
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
