package tutorial.ch10.exercise08;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tutorial.ch10.exercise06.WordCatalogProvider;

/**
 * 08
 * In a ConcurrentHashMap<String, Long>, find the key with
 * maximum value (breaking ties arbitrarily). Hint: reduceEntries.
 */
public class FrequentWordProvider {

    private WordCatalogProvider wordCatalogProvider = new WordCatalogProvider();

    public Map.Entry<String, Long> provide(final Path directory) throws InterruptedException {
        return transform(wordCatalogProvider.provideWithMerge(directory))
            .reduceEntries(Long.MAX_VALUE, (first, second) -> first.getValue().compareTo(second.getValue()) >= 0 ? first : second);
    }

    private ConcurrentHashMap<String, Long> transform(final Map<String, Set<File>> map) {
        ConcurrentHashMap<String, Long> result = new ConcurrentHashMap<>();
        map.forEach((key, value) -> result.put(key, (long) value.size()));
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        Path directory = Path.of("src/main/java/tutorial");
        System.out.println(new FrequentWordProvider().provide(directory));
    }

}
