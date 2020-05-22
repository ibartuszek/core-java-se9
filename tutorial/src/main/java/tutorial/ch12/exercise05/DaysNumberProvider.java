package tutorial.ch12.exercise05;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 05
 * Write a program that prints how many days you have been alive.
 */
public class DaysNumberProvider {

    public static long provide(final LocalDate from) {
        LocalDate now = LocalDate.now();
        return ChronoUnit.DAYS.between(from, now);
    }

    public static void main(String[] args) {
        System.out.println(provide(LocalDate.of(2000, 1, 1)));
    }

}
