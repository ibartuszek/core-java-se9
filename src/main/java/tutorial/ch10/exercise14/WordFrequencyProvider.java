package tutorial.ch10.exercise14;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tutorial.ch10.exercise12.DictionarySorter;

/**
 * 14
 * Use an ExecutorCompletionService instead and merge the results as soon as they become available.
 */
public class WordFrequencyProvider {

    private final BlockingQueue<Path> fileQueue;
    private final ExecutorService executorService;
    private final ExecutorCompletionService<Map<String, Long>> completionService;

    public WordFrequencyProvider() {
        this.fileQueue = new ArrayBlockingQueue<>(1024, true);
        this.executorService = Executors.newCachedThreadPool();
        this.completionService = new ExecutorCompletionService<>(executorService);
    }

    public Map<String, Long> provideTopTen(final Path directory) throws InterruptedException, ExecutionException {
        Future<Map<String, Long>> fileProducerFuture =
            completionService.submit(() -> new FileListProducer(fileQueue, directory).run(), Collections.emptyMap());
        submitTasks(fileProducerFuture);
        Map<String, Long> resultMap = getMergedMap();
        executorService.shutdown();
        return new DictionarySorter().getTopTen(resultMap);
    }

    private void submitTasks(final Future<Map<String, Long>> fileProducerFuture) throws InterruptedException {
        while (!fileProducerFuture.isDone() || !fileQueue.isEmpty()) {
            Path path = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
            if (path != null) {
                completionService.submit(() -> new FileWordCounterCallable(path).call());
            }
        }
    }

    private Map<String, Long> getMergedMap() {
        Map<String, Long> resultMap = new HashMap<>();
        try {
            for (Future<Map<String, Long>> future = completionService.poll(1000L, TimeUnit.MILLISECONDS); future != null;
                 future = completionService.poll(1000L, TimeUnit.MILLISECONDS)) {
                future.get().forEach((key, value) -> resultMap.merge(key, value, Long::sum));
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(new WordFrequencyProvider().provideTopTen(Path.of("src/main/java/tutorial")));
    }

}
