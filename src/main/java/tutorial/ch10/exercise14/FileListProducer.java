package tutorial.ch10.exercise14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class FileListProducer implements Runnable {

    private final BlockingQueue<Path> queue;
    private final Path directory;

    public FileListProducer(final BlockingQueue<Path> queue, final Path directory) {
        this.queue = queue;
        this.directory = directory;
    }

    @Override
    public void run() {
        addFilesToQueue(directory);
    }

    private void addFilesToQueue(final Path directory) {
        try {
            Files.list(directory).forEach(path -> {
                if (path.toFile().isDirectory()) {
                    addFilesToQueue(path);
                } else {
                    putIntoTheQueue(path);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void putIntoTheQueue(final Path path) {
        try {
            queue.put(path);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
