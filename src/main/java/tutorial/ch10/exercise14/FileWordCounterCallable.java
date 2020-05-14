package tutorial.ch10.exercise14;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;

import tutorial.ch10.exercise12.FileWordCounter;

public class FileWordCounterCallable implements Callable<Map<String, Long>> {

    private final Path path;
    private final FileWordCounter counter;

    public FileWordCounterCallable(final Path path) {
        this.path = path;
        this.counter = new FileWordCounter();
    }

    @Override
    public Map<String, Long> call() throws Exception {
        return counter.count(path);
    }
}
