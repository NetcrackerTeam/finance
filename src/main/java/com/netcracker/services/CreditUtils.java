package com.netcracker.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public final class CreditUtils {

    private final static int MONTH_IN_YEAR = 12;
    private final static int PERCENT_CALC = 100;

    private CreditUtils() {
        throw new UnsupportedOperationException();
    }

    public static Date addMonthsToDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    public static long calculateMonthPayment(LocalDate dateFrom, LocalDate dateTo, long amount, long rate) {
        int months = Period.between(dateFrom, dateTo).getMonths();
        long oneMonthRate = rate / MONTH_IN_YEAR;
        long allowance = (amount / PERCENT_CALC) * oneMonthRate;
        long paymentWithoutRate = amount / months;
        return paymentWithoutRate + allowance;
    }

}
