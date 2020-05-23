package tutorial.ch10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableServiceTest {

    private static String getTheFastestFutureResultAndStopTheOthers() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        List<Future<String>> futureList = submitTasks(completionService);
        Future<String> result = completionService.take();
        futureList.forEach(future -> future.cancel(true));
        executorService.shutdown();
        return result.get();
    }

    private static List<Future<String>> submitTasks(final CompletionService<String> completionService) throws InterruptedException {
        List<Future<String>> futureList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            Thread.sleep(500);
            futureList.add(completionService.submit(new ExampleCallable()));
        }
        return futureList;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Future value: " + getTheFastestFutureResultAndStopTheOthers());
    }

}
