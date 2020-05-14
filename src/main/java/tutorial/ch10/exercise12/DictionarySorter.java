package tutorial.ch10.exercise12;

import java.util.Map;
import java.util.stream.Collectors;

public class DictionarySorter {

    public Map<String, Long> getTopTen(final Map<String, Long> map) {
        return map.entrySet().stream()
            .sorted((first, second) -> second.getValue().compareTo(first.getValue()))
            .limit(10)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
