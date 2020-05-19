package tutorial.ch10.exercise27;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private T customResult;
    private Throwable customThrowable;

    private CustomCompletableFuture() {
        super();
    }

    public static <T> CustomCompletableFuture<T> supplyAsync(final Supplier<T> action, final Executor executor) {

        CustomCompletableFuture<T> result = new CustomCompletableFuture<>();

        executor.execute(() -> {
            result.currentThread = Thread.currentThread();
            try {
                T temp = action.get();
                if (!result.currentThread.isInterrupted()) {
                    result.customResult = temp;
                }
            } catch (Exception e) {
                result.customThrowable = e;
            }
        });

        return result;
    }

    public boolean cancel() {
        boolean result = false;
        if (customResult == null && customThrowable == null) {
            result = true;
            currentThread.interrupt();
        }
        return result;
    }

    public T getCustomResult() throws Throwable {
        waitForExecution();
        if (customThrowable != null) {
            throw customThrowable;
        }
        return customResult;
    }

    public Throwable getCustomThrowable() {
        waitForExecution();
        return customThrowable;
    }

    private void waitForExecution() {
        while (customResult == null && customThrowable == null) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        ExecutorService executor = Executors.newCachedThreadPool();
        CustomCompletableFuture<String> result = CustomCompletableFuture.supplyAsync(CustomCompletableFuture::supplyWord, executor);
        Thread.sleep(500L);
        result.cancel();
        try {
            System.out.println(result.getCustomResult());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        result = CustomCompletableFuture.supplyAsync(CustomCompletableFuture::supplyWord, executor);
        System.out.println(result.getCustomResult());
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
