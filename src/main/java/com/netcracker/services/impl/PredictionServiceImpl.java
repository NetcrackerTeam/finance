package com.netcracker.services.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.MonthReport;
import com.netcracker.services.PredictionService;
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

@Service
public class PredictionServiceImpl implements PredictionService {

    private static final Logger logger = Logger.getLogger(PredictionServiceImpl.class);

    @Autowired
    MonthReportDao monthReportDao;

    @Override
    public boolean predictPersonalCreditPossibility(BigInteger id, int duration, double amount) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        ObjectsCheckUtils.isNotNull(id, duration, amount);

        double potentialIncome = predictPersonalMonthIncome(id, duration);
        double potentialExpense = predictPersonalMonthExpense(id, duration);

        return calculateDifference(potentialIncome, potentialExpense, amount);
    }

    @Override
    public boolean predictFamilyCreditPossibility(BigInteger id, int duration, double amount) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        ObjectsCheckUtils.isNotNull(id, duration, amount);

        double potentialIncome = predictFamilyMonthIncome(id, duration);
        double potentialExpense = predictFamilyMonthExpense(id, duration);

        return calculateDifference(potentialIncome, potentialExpense, amount);
    }

    @Override
    public double predictPersonalMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        Collection<MonthReport> reports = getPersonalReportsOfSixMonths(id);

        double average = reports.stream().mapToDouble(MonthReport::getTotalIncome).average().orElse(Double.NaN);

        return average * duration;
    }

    @Override
    public double predictPersonalMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);

        Collection<MonthReport> reports = getPersonalReportsOfSixMonths(id);

        double average = reports.stream().mapToDouble(MonthReport::getTotalExpense).average().orElse(Double.NaN);

        return average * duration;
    }

    @Override
    public double predictFamilyMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        Collection<MonthReport> reports = getFamilyReportsOfSixMonths(id);

        double average = reports.stream().mapToDouble(MonthReport::getTotalIncome).average().orElse(Double.NaN);

        return average * duration;
    }

    @Override
    public double predictFamilyMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);

        Collection<MonthReport> reports = getFamilyReportsOfSixMonths(id);

        double average = reports.stream().mapToDouble(MonthReport::getTotalExpense).average().orElse(Double.NaN);

        return average * duration;
    }


    private List<MonthReport> getPersonalReportsOfSixMonths(BigInteger id) {

        List<MonthReport> reports = new ArrayList<>();

        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue() - 1, 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1);

        int monthReports = 6;


        for (int i = 0; i < monthReports; i++) {
            MonthReport reportTest = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
            if (reportTest == null) {
                logger.debug("There is no report");
                throw new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
            }
            reports.add(reportTest);
        }
        return reports;
    }

    private List<MonthReport> getFamilyReportsOfSixMonths(BigInteger id) {

        List<MonthReport> reports = new ArrayList<>();

        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue() - 1, 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1);

        int monthReports = 6;

        for (int i = 0; i < monthReports; i++) {
            MonthReport reportTest = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom, dateTo);
            if (reportTest == null) {
                logger.debug("There is no report");
                throw new PredictionException(ExceptionMessages.ERROR_MESSAGE_PREDICTION);
            }
            reports.add(reportTest);
        }
        return reports;
    }

    private boolean calculateDifference(double income, double expense, double amount) {
        double difference = income - expense;

        if (amount < difference) {
            return true;
        } else {
            return false;
        }
    }
}
