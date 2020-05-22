package tutorial.ch12.exercise12;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 12
 * Write a program that solves the problem described at the beginning of Section
 * 12.5, “Zoned Time” (page 410). Read a set of appointments in different time
 * zones and alert the user which ones are due within the next hour in local time.
 */
public class MeetingReminder {

    public static boolean hasMeeting(final ZonedDateTime meeting) {
        return hasMeeting(LocalDateTime.now(), meeting);
    }

    private static boolean hasMeeting(final LocalDateTime now, final ZonedDateTime meeting) {
        Instant nowAsInstant = now.toInstant(ZoneId.systemDefault().getRules().getOffset(now));
        Instant meetingAsInstant = meeting.minusHours(1L).toInstant();
        return nowAsInstant.isAfter(meetingAsInstant);
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        ZonedDateTime meetingInFrankfurt = ZonedDateTime.of(LocalDateTime.of(now, LocalTime.of(14, 5)), ZoneId.of("CET"));
        ZonedDateTime meetingInLosAngeles = ZonedDateTime.of(LocalDateTime.of(now, LocalTime.of(14, 40)), ZoneId.of("America/Los_Angeles"));
        System.out.println(hasMeeting(LocalDateTime.of(now, LocalTime.of(13, 10)), meetingInFrankfurt));
        System.out.println(hasMeeting(LocalDateTime.of(now, LocalTime.of(13, 5)), meetingInFrankfurt));
        System.out.println(hasMeeting(LocalDateTime.of(now, LocalTime.of(23, 41)), meetingInLosAngeles));
    }

}
