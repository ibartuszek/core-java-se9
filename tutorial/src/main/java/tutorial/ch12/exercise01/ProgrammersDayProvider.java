package tutorial.ch12.exercise01;

import java.time.LocalDate;

/**
 * 01
 * Compute Programmer’s Day without using plusDays.
 */
public class ProgrammersDayProvider {

    private static final int DAY_OF_THE_YEAR = 256;

    public LocalDate provide() {
        LocalDate now = LocalDate.now();
        return LocalDate.ofYearDay(now.getYear(), DAY_OF_THE_YEAR);
    }

    public static void main(String[] args) {
        System.out.println(new ProgrammersDayProvider().provide());
    }

}
