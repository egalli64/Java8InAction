package dd.ch07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class P3Spliterator {
    private static final String SENTENCE = " Nel   mezzo del cammin  di nostra  vita "
            + "mi  ritrovai in una  selva oscura" + " che la  dritta via era   smarrita ";

    public static void main(String[] args) {
        System.out.println("Found " + WordCount.iteratively(SENTENCE) + " words");
        System.out.println("Found " + WordCount.byStream(SENTENCE) + " words");
        System.out.println("Found " + WordCount.byBuggedParallelStream(SENTENCE) + " words");
        System.out.println("Found " + WordCount.byParallelStream(SENTENCE) + " words");
    }
}

class WordCount {
    public static int iteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace)
                    counter++;
                lastSpace = Character.isWhitespace(c);
            }
        }
        return counter;
    }

    public static int byStream(String s) {
        Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(s::charAt);
        return byStream(stream);
    }

    public static int byBuggedParallelStream(String s) {
        Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(s::charAt);
        return byStream(stream.parallel());
    }

    public static int byParallelStream(String s) {
        Spliterator<Character> spliterator = new WordCounterSpliterator(s);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        return byStream(stream);
    }

    public static int byStream(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate,
                WordCounter::combine);
        return wordCounter.getCounter();
    }
}

class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCounter(counter, true);
        } else {
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    public WordCounter combine(WordCounter other) {
        return new WordCounter(counter + other.counter, other.lastSpace);
    }

    public int getCounter() {
        return counter;
    }
}

class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int curPos = 0;

    WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(curPos++));
        return curPos < string.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - curPos;
        if (currentSize < 10) {
            // no more splitting
            return null;
        }

        // find a whitespace before splitting
        for (int splitPos = currentSize / 2 + curPos; splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                Spliterator<Character> spliterator = new WordCounterSpliterator(
                        string.substring(curPos, splitPos));
                curPos = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - curPos;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
