package tutorial.ch12.exercise10;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 10
 * Your flight from Los Angeles to Frankfurt leaves at 3:05 pm local time and
 * takes 10 hours and 50 minutes. When does it arrive? Write a program that can
 * handle calculations like this.
 * 11
 * Your return flight leaves Frankfurt at 14:05 and arrives in Los Angeles at
 * 16:40. How long is the flight? Write a program that can handle calculations
 * like this.
 */
public class ZonedDateTimeExamples {

    public static ZonedDateTime getArriveTime(final ZonedDateTime start, final ZoneId to, final long hours, final int minutes) {
        Instant instant = start.plusHours(hours).plusMinutes(minutes).toInstant();
        return ZonedDateTime.ofInstant(instant, to);
    }

     public static Duration getTravellingTime(final ZonedDateTime from, final ZonedDateTime to) {
        long epochSecond = to.toEpochSecond() - from.toEpochSecond();
        return Duration.ofSeconds(epochSecond);
     }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        ZonedDateTime leaveFromLosAngeles = ZonedDateTime.of(LocalDateTime.of(now, LocalTime.of(3, 5)), ZoneId.of("America/Los_Angeles"));
        System.out.println(getArriveTime(leaveFromLosAngeles, ZoneId.of("CET"), 10, 50));

        ZonedDateTime leaveFromFrankfurt = ZonedDateTime.of(LocalDateTime.of(now, LocalTime.of(14, 5)), ZoneId.of("CET"));
        ZonedDateTime arriveAtLosAngeles = ZonedDateTime.of(LocalDateTime.of(now, LocalTime.of(16, 40)), ZoneId.of("America/Los_Angeles"));
        Duration travellingTime = getTravellingTime(leaveFromFrankfurt, arriveAtLosAngeles);
        System.out.println(travellingTime.toHours() + ":" + travellingTime.toMinutesPart());
    }

}
