package tutorial.ch10.exercise11;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractFileConsumer implements Callable<Void> {

    private static AtomicBoolean done = new AtomicBoolean(false);
    private final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue;

    public AbstractFileConsumer(final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue) {
        this.fileQueue = fileQueue;
    }

    @Override
    public Void call() {
        while (!done.get()) {
            try {
                Map.Entry<Path, Boolean> entry = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
                if (entry != null) {
                    if (entry.getValue()) {
                        done.getAndSet(true);
                    } else {
                        processFile(entry.getKey());
                    }
                } else {
                    System.out.println("Producer is waiting...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    abstract protected void processFile(final Path file);

}
