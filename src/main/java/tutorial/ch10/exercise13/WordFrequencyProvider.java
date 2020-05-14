package tutorial.ch10.exercise13;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorial.ch10.exercise11.FileListProducer;
import tutorial.ch10.exercise12.DictionarySorter;
import tutorial.ch10.exercise12.FileWordCounterCallable;
import tutorial.ch10.exercise12.WordDictionaryMerger;

/**
 * 13
 * Repeat the preceding exercise, making a Callable<Map<String,Integer>> for each file
 * and using an appropriate executor service. Merge the results when all are available.
 * Why don't you need to use a ConcurrentHashMap?
 */
public class WordFrequencyProvider {

    private final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue;
    private final BlockingQueue<Map<String, Long>> wordDictionary;
    private final ExecutorService executorService;

    public WordFrequencyProvider() {
        this.fileQueue = new ArrayBlockingQueue<>(1024, true);
        this.wordDictionary = new ArrayBlockingQueue<>(1024, true);
        this.executorService = Executors.newCachedThreadPool();
    }

    public Map<String, Long> provideTopTen(final Path directory) throws InterruptedException, ExecutionException {
        executorService.submit(() -> new FileListProducer(fileQueue, directory).run());
        List<Future<Void>> futures = executorService.invokeAll(createConsumers(fileQueue, wordDictionary));
        waitForFutures(futures);
        executorService.shutdown();
        return new DictionarySorter().getTopTen(new WordDictionaryMerger(wordDictionary).merge());
    }

    public List<Callable<Void>> createConsumers(final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue,
        final BlockingQueue<Map<String, Long>> wordDictionary) {
        return Stream.generate(() -> new FileWordCounterCallable(fileQueue, wordDictionary))
            .limit(10)
            .collect(Collectors.toList());
    }

    private void waitForFutures(final List<Future<Void>> futures) throws InterruptedException {
        boolean done = false;
        while (!done) {
            done = futures.stream()
                .filter(future -> !future.isDone())
                .findAny()
                .isEmpty();
            Thread.sleep(100L);
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(new WordFrequencyProvider().provideTopTen(Path.of("src/main/java/tutorial")));
    }

}
