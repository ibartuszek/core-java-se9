package tutorial.ch10.exercise17;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import tutorial.ch10.WordFinderUtility;

abstract public class AbstractWordCounterWithSharedCounter {

    public abstract long provide(final Path directory) throws InterruptedException;

    protected void invoke(final Path directory, final Function<Path, Callable<Void>> function) throws InterruptedException {
        List<Path> fileList = new WordFinderUtility().initFileList(directory);
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Void>> callableList = fileList.stream()
            .map(function)
            .collect(Collectors.toList());
        List<Future<Void>> futures = executorService.invokeAll(callableList);
        wait(futures);
        executorService.shutdown();
    }

    private void wait(final List<Future<Void>> futures) {
        boolean done = false;
        while (!done) {
            done = futures.stream()
                .filter(future -> !future.isDone())
                .findAny()
                .isEmpty();
        }
    }

}
