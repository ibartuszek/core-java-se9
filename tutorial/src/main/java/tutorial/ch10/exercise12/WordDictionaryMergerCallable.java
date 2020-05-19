package tutorial.ch10.exercise12;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class WordDictionaryMergerCallable implements Callable<Map<String, Long>> {

    private boolean done = false;
    private final WordDictionaryMerger merger;

    public WordDictionaryMergerCallable(final BlockingQueue<Map<String, Long>> wordDictionary) {
        this.merger = new WordDictionaryMerger(wordDictionary);
    }

    public void finish() {
        done = true;
    }

    @Override
    public Map<String, Long> call() throws Exception {
        Map<String, Long> resultMap = null;
        while (!done) {
            resultMap = merger.merge();
            Thread.sleep(100L);
        }
        return resultMap;
    }

}
