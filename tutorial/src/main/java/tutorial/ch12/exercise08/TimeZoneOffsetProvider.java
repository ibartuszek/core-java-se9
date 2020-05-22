package tutorial.ch12.exercise08;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 08
 * Obtain the offsets of today’s date in all supported time zones for the current
 * time instant, turning ZoneId.getAvailableZoneIds into a stream and
 * using stream operations.
 * 09
 * Again using stream operations, find all time zones whose offsets aren’t full
 * hours.
 */
public class TimeZoneOffsetProvider {


    public Stream<ZoneOffset> provide() {
        LocalDateTime now = LocalDateTime.now();
        return ZoneId.getAvailableZoneIds().stream()
            .map(ZoneId::of)
            .map(zoneId -> zoneId.getRules().getOffset(now))
            .sorted(ZoneOffset::compareTo);
    }

    public Stream<ZoneOffset> provideNotFullHoursOffsets() {
        return provide()
            .filter(zoneOffset -> getMinutes(zoneOffset) != 0);
    }

    private int getMinutes(final ZoneOffset zoneOffset) {
        return zoneOffset.getTotalSeconds() / 60 % 60;
    }

    public static void main(String[] args) {
        TimeZoneOffsetProvider provider = new TimeZoneOffsetProvider();
        provider.provide().forEach(System.out::println);
        System.out.println();
        provider.provideNotFullHoursOffsets().forEach(System.out::println);
    }

}
