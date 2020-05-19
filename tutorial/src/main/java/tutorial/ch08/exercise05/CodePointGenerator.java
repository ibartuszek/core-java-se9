package tutorial.ch08.exercise05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 05
 * The codePoints method in Section 8.3, “The filter, map, and
 * flatMap Methods” (page 263) was a bit clumsy, first filling an array list and
 * then turning it into a stream. Write a stream-based version instead, using the
 * IntStream.iterate method to construct a finite stream of offsets, then
 * extract the substrings.
 * <p>
 * public static Stream<String> codePoints(String s) {
 * List<String> result = new ArrayList<>();
 * int i = 0;
 * while (i < s.length()) {
 * int j = s.offsetByCodePoints(i, 1);
 * result.add(s.substring(i, j));
 * i = j;
 * } return result.stream();
 * }
 */
public class CodePointGenerator {

    public static Stream<String> codePoints(final String source) {
        return source.codePoints()
            .mapToObj(Objects::toString);
    }

    public static void main(String[] args) {
        String source = "Being an irrational number, π cannot be expressed as a common fraction,"
            + " and equivalently its decimal representation never ends and never settles into a"
            + " permanently repeating pattern. Still, fractions such as 22/7 and other rational"
            + " numbers are commonly used to approximate π. The digits appear to be randomly"
            + " distributed. In particular, the digit sequence of π is conjectured to satisfy a"
            + " specific kind of statistical randomness, but to date, no proof of this has been"
            + " discovered. Also, π is a transcendental number; that is, it is not the root of"
            + " any polynomial having rational coefficients. This transcendence of π implies that"
            + " it is impossible to solve the ancient challenge of squaring the circle with a"
            + " compass and straightedge.";
        System.out.println(codePoints(source).collect(Collectors.toList()));
    }

}
