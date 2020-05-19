package tutorial.ch08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) throws IOException {
        String path = "src/main/java/tutorial/ch07/exercise10/cities";
        String contents = Files.readString(Paths.get(path));
        Stream<String> words = Stream.of(contents.split("\\PL+"))
            .skip(1); // skip the first element

        // File can be read to Stream<String> directly
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            List<String> list = lines.map(line -> Arrays.asList(line.split(",")))
                .flatMap(Collection::stream)
                .map(String::trim)
                .collect(Collectors.toList());
        }

        // Generate infinite sequence, then get the first 100 elements
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
//        Map<String, String> languageNames = locales.collect(
//            Collectors.toMap(
//                Locale::getDisplayLanguage, // Key
//                loc -> loc.getDisplayLanguage(loc), // Value
//                (existingValue, newValue) -> existingValue)); // Override in case of conflict

        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(
            Collectors.groupingBy(Locale::getCountry, Collectors.toSet()));

        System.out.println(countryToLocaleSet);

        int[] numbers = {1, 2, 4, 8, 16};
        Arrays.stream(numbers);

    }

}
