package tutorial.ch10.exercise12;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorial.ch10.exercise11.FileFilterConsumer;
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

    public void shutdown() {
        executorService.shutdown();
    }

    public Map<String, Long> provideTopTen(final Path directory) throws InterruptedException, ExecutionException {
        executorService.submit(() -> new FileListProducer(fileQueue, directory).run());
        List<Future<Void>> futureList = submitConsumerTasks();
        Map<String, Long> resultMap = submitMergerTask(futureList);
        return new DictionarySorter().getTopTen(resultMap);
    }

    private List<Future<Void>> submitConsumerTasks() throws InterruptedException {
        List<Future<Void>> futureList = new ArrayList<>();
        Map.Entry<Path, Boolean> entry = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
        while (entry == null || !entry.getValue()) {
            if (entry != null) {
                futureList.add(executorService.submit(new FileWordCounterCallable(wordDictionary, entry.getKey())));
            } else {
                System.out.println("Producer is waiting...");
            }
            entry = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
        }
        return futureList;
    }

    private Map<String, Long> submitMergerTask(final List<Future<Void>> futureList) throws ExecutionException, InterruptedException {
        Future<Map<String, Long>> futureMap = executorService.submit(() -> {
            Map<String, Long> resultMap = new HashMap<>();
            while(fileConsumersAreStillWorking(futureList) || !wordDictionary.isEmpty()) {
                Map<String, Long> map = wordDictionary.poll();
                if (map != null) {
                    map.forEach((key, value) -> resultMap.merge(key, value, Long::sum));
                }
            }
            return resultMap;
        });
        return futureMap.get();
    }

    private boolean fileConsumersAreStillWorking(final List<Future<Void>> futureList) {
        return futureList.stream()
            .anyMatch(future -> !future.isDone());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        WordFrequencyProvider provider = new WordFrequencyProvider();
        System.out.println(provider.provideTopTen(Path.of("src/main/java/tutorial")));
        provider.shutdown();
    }

}
