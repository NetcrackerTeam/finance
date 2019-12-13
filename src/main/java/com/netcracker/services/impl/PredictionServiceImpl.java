package com.netcracker.services.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.models.MonthReport;
import com.netcracker.services.PredictionService;
import com.netcracker.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    MonthReportDao monthReportDao;

    @Override
    public boolean predictCreditPossibility(BigInteger id, int duration, long amount) {
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
        LocalDate date= LocalDate.now();
        LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonth().getValue() - 1, 1);
        LocalDate dateEnd = LocalDate.of(date.getYear(),date.getMonth().getValue() - 1  , 31);

        Date dateFrom = DateUtils.localDateToDate(dateStart);
        Date dateTo = DateUtils.localDateToDate(dateEnd);

        Collection<MonthReport> reports = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
           reports.add(monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo));
        }

        double average = reports.stream().mapToLong(MonthReport::getTotalIncome).average().orElse(Double.NaN);

        return average * duration;
    }

    @Override
    public double predictMonthExpense(BigInteger id, int duration) {

        LocalDate date= LocalDate.now();
        LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonth().getValue() - 1, 1);
        LocalDate dateEnd = LocalDate.of(date.getYear(),date.getMonth().getValue() - 1  , 31);

        Date dateFrom = DateUtils.localDateToDate(dateStart);
        Date dateTo = DateUtils.localDateToDate(dateEnd);

        Collection<MonthReport> reports = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            reports.add(monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo));
        }

        double average = reports.stream().mapToLong(MonthReport::getTotalExpense).average().orElse(Double.NaN);

        return average * duration;
    }
}
