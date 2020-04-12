package tutorial.ch01.exercises;

/**
 * Write a program that reads a line of text and prints all characters that are not
 * ASCII, together with their Unicode values
 */
public class SpecialCharacterPrinterExercise {

    public static void main(String[] args) {
        String test = "Árvíztürőtükörfúrógép";
        for (char c : test.toCharArray()) {
            int code = c;
            if (code > 127) {
                System.out.printf("'%s' is not ASCII character, its Unicode value: '%s'\n", c, code);
            }
        }
    }
}
