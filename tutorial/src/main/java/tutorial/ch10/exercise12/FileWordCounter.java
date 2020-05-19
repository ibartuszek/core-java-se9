package tutorial.ch10.exercise12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileWordCounter {

    public Map<String, Long> count(final Path file) {
        Map<String, Long> resultMap;
        try {
            resultMap = Files.lines(file)
                .flatMap(line -> Arrays.stream(line.split("\\PL+")))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultMap;
    }

}
