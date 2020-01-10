package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.MonthReport;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.services.PersonalCreditService;
import com.netcracker.services.PredictionService;
import com.netcracker.services.utils.CreditUtils;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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
    public boolean predictPersonalCreditPossibility(BigInteger id, int duration, double amount) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        ObjectsCheckUtils.isNotNull(id, duration, amount);

        double potentialIncome = predictPersonalMonthIncome(id, duration);
        double potentialExpense = predictPersonalMonthExpense(id, duration);

        boolean result;
        try {
            result = calculateDifference(potentialIncome, potentialExpense, amount);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            double currentAmount = personalDebitAccountDao.getPersonalAccountById(userDao.getUserById(id).getPersonalDebitAccount()).getAmount();
            return compareCreditAndAmount(currentAmount, amount);
        }
        return result;
    }

    @Override
    public boolean predictFamilyCreditPossibility(BigInteger id, int duration, double amount) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        ObjectsCheckUtils.isNotNull(id, duration, amount);

        double potentialIncome = predictFamilyMonthIncome(id, duration);
        double potentialExpense = predictFamilyMonthExpense(id, duration);

        boolean result;
        try {
            result = calculateDifference(potentialIncome, potentialExpense, amount);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            double currentAmount = familyAccountDebitDao.getFamilyAccountById(userDao.getUserById(id).getFamilyDebitAccount()).getAmount();
            return compareCreditAndAmount(currentAmount, amount);
        }
        return result;
    }

    @Override
    public double predictPersonalMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        Collection<MonthReport> reports = monthReportDao.getAllPersonalReports(id);

        return calculateAverage(reports, reports.size(), INCOME) * duration;
    }

    @Override
    public double predictPersonalMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);

        Collection<MonthReport> reports = monthReportDao.getAllPersonalReports(id);

        return calculateAverage(reports, reports.size(), EXPENSE) * duration;
    }

    @Override
    public double predictFamilyMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        Collection<MonthReport> reports = monthReportDao.getAllFamilyReports(id);

        return calculateAverage(reports, reports.size(), INCOME) * duration;
    }

    @Override
    public double predictFamilyMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);

        Collection<MonthReport> reports = monthReportDao.getAllFamilyReports(id);

        return calculateAverage(reports, reports.size(), EXPENSE) * duration;
    }

    private boolean calculateDifference(double income, double expense, double amount) {
        double difference = income - expense;

        return amount < difference;
    }

    private double calculateAverage(Collection<MonthReport> reports, int size, String type) {

        double average = 0;

        if (size > 2) {
            if(INCOME.equals(type)) {
                average = reports.stream().mapToDouble(MonthReport::getTotalIncome).average().orElse(Double.NaN);
            } else if (EXPENSE.equals(type)){
                average = reports.stream().mapToDouble(MonthReport::getTotalExpense).average().orElse(Double.NaN);
            }
        } else {
            PredictionException e = new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
            logger.error(e.getMessage(), e);
            throw e;
        }
        return average;
    }

    private boolean compareCreditAndAmount(double currentAmount, double amount) {
        if(currentAmount > amount ) {
            return true;
        } else {
            throw new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
        }
    }

}
