package com.netcracker.services;

import com.netcracker.models.MonthReport;

import java.math.BigInteger;
import java.util.List;

public interface PredictionService {

    public boolean predictPersonalCreditPossibility(BigInteger id, int duration, double amount);

    public boolean predictFamilyCreditPossibility(BigInteger id, int duration, double amount);

    public double predictPersonalMonthIncome(BigInteger id, int duration);

    public double predictPersonalMonthExpense(BigInteger id, int duration);

    public double predictFamilyMonthIncome(BigInteger id, int duration);

    public double predictFamilyMonthExpense(BigInteger id, int duration);


}
