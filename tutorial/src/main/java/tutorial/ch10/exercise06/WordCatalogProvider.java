package tutorial.ch10.exercise06;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import tutorial.ch10.WordFinderUtility;

/**
 * 06
 * Write an application in which multiple threads read all words from a
 * collection of files. Use a ConcurrentHashMap<String,
 * Set<File>> to track in which files each word occurs. Use the merge
 * method to update the map.
 * 07
 * Repeat the preceding exercise, but use computeIfAbsent instead. What is
 * the advantage of this approach?
 */
public class WordCatalogProvider {

    private WordFinderUtility wordFinderUtility = new WordFinderUtility();

    public Map<String, Set<File>> provideWithMerge(final Path directory) throws InterruptedException {
        return provide(directory, WordCatalogCallableWithMerge::new);
    }

    public Map<String, Set<File>> provideWithPutIfAbsent(final Path directory) throws InterruptedException {
        return provide(directory, WordCatalogCallableWithPutIfAbsent::new);
    }

    private Map<String, Set<File>> provide(final Path directory,
        final BiFunction<Path, ConcurrentHashMap<String, ConcurrentHashMap<File, Boolean>>, ? extends Callable<Void>> function)
        throws InterruptedException {
        ConcurrentHashMap<String, ConcurrentHashMap<File, Boolean>> catalog = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<AbstractWordCatalogCallable> taskList = wordFinderUtility.initFileList(directory).stream()
            .map(path -> (AbstractWordCatalogCallable) function.apply(path, catalog))
            .collect(Collectors.toList());
        waitForFinishing(executorService.invokeAll(taskList));
        executorService.shutdown();
        return transFormCatalog(catalog);
    }

    private void waitForFinishing(final List<Future<Void>> futures) throws InterruptedException {
        boolean done = false;
        while (!done) {
            done = futures.stream()
                .filter(future -> !future.isDone())
                .findAny()
                .isEmpty();
            Thread.sleep(10L);
        }
    }

    private Map<String, Set<File>> transFormCatalog(final Map<String, ConcurrentHashMap<File, Boolean>> catalog) {
        Map<String, Set<File>> result = new HashMap<>();
        catalog.forEach((key, value) -> result.put(key, value.keySet()));
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        Path directory = Path.of("src/main/java/tutorial");
        Map<String, Set<File>> resultWithMerge = new WordCatalogProvider().provideWithMerge(directory);
        System.out.println(resultWithMerge);
        System.out.println(resultWithMerge.size());
        Map<String, Set<File>> resultWithPutIfAbsent = new WordCatalogProvider().provideWithPutIfAbsent(directory);
        System.out.println(resultWithPutIfAbsent);
        System.out.println(resultWithPutIfAbsent.size());
    }

}
