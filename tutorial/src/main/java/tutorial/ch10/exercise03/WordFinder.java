package tutorial.ch10.exercise03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import tutorial.ch10.WordFinderUtility;

/**
 * 03
 * Implement a method yielding a task that reads through all words in a file,
 * trying to find a given word. The task should finish immediately (with a debug
 * message) when it is interrupted. For all files in a directory, schedule one task
 * for each file. Interrupt all others when one of them has succeeded.
 */
public class WordFinder {

    private WordFinderUtility wordFinderUtility = new WordFinderUtility();

    public Path findFirstWithInterruption(final String word, final Path directory) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Map.Entry<Path, Boolean>>> taskList = createTaskList(wordFinderUtility.initFileList(directory), word);
        return getResult(executorService.invokeAll(taskList), executorService);
    }

    private List<Callable<Map.Entry<Path, Boolean>>> createTaskList(final List<Path> fileList, final String word) {
        return fileList.stream()
            .map(path -> new WordFinderCallable(path, word))
            .collect(Collectors.toList());
    }

    private Path getResult(final List<Future<Map.Entry<Path, Boolean>>> futures, final ExecutorService executorService)
        throws ExecutionException, InterruptedException {
        System.out.println("Start searching for result");
        Future<Path> result = executorService.submit(() -> {
            Path target = null;
            for (int index = 0; index < futures.size() && target == null; index++) {
                Future<Map.Entry<Path, Boolean>> future = futures.get(index);
                if (future.isDone() && future.get().getValue().equals(true)) {
                    System.out.println("Found result");
                    target = future.get().getKey();
                }
            }
            futures.forEach(f -> f.cancel(true));
            return target;
        });

        executorService.shutdown();
        return result.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // System.out.println(new WordFinder().findFirstWithInterruption("required", Path.of("src/main/java/tutorial")));
        System.out.println(new WordFinder().findFirstWithInterruption("required", Path.of("c:")));
    }

}

class WordFinderCallable implements Callable<Map.Entry<Path, Boolean>> {

    private final Path file;
    private final String word;

    public WordFinderCallable(final Path file, final String word) {
        this.file = file;
        this.word = word;
    }

    @Override
    public Map.Entry<Path, Boolean> call() {
        Map.Entry<Path, Boolean> result = null;
        try {
            if (contains(word, file)) {
                result = Map.entry(file, true);
                System.out.println("True");
            } else {
                result = Map.entry(file, false);
                System.out.println("False");
            }
        } catch (Exception e) {
            System.out.println("Interrupted");
        }
        return result;
    }

    private boolean contains(final String word, final Path path) {
        boolean result = false;
        try {
            result = Files.lines(path)
                .anyMatch(line -> line.contains(word));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
