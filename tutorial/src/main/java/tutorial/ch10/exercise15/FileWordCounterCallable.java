package tutorial.ch10.exercise15;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import tutorial.ch10.exercise12.FileWordCounter;

public class FileWordCounterCallable implements Callable<Void> {

    private final Path path;
    private final ConcurrentHashMap<String, Long> worDictionary;
    private final FileWordCounter counter;

    public FileWordCounterCallable(final Path path, final ConcurrentHashMap<String, Long> worDictionary) {
        this.path = path;
        this.worDictionary = worDictionary;
        this.counter = new FileWordCounter();
    }

    @Override
    public Void call() throws Exception {
        counter.count(path).forEach((key, value) -> worDictionary.merge(key, value, Long::sum));
        return null;
    }
}
