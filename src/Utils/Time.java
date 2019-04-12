package Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Time {
    public static long getFirstWeek() {
        LocalDate endDate=LocalDate.of(2018, 10, 2);
        LocalDate startDate=LocalDate.of(2018, 1, 1);
        long weeks= ChronoUnit.WEEKS.between(startDate, endDate);
        return weeks;
    }

    public static long getCurrentWeek(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR)-getFirstWeek()+1;
    }

    public static LocalDate getFirstDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        Date firstDayOfTheWeek = cal.getTime();
        LocalDate date=firstDayOfTheWeek.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    public static LocalDate getFirstDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date firstDayOfTheWeek = cal.getTime();
        LocalDate date=firstDayOfTheWeek.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }
}
