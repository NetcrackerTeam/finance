package com.netcracker.services.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.MonthReport;
import com.netcracker.services.PredictionService;
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
    public boolean predictCreditPossibility(BigInteger id, int duration, double amount) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        if (id == null) {
            logger.debug("Invalid id");
            throw new PredictionException("Id is null");
        }

        if (duration <= 0) {
            logger.debug("Invalid duration");
            throw new PredictionException("Duration should be more");
        }

        double potentialIncome = predictMonthIncome(id, duration);
        double potentialExpense = predictMonthExpense(id, duration);

        double difference = potentialIncome - potentialExpense;

        if (amount < difference) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double predictMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        Collection<MonthReport> reports = getReportsOfSixMonths(id);

        double average = reports.stream().mapToDouble(MonthReport::getTotalIncome).average().orElse(Double.NaN);

        return average * duration;
    }

    @Override
    public double predictMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting in predict " + id + " " + duration);

        Collection<MonthReport> reports = getReportsOfSixMonths(id);

        double average = reports.stream().mapToDouble(MonthReport::getTotalExpense).average().orElse(Double.NaN);

        return average * duration;
    }

    private List<MonthReport> getReportsOfSixMonths(BigInteger id) {

        List<MonthReport> reports = new ArrayList<>();

        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue() - 1, 1);
        LocalDate dateTo = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1);

        int monthReports = 6;

        for (int i = 0; i < monthReports; i++) {
            MonthReport reportTest = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
            if (reportTest == null) {
                logger.debug("There is no report");
                throw new PredictionException("Not enough reports");
            }
            reports.add(reportTest);
        }
        return reports;
    }

}
