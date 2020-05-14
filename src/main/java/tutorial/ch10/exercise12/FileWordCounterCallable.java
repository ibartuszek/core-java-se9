package tutorial.ch10.exercise12;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import tutorial.ch10.exercise11.AbstractFileConsumer;

public class FileWordCounterCallable extends AbstractFileConsumer {

    private final FileWordCounter counter;
    private final BlockingQueue<Map<String, Long>> wordDictionary;

    public FileWordCounterCallable(final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue, final BlockingQueue<Map<String, Long>> wordDictionary) {
        super(fileQueue);
        this.counter = new FileWordCounter();
        this.wordDictionary = wordDictionary;
    }

    @Override
    protected void processFile(final Path file) {
        try {
            wordDictionary.put(counter.count(file));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
