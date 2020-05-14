package tutorial.ch10.exercise29;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 29
 * The method static CompletableFuture<Object> CompletableFuture.anyOf(CompletableFuture<?>... cfs)
 * returns as soon as any of the arguments completes, normally or exceptionally. This is markedly
 * different from ExecutorService.invokeAny which keeps going until one of the tasks completes
 * successfully and prevents the method from being used for a concurrent search. Implement a method
 * static CompletableFuture<T> anyOf(List<Supplier<T>> actions, Executor exec)
 * that yields the first actual result, or a NoSuchElementException if all actions completed with exceptions.
 */
public class CompletableFutureUtility {

    public static <T> CompletableFuture<T> anyOf(final List<Supplier<T>> actions, final Executor executor) throws ExecutionException, InterruptedException {
        CompletionService<T> completionService = new ExecutorCompletionService<>(executor);
        actions.forEach(action -> completionService.submit(action::get));
        return getCompletableFuture(completionService);
    }

    private static <T> CompletableFuture<T> getCompletableFuture(final CompletionService<T> completionService) {
        return CompletableFuture.supplyAsync(() -> {
            T result = null;
            try {
                result = completionService.poll().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Supplier<Integer>> actions = Stream.generate(CompletableFutureUtility::createSupplier)
            .limit(5)
            .collect(Collectors.toList());
        CompletableFuture<Integer> future = CompletableFutureUtility.anyOf(actions, executorService);
        System.out.println(future.get());
        executorService.shutdown();
    }

    public static Supplier<Integer> createSupplier() {
        int[] nextInt = { 0 };
        return  () -> nextInt[0]++;
    }

}
