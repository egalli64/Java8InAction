package dd.ch12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class P2Manipulating {
    public static void main(String[] args) {
        changeLocalDateAttr();
        predefinedTemporalAdjusters();
        quiz2();
        formatAndParse();
    }

    private static void formatAndParse() {
        LocalDate date = LocalDate.of(2018, 3, 28);
        System.out.println("Base date: " + date);
        System.out.println("Basic ISO: " + date.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println("ISO local: " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));

        LocalDate d2 = LocalDate.parse("20180328", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate d3 = LocalDate.parse("2018-03-28", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println((date.equals(d2)) + ", " + (d2.equals(d3)));

        DateTimeFormatter dtfSimple = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfItaly = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        DateTimeFormatter dtfItaly2 = new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ").appendText(ChronoField.MONTH_OF_YEAR).appendLiteral(" ")
                .appendText(ChronoField.YEAR).parseCaseInsensitive().toFormatter(Locale.ITALIAN);

        String simplyFormatted = date.format(dtfSimple);
        LocalDate d4 = LocalDate.parse(simplyFormatted, dtfSimple);
        System.out.println(simplyFormatted + " --> " + d4);

        String italFormatted = date.format(dtfItaly);
        LocalDate d5 = LocalDate.parse(italFormatted, dtfItaly);
        System.out.println(italFormatted + " --> " + d5);

        String italF2 = date.format(dtfItaly2);
        LocalDate d6 = LocalDate.parse(italF2, dtfItaly);
        System.out.println(italF2 + " --> " + d6);
    }

    private static void quiz2() {
        LocalDate mon = LocalDate.of(2018, 3, 26);
        LocalDate fri = LocalDate.of(2018, 3, 23);
        LocalDate sat = LocalDate.of(2018, 3, 24);
        LocalDate sun = LocalDate.of(2018, 3, 25);
        System.out.println("Monday: " + mon + " next working day: " + mon.with(new NextWorkingDay()));
        System.out.println("Friday: " + fri + " next working day: " + fri.with(new NextWorkingDay()));
        System.out.println("Saturday: " + sat + " next working day: " + sat.with(new NextWorkingDay()));
        System.out.println("Sunday: " + sun + " next working day: " + sun.with(new NextWorkingDay()));

        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(temporal -> {
            int dow = temporal.get(ChronoField.DAY_OF_WEEK);
            int shift = dow == 5 ? 3 : dow == 6 ? 2 : 1;
            return temporal.plus(shift, ChronoUnit.DAYS);
        });

        System.out.println("Monday: " + mon + " nwd: " + mon.with(nextWorkingDay));
        System.out.println("Friday: " + fri + " nwd: " + fri.with(nextWorkingDay));
        System.out.println("Saturday: " + sat + " nwd: " + sat.with(nextWorkingDay));
        System.out.println("Sunday: " + sun + " nwd: " + sun.with(nextWorkingDay));

    }

    private static void predefinedTemporalAdjusters() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        System.out.println("Start date: " + date);
        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println("Next or same sunday: " + date);
        date = date.with(lastDayOfMonth());
        System.out.println("Last day of month: " + date);
    }

    private static void changeLocalDateAttr() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        System.out.println(date);
        System.out.println("Change year to 2018: " + date.withYear(2018));
        System.out.println("Change day to 12: " + date.withDayOfMonth(12));
        System.out.println("Change month to April: " + date.withMonth(4));

        System.out.println("Next week: " + date.plusWeeks(1));
        System.out.println("Three years before: " + date.minusYears(3));
        System.out.println("Six months later: " + date.plusMonths(6));
    }
}

class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        int dow = temporal.get(ChronoField.DAY_OF_WEEK);
        int shift = dow == 5 ? 3 : dow == 6 ? 2 : 1;
        return temporal.plus(shift, ChronoUnit.DAYS);
    }

}
