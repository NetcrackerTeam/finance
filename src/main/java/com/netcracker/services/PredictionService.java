package com.netcracker.services;

import com.netcracker.models.MonthReport;

import java.math.BigInteger;
import java.util.List;

public interface PredictionService {

    public boolean predictCreditPossibility(BigInteger id, int duration, double amount);

    public double predictMonthIncome(BigInteger id, int duration);

    public double predictMonthExpense(BigInteger id, int duration);

}
