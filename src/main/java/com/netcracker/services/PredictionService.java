package com.netcracker.services;


import java.math.BigInteger;


public interface PredictionService {

     boolean predictPersonalCreditPossibility(BigInteger id, int duration, double amount);

     boolean predictFamilyCreditPossibility(BigInteger id, int duration, double amount);

     double predictPersonalMonthIncome(BigInteger id, int duration);

     double predictPersonalMonthExpense(BigInteger id, int duration);

     double predictFamilyMonthIncome(BigInteger id, int duration);

     double predictFamilyMonthExpense(BigInteger id, int duration);


    String INCOME = "income";

    String EXPENSE = "expense";


}
