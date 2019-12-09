package com.netcracker.services.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public final class CreditUtils {

    private final static int MONTH_IN_YEAR = 12;
    private final static int PERCENT_CALC = 100;

    private CreditUtils() {
        throw new UnsupportedOperationException();
    }

    public static LocalDate addMonthsToDate(LocalDate date, int amount) {
        return date.plusMonths(amount);
    }

    public static long calculateMonthPayment(LocalDate dateFrom, LocalDate dateTo, long amount, long rate) {
        long oneMonthRate = rate / MONTH_IN_YEAR;
        long allowance = (amount / PERCENT_CALC) * oneMonthRate;
        long paymentWithoutRate = amount / getMonthAmountBetweenDates(dateFrom, dateTo);
        return paymentWithoutRate + allowance;
    }

    public static long getTotalCreditPayment(LocalDate dateFrom, LocalDate dateTo, long amount, long rate) {
        return getMonthAmountBetweenDates(dateFrom, dateTo) * calculateMonthPayment(dateFrom, dateTo, amount, rate);
    }

    public static int getMonthAmountBetweenDates(LocalDate dateFrom, LocalDate dateTo) {
        return Period.between(dateFrom, dateTo).getMonths();
    }

    public static LocalDate getMonthAmountBetweenDates(LocalDate dateFrom, int month) {
        return dateFrom.plus(Period.ofMonths(month));
    }

    public static Date localDateToSqlDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }
}
