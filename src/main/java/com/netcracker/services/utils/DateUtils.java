package com.netcracker.services.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtils {

    public DateUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getMonthAmountBetweenDates(LocalDate dateFrom, LocalDate dateTo) {
        return Period.between(dateFrom, dateTo).getMonths();
    }

    public static Date localDateToDate(LocalDate date) {
        return date == null ? null : Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
