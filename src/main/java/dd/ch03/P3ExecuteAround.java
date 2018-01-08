package dd.ch03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class P3ExecuteAround {
    private static final String FILENAME = P3ExecuteAround.class
            .getResource("/lambdasinaction/chap3/data.txt").getPath();

    public static String processFileLimited() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            return br.readLine();
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    public static String processFile(BRProcessor p) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            return p.process(br);
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    public interface BRProcessor {
        public String process(BufferedReader b);
    }
}
