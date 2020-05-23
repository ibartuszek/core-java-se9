package tutorial.ch10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTest {

    private static String getTheFastestFutureResultAndStopTheOthers() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futureList = submitTasks(executorService);
        Future<String> firstReadyElement = getFirstReadyElement(futureList);
        futureList.forEach(future -> future.cancel(true));
        executorService.shutdown();
        return firstReadyElement.get();
    }

    private static List<Future<String>> submitTasks(final ExecutorService executorService) {
        List<Future<String>> futureList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            futureList.add(executorService.submit(new ExampleCallable()));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return futureList;
    }

    private static Future<String> getFirstReadyElement(final List<Future<String>> futureList) {
        Future<String> firstReadyElement = null;
        while (firstReadyElement == null) {
            firstReadyElement = futureList.stream()
                .filter(Future::isDone)
                .findAny()
                .orElse(null);
        }
        return firstReadyElement;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Future value: " + getTheFastestFutureResultAndStopTheOthers());
    }

}
