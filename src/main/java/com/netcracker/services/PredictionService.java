package com.netcracker.services;


import java.math.BigInteger;
import java.util.List;


public interface PredictionService {

     boolean predictPersonalCreditPossibility(BigInteger id, int duration, double amount, double rate);

     boolean predictFamilyCreditPossibility(BigInteger id, int duration, double amount, double rate);

     double predictPersonalMonthIncome(BigInteger id, int duration);

     double predictPersonalMonthExpense(BigInteger id, int duration);

     double predictFamilyMonthIncome(BigInteger id, int duration);

     double predictFamilyMonthExpense(BigInteger id, int duration);

     List<Double> getArrayByMovingAverage(List<Double> valuesForMonth, int months);

    String INCOME = "income";

    String EXPENSE = "expense";


}
