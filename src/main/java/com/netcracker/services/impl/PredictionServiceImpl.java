package com.netcracker.services.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.MonthReport;
import com.netcracker.services.PredictionService;
import com.netcracker.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
public class PredictionServiceImpl implements PredictionService {

    private static final Logger logger = Logger.getLogger(PredictionServiceImpl.class);

    @Autowired
    MonthReportDao monthReportDao;

    @Override
    public boolean predictCreditPossibility(BigInteger id, int duration, long amount) {

        logger.debug("Inserting " + id + " " + duration + " " + amount);

        if(id == null) {
            logger.debug("Invalid id");
            throw new PredictionException("id id null");
        }

        double potentialIncome = predictMonthIncome(id, duration);
        double potentialExpense = predictMonthExpense(id, duration);

        double difference = potentialIncome - potentialExpense;

        if(amount < difference) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double predictMonthIncome(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        if(id == null) {
            logger.debug("Invalid id");
            throw new PredictionException("id id null");
        }

        LocalDate date= LocalDate.now();
        LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonth().getValue() - 1, 1);
        LocalDate dateEnd = LocalDate.of(date.getYear(),date.getMonth().getValue() - 1  , 31);

        Date dateFrom = DateUtils.localDateToDate(dateStart);
        Date dateTo = DateUtils.localDateToDate(dateEnd);

        Collection<MonthReport> reports = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            MonthReport reportTest = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
            if(reportTest == null) {
                logger.debug("There is no report");
                throw new PredictionException("Not enough reports");
            }
           reports.add(reportTest);
        }

        double average = reports.stream().mapToLong(MonthReport::getTotalIncome).average().orElse(Double.NaN);

        return average * duration;
    }

    @Override
    public double predictMonthExpense(BigInteger id, int duration) {
        logger.debug("Inserting " + id + " " + duration);

        if(id == null) {
            logger.debug("Invalid id");
            throw new PredictionException("id id null");
        }

        LocalDate date= LocalDate.now();
        LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonth().getValue() - 1, 1);
        LocalDate dateEnd = LocalDate.of(date.getYear(),date.getMonth().getValue() - 1  , 31);

        Date dateFrom = DateUtils.localDateToDate(dateStart);
        Date dateTo = DateUtils.localDateToDate(dateEnd);

        Collection<MonthReport> reports = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            MonthReport reportTest = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
            if(reportTest == null) {
                logger.debug("There is no report");
                throw new PredictionException("Not enough reports");
            }
            reports.add(reportTest);
        }

        double average = reports.stream().mapToLong(MonthReport::getTotalExpense).average().orElse(Double.NaN);

        return average * duration;
    }
}
