package dd.ch03;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import dd.ch03.P3ExecuteAround.BRProcessor;

class P3ExecuteAroundTest {
    @Test
    void testProcessFileLimited() {
        assertThat(P3ExecuteAround.processFileLimited(), equalTo("Java"));
    }

    @Test
    void testProcessFileOneLine() {
        BRProcessor processor = b -> {
            try {
                return b.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        };
        assertThat(P3ExecuteAround.processFile(processor), equalTo("Java"));
    }

    @Test
    void testProcessFileTwoLines() {
        BRProcessor processor = b -> {
            try {
                return b.readLine() + b.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);            }
        };
        assertThat(P3ExecuteAround.processFile(processor), equalTo("Java8"));
    }
}
