package tutorial.ch12.exercise03;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.function.Predicate;

/**
 * 03
 * Implement a method next that takes a Predicate<LocalDate>
 * and returns an adjuster yielding the next date fulfilling the predicate.
 * For example, today.with(next(w -> getDayOfWeek().getValue() < 6))
 * computes the next workday.
 */
public class CustomDateAdjuster implements TemporalAdjuster {

    private final Predicate<LocalDate> condition;

    public CustomDateAdjuster(final Predicate<LocalDate> condition) {
        this.condition = condition;
    }

    @Override
    public Temporal adjustInto(final Temporal temporal) {
        LocalDate result = (LocalDate) temporal;
        do {
            result = result.plusDays(1);
        } while (condition.test(result));
        return result;
    }

    public static CustomDateAdjuster next(final Predicate<LocalDate> condition) {
        return new CustomDateAdjuster(condition);
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println(today.with(next(w -> w.getDayOfWeek().getValue() < 6)));
    }

}
