package com.netcracker.services.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    public DateUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getMonthAmountBetweenDates(LocalDate dateFrom, LocalDate dateTo) {
            int yearFrom = dateFrom.getYear();
            int yearTo = dateTo.getYear();
            int monthFromYear = (yearTo - yearFrom) * 12;
            return monthFromYear + Math.abs(dateTo.getMonthValue() - dateFrom.getMonthValue());
    }

    public static Date localDateToDate(LocalDateTime date) {
        return date == null ? null : Date.from(date.atZone( ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime addMonthsToDate(LocalDateTime date, int amount) {
        return date.plusMonths(amount);
    }

    public static LocalDate addMonthsToDateCredit(LocalDate date, int amount) {
        return date.plusMonths(amount);
    }

    public static Date localDateToDateCredit(LocalDate date) {
        return date == null ? null : Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static  boolean checkMaxDayInCurrentMonth(int days) {
        Calendar myCalendar = (Calendar) Calendar.getInstance().clone();
        int max_date = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days > max_date) {
            return false;
        }
        return true;
    }


    public static int MaxDayInCurrentMonth(){
        Calendar myCalendar = (Calendar) Calendar.getInstance().clone();
        int max_date = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return max_date;
    }
}
