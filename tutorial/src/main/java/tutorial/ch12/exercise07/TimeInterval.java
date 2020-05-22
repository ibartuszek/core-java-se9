package tutorial.ch12.exercise07;

import java.time.LocalDate;

/**
 * 07
 * Implement a TimeInterval class that represents an interval of time,
 * suitable for calendar events (such as a meeting on a given date from 10:00 to
 * 11:00). Provide a method to check whether two intervals overlap.
 */
public class TimeInterval {

    private final LocalDate start;
    private final LocalDate end;

    public TimeInterval(final LocalDate start, final LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public boolean isOverlapping(final TimeInterval other) {
        // start    end
        // start          end
        boolean overlapping = hasCommonDate(other);

        if (!overlapping) {
            // start         end
            //        start      end
            overlapping = (this.start.isAfter(other.start) && this.start.isBefore(other.end))
                || (this.end.isAfter(other.start) && this.end.isBefore(other.end));
        }
        return overlapping;
    }

    private boolean hasCommonDate(final TimeInterval other) {
        return this.start.equals(other.start) || this.end.equals(other.end)
            || this.start.equals(other.end) || this.end.equals(other.end);
    }

    public static void main(String[] args) {
        LocalDate firstDate = LocalDate.of(2020, 02, 01);
        LocalDate secondDate = LocalDate.of(2020, 03, 01);
        LocalDate thirdDate = LocalDate.of(2020, 04, 01);
        LocalDate forthDate = LocalDate.of(2020, 05, 01);
        TimeInterval first = new TimeInterval(firstDate, thirdDate);
        TimeInterval second = new TimeInterval(secondDate, forthDate);
        System.out.println(first.isOverlapping(second));
    }

}
