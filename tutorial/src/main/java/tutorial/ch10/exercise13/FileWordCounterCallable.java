package tutorial.ch10.exercise13;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import tutorial.ch10.exercise12.FileWordCounter;

public class FileWordCounterCallable implements Callable<Map<String, Long>> {

    private final FileWordCounter counter;
    private final Path file;

    public FileWordCounterCallable(final Path file) {
        this.counter = new FileWordCounter();
        this.file = file;
    }

    @Override
    public Map<String, Long> call() {
        return counter.count(file);
    }
}
