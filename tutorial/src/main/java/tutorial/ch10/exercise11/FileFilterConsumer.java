package tutorial.ch10.exercise11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileFilterConsumer extends AbstractFileConsumer {

    private final String keyWord;
    private final CopyOnWriteArrayList<Path> resultList;

    public FileFilterConsumer(final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue, final String keyWord, final CopyOnWriteArrayList<Path> resultList) {
        super(fileQueue);
        this.keyWord = keyWord;
        this.resultList = resultList;
    }

    @Override
    protected void processFile(final Path file) {
        if (contains(file)) {
            resultList.add(file);
        }
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
