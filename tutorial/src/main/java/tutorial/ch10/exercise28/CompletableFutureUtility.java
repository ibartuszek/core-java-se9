package tutorial.ch10.exercise28;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 28
 * The method static CompletableFuture<Void> CompletableFuture.allOf(CompletableFuture<?>... cfs)
 * does not yield the results of the arguments, which makes it a bit cumbersome to use. Implement
 * a method that combines completable futures of the same type:
 * static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> cfs)
 * Note that this method has a List parameter since you cannot have variable arguments of a generic type.
 */
public class CompletableFutureUtility {

    public static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> completableFutureList) {
        return CompletableFuture.completedFuture(collectResults(completableFutureList));
    }

    private static <T> List<T> collectResults(final List<CompletableFuture<T>> completableFutureList) {
        return completableFutureList.stream()
            .flatMap(CompletableFutureUtility::apply)
            .collect(Collectors.toList());
    }

    private static <T> Stream<T> apply(CompletableFuture<T> completableFuture) {
        Optional<T> result = Optional.empty();
        try {
            result = Optional.ofNullable(completableFuture.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result.stream();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> collect = Stream.generate(() -> CompletableFuture.supplyAsync(() -> "Hello"))
            .limit(5)
            .collect(Collectors.toList());
        CompletableFuture<List<String>> listCompletableFuture = CompletableFutureUtility.allOf(collect);
        System.out.println(listCompletableFuture.get());
    }

}
