package tutorial.ch10.exercise11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class FileListProducer implements Runnable {

    private final BlockingQueue<Map.Entry<Path, Boolean>> queue;
    private final Path directory;

    public FileListProducer(final BlockingQueue<Map.Entry<Path, Boolean>> queue, final Path directory) {
        this.queue = queue;
        this.directory = directory;
    }

    @Override
    public void run() {
        addFilesToQueue(directory);
        putIntoTheQueue(directory, true);
    }

    private void addFilesToQueue(final Path directory) {
        try {
            Files.list(directory).forEach(path -> {
                if (path.toFile().isDirectory()) {
                    addFilesToQueue(path);
                } else {
                    putIntoTheQueue(path, false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putIntoTheQueue(final Path path, final Boolean end) {
        try {
            queue.put(Map.entry(path, end));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
