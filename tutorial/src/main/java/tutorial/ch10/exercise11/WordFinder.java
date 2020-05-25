package tutorial.ch10.exercise11;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 11
 * Use a blocking queue for processing files in a directory. One thread walks the
 * file tree and inserts files into a queue. Several threads remove the files and
 * search each one for a given keyword, printing out any matches. When the
 * producer is done, it should put a dummy file into the queue.
 */
public class WordFinder {

    public List<Path> find(final String keyWord, final Path directory) throws InterruptedException {
        BlockingQueue<Map.Entry<Path, Boolean>> queue = new ArrayBlockingQueue<>(1024, true);
        ExecutorService executorService = Executors.newCachedThreadPool();
        submitFileProducer(directory, queue, executorService);
        CopyOnWriteArrayList<Path> resultList = process(executorService, keyWord, queue);
        executorService.shutdown();
        return resultList;
    }

    private void submitFileProducer(final Path directory, final BlockingQueue<Map.Entry<Path, Boolean>> queue, final ExecutorService executorService) {
        FileListProducer producer = new FileListProducer(queue, directory);
        executorService.submit(producer);
    }

    private CopyOnWriteArrayList<Path> process(final ExecutorService executorService, final String keyWord,
        final BlockingQueue<Map.Entry<Path, Boolean>> queue) throws InterruptedException {
        CopyOnWriteArrayList<Path> resultList = new CopyOnWriteArrayList<>();

        submitTasks(executorService, keyWord, queue, resultList);
        return resultList;
    }

    private List<Future<Void>> submitTasks(final ExecutorService executorService, final String keyWord, final BlockingQueue<Map.Entry<Path, Boolean>> queue,
        final CopyOnWriteArrayList<Path> resultList) throws InterruptedException {
        List<Future<Void>> futureList = new ArrayList<>();
        Map.Entry<Path, Boolean> entry = queue.poll(10L, TimeUnit.MILLISECONDS);
        while (entry == null || !entry.getValue()) {
            if (entry != null) {
                futureList.add(executorService.submit(new FileFilterConsumer(keyWord, resultList, entry.getKey())));
            } else {
                System.out.println("Producer is waiting...");
            }
            entry = queue.poll(10L, TimeUnit.MILLISECONDS);
        }
        return futureList;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(new WordFinder().find("the", Path.of("src/main/java/tutorial")).size());
        // System.out.println(new WordFinder().find("the", Path.of("c:/projects")).size());
    }

}
