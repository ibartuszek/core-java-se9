package tutorial.ch10.exercise06;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

abstract class AbstractWordCatalogCallable implements Callable<Void> {

    protected final Path path;
    protected final ConcurrentHashMap<String, ConcurrentHashMap<File, Boolean>> catalog;

    public AbstractWordCatalogCallable(final Path path, final ConcurrentHashMap<String, ConcurrentHashMap<File, Boolean>> catalog) {
        this.path = path;
        this.catalog = catalog;
    }

}

class WordCatalogCallableWithMerge extends AbstractWordCatalogCallable {

    public WordCatalogCallableWithMerge(final Path path, final ConcurrentHashMap<String, ConcurrentHashMap<File, Boolean>> catalog) {
        super(path, catalog);
    }

    @Override
    public Void call() {
        File file = path.toFile();
        try {
            Files.lines(path)
                .flatMap(s -> Stream.of(s.split("\\PL+")))
                .forEach(word -> catalog.merge(word, new ConcurrentHashMap<>(Map.of(file, false)), this::union));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ConcurrentHashMap<File, Boolean> union(final ConcurrentHashMap<File, Boolean> first, final ConcurrentHashMap<File, Boolean> second) {
        ConcurrentHashMap<File, Boolean> result = new ConcurrentHashMap<>();
        result.putAll(first);
        result.putAll(second);
        return result;
    }

}

class WordCatalogCallableWithPutIfAbsent extends AbstractWordCatalogCallable {

    public WordCatalogCallableWithPutIfAbsent(final Path path, final ConcurrentHashMap<String, ConcurrentHashMap<File, Boolean>> catalog) {
        super(path, catalog);
    }

    @Override
    public Void call() {
        File file = path.toFile();
        try {
            Files.lines(path)
                .flatMap(s -> Stream.of(s.split("\\PL+")))
                .forEach(word -> catalog.merge(word, new ConcurrentHashMap<>(Map.of(file, false)),
                    (first, second) -> {
                        second.forEach(first::putIfAbsent);
                        return first;
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
