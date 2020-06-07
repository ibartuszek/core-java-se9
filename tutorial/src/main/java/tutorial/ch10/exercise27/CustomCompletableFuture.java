package tutorial.ch10.exercise27;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * 27
 * Implement a static method CompletableFuture<T> <T> supplyAsync(Supplier<T> action, Executor exec) that
 * returns an instance of a subclass of CompletableFuture<T> whose cancel method can interrupt the thread
 * that executes the action method, provided the task is running. In a Runnable, capture the current thread,
 * then call action.get(), and complete the CompletableFuture with the result or exception.
 */
public class CustomCompletableFuture<T> extends CompletableFuture<T> {

    private Thread currentThread;
    private Future<T> customResult;
    private Throwable customThrowable;

    private CustomCompletableFuture() {
        super();
    }

    public static <T> CustomCompletableFuture<T> supplyAsync(final Supplier<T> action, final Executor executor) {
        CompletionService<T> completionService = new ExecutorCompletionService<>(executor);
        CustomCompletableFuture<T> result = new CustomCompletableFuture<>();
        result.customResult = completionService.submit(() -> {
            result.currentThread = Thread.currentThread();
            return action.get();
        });
        return result;
    }

    public boolean cancel() {
        currentThread.interrupt();
        return true;
    }

    public T getResult() {
        T result = null;
        try {
            result = customResult.get();
        } catch (Throwable t) {
            customThrowable = t;
        }
        return result;
    }

    public Throwable getCustomThrowable() {
        return customThrowable;
    }

    public static void main(String[] args) throws Throwable {
        ExecutorService executor = Executors.newCachedThreadPool();
        CustomCompletableFuture<String> result = CustomCompletableFuture.supplyAsync(CustomCompletableFuture::supplyWord, executor);
        Thread.sleep(500L);
        result.cancel();
        System.out.println("Result: (if exception happened) " + result.getResult());
        result = CustomCompletableFuture.supplyAsync(CustomCompletableFuture::supplyWord, executor);
        System.out.println("Result: " + result.getResult());
        executor.shutdown();

    }

    private static String supplyWord() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Example";
    }

}
