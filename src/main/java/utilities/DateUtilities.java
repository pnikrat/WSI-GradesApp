package utilities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by pnikrat on 15.03.17.
 */
public class DateUtilities {
    private DateUtilities() {

    }

    public static Date fromYearMonthDay(int year, int month, int day) {
        Calendar calendarDay = Calendar.getInstance();
        calendarDay.set(year, month - 1, day, 0, 0);
        return calendarDay.getTime();
    }
}
