package tutorial.ch10.exercise12;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class FileWordCounterCallable implements Callable<Void> {

    private final FileWordCounter counter;
    private final BlockingQueue<Map<String, Long>> wordDictionary;
    private final Path file;

    public FileWordCounterCallable(final BlockingQueue<Map<String, Long>> wordDictionary, final Path file) {
        this.counter = new FileWordCounter();
        this.wordDictionary = wordDictionary;
        this.file = file;
    }

    @Override
    public Void call() {
        try {
            wordDictionary.put(counter.count(file));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
