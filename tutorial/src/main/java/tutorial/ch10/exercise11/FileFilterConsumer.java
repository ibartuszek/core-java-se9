package tutorial.ch10.exercise11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileFilterConsumer implements Callable<Void> {

    private final String keyWord;
    private final CopyOnWriteArrayList<Path> resultList;
    private final Path file;

    public FileFilterConsumer(final String keyWord, final CopyOnWriteArrayList<Path> resultList, final Path file) {
        this.keyWord = keyWord;
        this.resultList = resultList;
        this.file = file;
    }

    @Override
    public Void call() {
        if (contains(file)) {
            resultList.add(file);
        }
        return null;
    }

    private boolean contains(final Path path) {
        boolean result = false;
        try {
            result = Files.lines(path)
                .anyMatch(line -> line.contains(keyWord));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
