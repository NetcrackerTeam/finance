package com.netcracker.services.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        return new BigDecimal(result).setScale(2, RoundingMode.UP).doubleValue();
    }

    public static double getTotalCreditPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
        return new BigDecimal(getMonthAmountBetweenDates(dateFrom, dateTo) * calculateMonthPayment(dateFrom, dateTo, amount, rate))
                .setScale(2, RoundingMode.UP).doubleValue();
    }

}
