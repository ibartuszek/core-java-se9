package tutorial.ch12.exercise04;

import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * 04
 * Write an equivalent of the Unix cal program that displays a calendar for a month.
 * Example:
 * May 2020
 * Su Mo Tu We Th Fr Sa
 *                 1  2
 *  3  4  5  6  7  8  9
 * 10 11 12 13 14 15 16
 * 17 18 19 20 21 22 23
 * 24 25 26 27 28 29 30
 * 31
 */
public class CalendarConsolePrinter {

    public static void print(final PrintStream out, final LocalDate input) {
        for (DayOfWeek w : DayOfWeek.values()) {
            out.printf("%4s", w.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        out.println();
        LocalDate date = LocalDate.of(input.getYear(), input.getMonth(), 1);
        for (int index = 1; input.getMonth().equals(date.getMonth()); index++) {
            if (index != date.getDayOfWeek().getValue()) {
                out.printf("%4s", " ");
            } else {
                out.printf("%4s", date.getDayOfMonth());
                if (date.getDayOfWeek().getValue() == 7) {
                    out.println();
                    index = 0;
                }
                date = date.plusDays(1);
            }
        }
    }

    public static void main(String[] args) {
        print(System.out, LocalDate.now());
    }

}
