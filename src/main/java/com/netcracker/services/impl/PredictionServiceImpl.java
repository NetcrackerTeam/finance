package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.MonthReport;
import com.netcracker.services.PredictionService;
import com.netcracker.services.utils.CreditUtils;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PredictionServiceImpl implements PredictionService {

    private static final Logger logger = Logger.getLogger(PredictionServiceImpl.class);

    @Autowired
    MonthReportDao monthReportDao;
    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    UserDao userDao;


    @Override
    public boolean predictPersonalCreditPossibility(BigInteger id, int duration, double amount, double rate) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        ObjectsCheckUtils.isNotNull(id, duration, amount, rate);

        double sumToPay = CreditUtils.getTotalCreditPayment(LocalDate.now(), LocalDate.now().plusMonths(duration), amount, rate);

        double currentAmount = personalDebitAccountDao.getPersonalAccountById(id).getAmount();

        boolean result;
        try {
            double potentialIncome = predictPersonalMonthIncome(id, duration);
            double potentialExpense = predictPersonalMonthExpense(id, duration);
            result = calculateDifference(potentialIncome + currentAmount, potentialExpense, sumToPay);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return compareCreditAndAmount(currentAmount, sumToPay);
        }
        return result;
    }

    @Override
    public boolean predictFamilyCreditPossibility(BigInteger id, int duration, double amount, double rate) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        ObjectsCheckUtils.isNotNull(id, duration, amount, rate);

        double sumToPay = CreditUtils.getTotalCreditPayment(LocalDate.now(), LocalDate.now().plusMonths(duration), amount, rate);

        double currentAmount = familyAccountDebitDao.getFamilyAccountById(id).getAmount();

        boolean result;
        try {
            double potentialIncome = predictFamilyMonthIncome(id, duration);
            double potentialExpense = predictFamilyMonthExpense(id, duration);
            result = calculateDifference(potentialIncome + currentAmount, potentialExpense, sumToPay);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return compareCreditAndAmount(currentAmount, sumToPay);
        }
        return result;
    }

    @Override
    public double predictPersonalMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        List<MonthReport> reports = getPersonalReports(id);

        return calculateSumByMovingAverage(addToList(reports, INCOME), duration);
    }

    @Override
    public double predictPersonalMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);

        List<MonthReport> reports = getPersonalReports(id);

        return calculateSumByMovingAverage(addToList(reports, EXPENSE), duration);
    }

    @Override
    public double predictFamilyMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        List<MonthReport> reports = getFamilyReports(id);

        return calculateSumByMovingAverage(addToList(reports, INCOME), duration);
    }

    @Override
    public double predictFamilyMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);


        List<MonthReport> reports = getFamilyReports(id);

        return calculateSumByMovingAverage(addToList(reports, EXPENSE), duration);
    }

    private boolean calculateDifference(double income, double expense, double amount) {
        double difference = income - expense;

        return amount < difference;
    }


    private boolean compareCreditAndAmount(double currentAmount, double amount) {
        if(currentAmount > amount ) {
            return true;
        } else {
            throw new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
        }
    }

    private List<Double> addToList(List<MonthReport> reports, String type) {
        List<Double> valuesForMonth = new ArrayList<>();

        if(INCOME.equals(type)) {
            for (MonthReport m : reports) {
                valuesForMonth.add(m.getTotalIncome());
            }
        } else {
            for (MonthReport m : reports) {
                valuesForMonth.add(m.getTotalExpense());
            }
        }
        return valuesForMonth;
    }

    private List<MonthReport> getPersonalReports(BigInteger id) {
        List<MonthReport> reports = monthReportDao.getFullPersonalReports(id);
        if(reports.size() < 3) {
            throw new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
        }
        return reports;
    }

    private List<MonthReport> getFamilyReports(BigInteger id) {
        List<MonthReport> reports = monthReportDao.getFullFamilyReports(id);
        if(reports.size() < 3) {
            throw new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
        }
        return reports;
    }

    private double calculateSumByMovingAverage(List<Double> valuesForMonth, int months) {

        Map<Integer, Double> movingFunctionValue = new HashMap<>();

        int lastStaticValue = valuesForMonth.size();

        double average = 0;

        for (int i = 1; i < valuesForMonth.size() - 1; i++) {
            average = (valuesForMonth.get(i - 1) + valuesForMonth.get(i) + valuesForMonth.get(i+1))/3;
            movingFunctionValue.put(i,average);
        }

        double predictMonthSum = 0;

        int monthFromCount = lastStaticValue;

        for (int i = 1; i <= months; i++) {
            predictMonthSum = movingFunctionValue.get(movingFunctionValue.size()) +
                    ((valuesForMonth.get(lastStaticValue - 1) - valuesForMonth.get(lastStaticValue - 2))/3);
            valuesForMonth.add(predictMonthSum);

            movingFunctionValue.put(movingFunctionValue.size() + 1, (valuesForMonth.get(valuesForMonth.size() - 3)
                    + valuesForMonth.get(valuesForMonth.size() - 2) + valuesForMonth.get(valuesForMonth.size() - 1))/3);
            lastStaticValue++;
        }

        return new BigDecimal(valuesForMonth.stream().skip(monthFromCount).mapToDouble(Double::doubleValue).sum())
                .setScale(2, RoundingMode.UP).doubleValue();
    }

}
