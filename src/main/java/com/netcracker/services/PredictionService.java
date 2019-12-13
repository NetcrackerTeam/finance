package com.netcracker.services;

import java.math.BigInteger;

public interface PredictionService {

    public boolean predictCreditPossibility(BigInteger id, int duration, long amount);

    public double predictMonthIncome(BigInteger id, int duration);

    public double predictMonthExpense(BigInteger id, int duration);
}
