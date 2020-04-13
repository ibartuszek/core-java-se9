package tutorial.ch02.exercise01;

import static java.lang.System.out;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Change the calendar printing program so it starts the week on a Sunday. Also
 * make it print a newline at the end (but only one).
 */
public class Calendar {

    public static void main(String[] args) {
        LocalDate now = now();
        LocalDate date = of(now.getYear(), now.getMonth(), 1);
        List<DayOfWeek> dayOfWeekList = createDayOfWeekList();
        printWeekdayLabels(dayOfWeekList);
        printEmptyDays(findFirstDay(dayOfWeekList, date));
        printNormalDays(date, getLastDayOfTheWeek(dayOfWeekList));
    }

    private static List<DayOfWeek> createDayOfWeekList() {
        ArrayList<DayOfWeek> dayOfWeekArrayList = new ArrayList<>();
        dayOfWeekArrayList.add(DayOfWeek.SUNDAY);
        dayOfWeekArrayList.addAll(Arrays.asList(DayOfWeek.values()));
        dayOfWeekArrayList.remove(dayOfWeekArrayList.size() - 1);
        return dayOfWeekArrayList;
    }

    private static void printWeekdayLabels(final List<DayOfWeek> dayOfWeekList) {
        for (DayOfWeek day : dayOfWeekList) {
            out.printf("%4s", day.getDisplayName(TextStyle.SHORT, Locale.US));
        }
        out.println();
    }

    private static int findFirstDay(final List<DayOfWeek> dayOfWeekList, final LocalDate date) {
        int index = 0;
        while (!dayOfWeekList.get(index).equals(date.getDayOfWeek())) {
            index++;
        }
        return index;
    }

    private static void printEmptyDays(final int firstIndex) {
        for (int index = 0; index < firstIndex; index++) {
            out.printf("%4s", "");
        }
    }

    private static void printNormalDays(final LocalDate date, final DayOfWeek lastDayOfTheWeek) {
        LocalDate nextDate = date;
        while (nextDate.getMonthValue() == date.getMonth().getValue()) {
            out.printf("%4s", nextDate.getDayOfMonth());
            if (nextDate.getDayOfWeek().equals(lastDayOfTheWeek)) {
                out.println();
            }
            nextDate = nextDate.plusDays(1);
        }
    }

    private static DayOfWeek getLastDayOfTheWeek(final List<DayOfWeek> dayOfWeekList) {
        return dayOfWeekList.get(dayOfWeekList.size() - 1);
    }
}
