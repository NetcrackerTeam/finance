package com.netcracker.services.utils;

import java.time.LocalDate;

import static com.netcracker.services.utils.DateUtils.getMonthAmountBetweenDates;

public final class CreditUtils {

    private final static int MONTH_IN_YEAR = 12;
    private final static int PERCENT_CALC = 100;

    private CreditUtils() {
        throw new UnsupportedOperationException();
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


}
