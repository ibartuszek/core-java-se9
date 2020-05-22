package tutorial.ch12.exercise06;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 06
 * List all Friday the 13th in the twentieth century.
 */
public class Friday13ListProvider {

    private List<LocalDate> provide() {
        LocalDate firstDate = LocalDate.of(1900, 1, 13);
        return Stream.iterate(firstDate, date -> date.isBefore(LocalDate.now()), date -> date.plusMonths(1L))
            .filter(date -> date.getDayOfWeek().equals(DayOfWeek.FRIDAY))
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        new Friday13ListProvider().provide().forEach(System.out::println);
    }

}
