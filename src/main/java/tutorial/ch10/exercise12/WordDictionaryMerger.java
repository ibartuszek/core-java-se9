package tutorial.ch10.exercise12;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class WordDictionaryMerger {

    private final BlockingQueue<Map<String, Long>> wordDictionary;

    public WordDictionaryMerger(final BlockingQueue<Map<String, Long>> wordDictionary) {
        this.wordDictionary = wordDictionary;
    }

    public Map<String, Long> merge() {
        Map<String, Long> resultMap = new HashMap<>();
        while (!wordDictionary.isEmpty()) {
            Map<String, Long> map = wordDictionary.poll();
            map.forEach((key, value) -> resultMap.merge(key, value, Long::sum));
        }
        return resultMap;
    }
}
