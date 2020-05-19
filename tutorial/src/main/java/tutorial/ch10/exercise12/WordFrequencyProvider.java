package tutorial.ch10.exercise12;

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

/**
 * 12
 * Repeat the preceding exercise, but instead have each consumer compile a
 * map of words and their frequencies that are inserted into a second queue. A
 * final thread merges the dictionaries and prints the ten most common words.
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
        WordDictionaryMergerCallable mergerCallable = new WordDictionaryMergerCallable(wordDictionary);
        executorService.submit(() -> new FileListProducer(fileQueue, directory).run());
        List<Future<Void>> futures = executorService.invokeAll(createConsumers(fileQueue, wordDictionary));
        Future<Map<String, Long>> resultMapFuture = executorService.submit(mergerCallable);
        stopMerger(futures, mergerCallable);
        Map<String, Long> resultMap = new DictionarySorter().getTopTen(resultMapFuture.get());
        executorService.shutdown();
        return resultMap;
    }

    public List<Callable<Void>> createConsumers(final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue,
        final BlockingQueue<Map<String, Long>> wordDictionary) {
        return Stream.generate(() -> new FileWordCounterCallable(fileQueue, wordDictionary))
            .limit(10)
            .collect(Collectors.toList());
    }

    private void stopMerger(final List<Future<Void>> futures, final WordDictionaryMergerCallable mergerCallable) {
        boolean fileConsumersAreDone = false;
        while (!fileConsumersAreDone) {
            fileConsumersAreDone = futures.stream()
                .filter(future -> !future.isDone())
                .findAny()
                .isEmpty();
        }
        mergerCallable.finish();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(new WordFrequencyProvider().provideTopTen(Path.of("src/main/java/tutorial")));
    }

}
