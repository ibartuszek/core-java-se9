package tutorial.ch10.exercise11;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 11
 * Use a blocking queue for processing files in a directory. One thread walks the
 * file tree and inserts files into a queue. Several threads remove the files and
 * search each one for a given keyword, printing out any matches. When the
 * producer is done, it should put a dummy file into the queue.
 */
public class WordFinder {

    public List<Path> find(final String keyWord, final Path directory) throws InterruptedException {
        BlockingQueue<Map.Entry<Path, Boolean>> queue = new ArrayBlockingQueue<>(1024, true);
        CopyOnWriteArrayList<Path> resultList = new CopyOnWriteArrayList<>();
        process(new FileListProducer(queue, directory), createConsumers(keyWord, queue, resultList));
        return resultList;
    }

    private List<FileFilterConsumer> createConsumers(final String keyWord, final BlockingQueue<Map.Entry<Path, Boolean>> queue,
        final CopyOnWriteArrayList<Path> resultList) {
        return Stream.generate(() -> new FileFilterConsumer(queue, keyWord, resultList))
                .limit(8)
                .collect(Collectors.toList());
    }

    private void process(final FileListProducer producer, final List<FileFilterConsumer> consumers) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(producer);
        List<Future<Void>> futures = executorService.invokeAll(consumers);
        wait(futures);
        executorService.shutdown();
    }

    private void wait(final List<Future<Void>> futures) throws InterruptedException {
        boolean done = false;
        while (!done) {
            done = futures.stream()
                .filter(future -> !future.isDone())
                .findAny()
                .isEmpty();
            Thread.sleep(10L);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(new WordFinder().find("the", Path.of("src/main/java/tutorial")).size());
    }

}
