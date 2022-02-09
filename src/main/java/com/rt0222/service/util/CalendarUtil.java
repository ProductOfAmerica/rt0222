package com.rt0222.service.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

    public static boolean isWeekend(Calendar cal) {
        return (Calendar.SATURDAY == cal.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == cal.get(Calendar.DAY_OF_WEEK));
    }

    public static boolean isHoliday(Calendar cal) {
        return isConsideredIndependenceDay(cal) || isLaborDay(cal);
    }

    public static boolean isConsideredIndependenceDay(Calendar cal) {
        return (Calendar.JULY == cal.get(Calendar.MONTH) && 4 == cal.get(Calendar.DAY_OF_MONTH) && !isWeekend(cal))
                || (independenceDayIsSundayButObservedOnMonday(cal) || independenceDayIsSaturdayButObservedOnFriday(cal));
    }

    public static boolean isLaborDay(Calendar cal) {
        return Calendar.SEPTEMBER == cal.get(Calendar.MONTH) && 1 == cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) && Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean independenceDayIsSundayButObservedOnMonday(Calendar cal) {
        return Calendar.JULY == cal.get(Calendar.MONTH) && 5 == cal.get(Calendar.DAY_OF_MONTH) && Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean independenceDayIsSaturdayButObservedOnFriday(Calendar cal) {
        return Calendar.JULY == cal.get(Calendar.MONTH) && 3 == cal.get(Calendar.DAY_OF_MONTH) && Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK);
    }
}
