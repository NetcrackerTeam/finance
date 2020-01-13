package com.netcracker.services.utils;

import java.time.LocalDate;

import static com.netcracker.services.utils.DateUtils.getMonthAmountBetweenDates;

public final class CreditUtils {

    private final static int MONTH_IN_YEAR = 12;
    private final static int PERCENT_CALC = 100;

    private CreditUtils() {
        throw new UnsupportedOperationException();
    }

    public static double calculateMonthPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
        double oneMonthRate = rate / MONTH_IN_YEAR;
        double allowance = (amount / PERCENT_CALC) * oneMonthRate;
        double paymentWithoutRate = amount / getMonthAmountBetweenDates(dateFrom, dateTo);
        double result = paymentWithoutRate + allowance;
        return Math.round(result * 100.0) / 100.0;
    }

    public static double getTotalCreditPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
        return Math.round(getMonthAmountBetweenDates(dateFrom, dateTo) * calculateMonthPayment(dateFrom, dateTo, amount, rate)) * 100 / 100;
    }


}
