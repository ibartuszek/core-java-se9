package tutorial.ch08.exercise06;

/**
 * 06
 * Use the String.codePoints method to implement a method that tests
 * whether a string is a word, consisting only of letters. (Hint:
 * Character.isAlphabetic.) Using the same approach, implement a
 * method that tests whether a string is a valid Java identifier.
 */
public class WordTester {

    public boolean test(final String sample) {
        return sample.chars()
            .filter(character -> !Character.isAlphabetic(character))
            .peek(s -> System.out.println((char) s))
            .findAny()
            .isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(new WordTester().test("ThisIsAGoodWord"));
        System.out.println(new WordTester().test("ThisIsABad1Word"));
    }

}
