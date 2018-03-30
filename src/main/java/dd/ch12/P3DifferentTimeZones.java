package dd.ch12;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class P3DifferentTimeZones {
    public static void main(String[] args) {
        ZoneId rome = ZoneId.of("Europe/Rome");
        ZoneId zid = TimeZone.getDefault().toZoneId();
        System.out.println(rome + ", " + zid);

        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = date.atStartOfDay(rome);

        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(rome);

        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(rome);
        System.out.println(zdt1 + ", " + zdt2 + ", " + zdt3);

        ZoneOffset zoff = ZoneOffset.ofHours(1);
        Instant instantFromDateTime = dateTime.toInstant(zoff);
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant, rome);
        System.out.println(zoff + ", " + instantFromDateTime + ", " + timeFromInstant);
    }
}
