package tutorial.ch10.exercise13;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tutorial.ch10.exercise11.FileListProducer;
import tutorial.ch10.exercise12.DictionarySorter;

/**
 * 13
 * Repeat the preceding exercise, making a Callable<Map<String,Integer>> for each file
 * and using an appropriate executor service. Merge the results when all are available.
 * Why don't you need to use a ConcurrentHashMap?
 */
public class WordFrequencyProvider {

    private final BlockingQueue<Map.Entry<Path, Boolean>> fileQueue;
    private final ExecutorService executorService;

    public WordFrequencyProvider() {
        this.fileQueue = new ArrayBlockingQueue<>(1024, true);
        this.executorService = Executors.newCachedThreadPool();
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public Map<String, Long> provideTopTen(final Path directory) throws InterruptedException, ExecutionException {
        executorService.submit(() -> new FileListProducer(fileQueue, directory).run());
        List<Future<Map<String, Long>>> futureList = submitWordCounterTasks();
        Map<String, Long> resultMap = createResultMap(futureList);
        return new DictionarySorter().getTopTen(resultMap);
    }

    private List<Future<Map<String, Long>>> submitWordCounterTasks() throws InterruptedException {
        List<Future<Map<String, Long>>> futureList = new ArrayList<>();
        Map.Entry<Path, Boolean> entry = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
        while (entry == null || !entry.getValue()) {
            if (entry != null) {
                futureList.add(executorService.submit(new FileWordCounterCallable(entry.getKey())));
            } else {
                System.out.println("Producer is waiting...");
            }
            entry = fileQueue.poll(10L, TimeUnit.MILLISECONDS);
        }
        return futureList;
    }

    private Map<String, Long> createResultMap(final List<Future<Map<String, Long>>> futureList) {
        Map<String, Long> resultMap = new HashMap<>();
        futureList.forEach(future -> {
            try {
                Map<String, Long> map = future.get();
                map.forEach((key, value) -> resultMap.merge(key, value, Long::sum));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        return resultMap;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        WordFrequencyProvider provider = new WordFrequencyProvider();
        System.out.println(provider.provideTopTen(Path.of("src/main/java/tutorial")));
        provider.shutdown();
    }

}
