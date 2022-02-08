package com.rt0222.service.util;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalendarUtilTests {
    @Test
    void testLaborDayReturnsTrue() {
        GregorianCalendar cal = new GregorianCalendar(2022, Calendar.SEPTEMBER, 5);
        assertTrue(CalendarUtil.isLaborDay(cal));
    }

    @Test
    void testNonLaborDaysReturnFalse() {
        GregorianCalendar cal = new GregorianCalendar(2022, Calendar.SEPTEMBER, 6);
        assertFalse(CalendarUtil.isLaborDay(cal));

        cal = new GregorianCalendar(2022, Calendar.AUGUST, 4);
        assertFalse(CalendarUtil.isLaborDay(cal));
    }

    @Test
    void testIndependenceDayReturnsTrue() {
        GregorianCalendar cal = new GregorianCalendar(2022, Calendar.JULY, 4);
        assertTrue(CalendarUtil.isConsideredIndependenceDay(cal));

        cal = new GregorianCalendar(2026, Calendar.JULY, 3);
        assertTrue(CalendarUtil.isConsideredIndependenceDay(cal));

        // July 4, 2026, falls on a Saturday. Test to make sure observed date is really on Friday (the 3rd)
        cal = new GregorianCalendar(2026, Calendar.JULY, 3);
        assertTrue(CalendarUtil.isConsideredIndependenceDay(cal));

        // July 4, 2010, falls on a Sunday. Test to make sure observed date is really on Monday (the 5th)
        cal = new GregorianCalendar(2010, Calendar.JULY, 5);
        assertTrue(CalendarUtil.isConsideredIndependenceDay(cal));
    }

    @Test
    void testNonIndependenceDaysReturnFalse() {
        GregorianCalendar cal = new GregorianCalendar(2022, Calendar.JULY, 5);
        assertFalse(CalendarUtil.isConsideredIndependenceDay(cal));

        cal = new GregorianCalendar(2022, Calendar.JULY, 3);
        assertFalse(CalendarUtil.isConsideredIndependenceDay(cal));

        // July 4, 2026, falls on a Saturday. Test to make sure observed date is really on Friday (the 3rd)
        cal = new GregorianCalendar(2026, Calendar.JULY, 4);
        assertFalse(CalendarUtil.isConsideredIndependenceDay(cal));

        // July 4, 2010, falls on a Sunday. Test to make sure observed date is really on Monday (the 5th)
        cal = new GregorianCalendar(2010, Calendar.JULY, 4);
        assertFalse(CalendarUtil.isConsideredIndependenceDay(cal));
    }
}
