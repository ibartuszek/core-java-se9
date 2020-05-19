package tutorial.utils;

import java.util.Scanner;

public class IntegerReader {

    private Scanner scanner;

    public IntegerReader() {
        this(new Scanner(System.in));
    }

    public IntegerReader(final Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInteger() {
        int number = 0;
        NumberFormatException exception;
        do {
            exception = null;
            System.out.println("Please write a number:");
            try {
                number = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                exception = e;
            }
        } while (exception != null);
        return number;
    }

}
