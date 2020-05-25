package tutorial.ch10.exercise15;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tutorial.ch10.exercise12.DictionarySorter;
import tutorial.ch10.exercise14.FileListProducer;

/**
 * 15
 * Repeat the preceding exercise, using a global ConcurrentHashMap for collecting the word frequencies.
 */
public class WordFrequencyProvider {

    private final BlockingQueue<Path> fileQueue;
    private final ConcurrentHashMap<String, Long> worDictionary;
    private final ExecutorService executorService;
    private final ExecutorCompletionService<Void> completionService;

    public WordFrequencyProvider() {
        this.fileQueue = new ArrayBlockingQueue<>(1024, true);
        this.worDictionary = new ConcurrentHashMap<>();
        this.executorService = Executors.newCachedThreadPool();
        this.completionService = new ExecutorCompletionService<>(executorService);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public Map<String, Long> provideTopTen(final Path directory) throws InterruptedException, ExecutionException {
        Future<Void> fileProducerFuture = completionService.submit(() -> new FileListProducer(fileQueue, directory).run(), createVoid());
        List<Future<Void>> futureList = submitTasks(fileProducerFuture);
        waitForMerging(futureList);
        return new DictionarySorter().getTopTen(worDictionary);
    }
    private Void createVoid() {
        return null;
    }

    private List<Future<Void>> submitTasks(final Future<Void> fileProducerFuture) throws InterruptedException {
        List<Future<Void>> futureList = new ArrayList<>();
        while (!fileProducerFuture.isDone() || !fileQueue.isEmpty()) {
            Path path = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
            if (path != null) {
                futureList.add(completionService.submit(() -> new FileWordCounterCallable(path, worDictionary).call()));
            }
        }
        return futureList;
    }

    private void waitForMerging(final List<Future<Void>> futureList) {
        futureList.forEach(voidFuture -> {
            try {
                voidFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        WordFrequencyProvider provider = new WordFrequencyProvider();
        System.out.println(provider.provideTopTen(Path.of("src/main/java/tutorial")));
        provider.shutdown();
    }

}
