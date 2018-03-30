package dd.ch12;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class P1LocalDateEtAl {
    public static void main(String[] args) {
        listingOne();
        listingTwo();
        listingThree();
        listingFour();
        
        instant();
        
        duration();
        
        JapaneseDate jap = JapaneseDate.from(LocalDate.of(2018, Month.MARCH, 12));
        System.out.println(jap);
    }

    private static void duration() {
        LocalTime lt1 = LocalTime.of(13, 45, 10);
        LocalTime lt2 = LocalTime.of(13, 45, 20);
        
        LocalDateTime ldt1 = LocalDateTime.of(2018, Month.MARCH, 22, 22, 37);
        LocalDateTime ldt2 = LocalDateTime.of(2022, Month.MAY, 22, 7, 3, 59);
        Duration d1t = Duration.between(lt1, lt2);
        Duration d1dt = Duration.between(ldt1, ldt2);
        System.out.println("Positive durations: " + d1t.getSeconds() + ", " + d1dt.getSeconds());

        Duration d2 = Duration.between(lt2, lt1);
        System.out.println("Negative duration: " + d2.getSeconds());
        
        LocalDate ld1 = LocalDate.of(2018, Month.MARCH, 12);
        LocalDate ld2 = LocalDate.of(2018, Month.MAY, 13);
        Period twoMo = Period.between(ld1, ld2);
        System.out.println("Two month period: " + twoMo.getMonths() + ", " + twoMo.getDays());
        
        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes.getSeconds());

    }

    private static void instant() {
        System.out.println("Instant 3 sec: " + Instant.ofEpochSecond(3));
        System.out.println("Variation 1: " + Instant.ofEpochSecond(3, 0));
        System.out.println("Variation 2: " + Instant.ofEpochSecond(2, 1_000_000_000));
        System.out.println("Variation 3: " + Instant.ofEpochSecond(4, -1_000_000_000));
        
        try {
            Instant.now().get(ChronoField.DAY_OF_MONTH);
        } catch(UnsupportedTemporalTypeException utte) {
            System.out.println("Instant are not human friendly, " + utte);
        }
        
        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();
        System.out.println(instant + " < " + now);
    }

    private static void listingFour() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        System.out.println("2014-03-18T13:45 == " + dt1);
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        System.out.println("Now, date and time: " + dt2);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        System.out.println("Today, at a given time: " + dt3);
        LocalDateTime dt4 = date.atTime(time);
        System.out.println(dt4);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt5);

        System.out.println("Local time from full datetime: " + dt1.toLocalTime());
        System.out.println("Local date from full datetime: " + dt1.toLocalDate());
    }

    private static void listingThree() {
        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println(time + " == " + hour + "_" + minute + "_" + second);

        System.out.println("Parsed date: " + LocalDate.parse("2014-03-18"));
        System.out.println("Parsed time: " + LocalTime.parse("13:45:20"));

        try {
            LocalDate.parse("Mistake");
        } catch (DateTimeParseException pe) {
            System.out.println("As expected, we get a " + pe);
        }
    }

    private static void listingTwo() {
        LocalDate date = LocalDate.now();
        int year = date.get(ChronoField.YEAR);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);

        System.out.println(date + " == " + day + "_" + month + "_" + year);
    }

    private static void listingOne() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        System.out.println("Date is: " + date);

        int year = date.getYear(); // 2014
        System.out.println("Year is 2014: " + (year == 2014));

        Month month = date.getMonth(); // MARCH
        System.out.println("Month is MARCH: " + (month == Month.MARCH));

        int day = date.getDayOfMonth(); // 18
        System.out.println("Day is 18: " + (day == 18));

        DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
        System.out.println("Day of week is TUESDAY: " + (dow == DayOfWeek.TUESDAY));

        int len = date.lengthOfMonth(); // 31 (days in March)
        System.out.println("31 days in month: " + (len == 31));

        boolean leap = date.isLeapYear(); // false (not a leap year)
        System.out.println("It is not a leap year: " + !leap);

        System.out.println("Today is " + LocalDate.now());
    }
}
