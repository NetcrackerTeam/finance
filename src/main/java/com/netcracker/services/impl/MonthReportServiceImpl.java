package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.exception.MonthReportException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class MonthReportServiceImpl implements MonthReportService {

    private static final Logger logger = Logger.getLogger(MonthReportServiceImpl.class);

    @Autowired
    MonthReportDao monthReportDao;

    @Autowired
    OperationDao operationDao;

    @Autowired
    FamilyAccountDebitDao familyAccountDebitDao;

    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;

    @Autowired
    UserDao userDao;

    @Override
    public void formMonthFamilyReportFromDb(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo) {
        logger.debug("Insert formFamilyReportFromDb " + id);

        ObjectsCheckUtils.isNotNull(id);


        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesFamilyGroupByCategories(id, dateFrom);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesFamilyGroupByCategories(id, dateFrom);

        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);

        double totalIncome = incomeReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        FamilyDebitAccount debitAccount = familyAccountDebitDao.getFamilyAccountById(id);

        ObjectsCheckUtils.isNotNull(debitAccount);

        double sum = familyAccountDebitDao.getFamilyAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .dateTo(dateTo)
                .dateFrom(dateFrom)
                .build();

        monthReportDao.createFamilyMonthReport(monthReport, id);


        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom,
                dateTo).getId();

        ObjectsCheckUtils.isNotNull(idOfRecentMonth);

        expenseReports.forEach(exp -> {
            monthReportDao.createCategoryExpenseFamilyReport(idOfRecentMonth, exp.getUserReference(), exp);
        });
        incomeReports.forEach(inc -> {
            monthReportDao.createCategoryIncomeFamilyReport(idOfRecentMonth, inc.getUserReference(), inc);
        });
    }

    @Override
    public void formMonthPersonalReportFromDb(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo) {
        logger.debug("Insert formPersonalReportFromDb " + id);

        ObjectsCheckUtils.isNotNull(id, dateTo);

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesPersonalGroupByCategories(id, dateFrom);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesPersonalGroupByCategories(id, dateFrom);

        double totalIncome = incomeReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        double sum = personalDebitAccountDao.getPersonalAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();

        monthReportDao.createPersonalMonthReport(monthReport, id);

        BigInteger idOfRecentReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom,
                dateTo).getId();

        ObjectsCheckUtils.isNotNull(idOfRecentReport);

        expenseReports.forEach(exp -> {
            monthReportDao.createCategoryExpensePersonalReport(idOfRecentReport, exp);
        });
        incomeReports.forEach(inc -> {
            monthReportDao.createCategoryIncomePersonalReport(idOfRecentReport, inc);
        });
    }

    @Override
    public Path convertToTxt(MonthReport monthReport) {
        logger.debug("Convertation " + monthReport);

        ObjectsCheckUtils.isNotNull(monthReport);

        Path path = Paths.get( "report.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(MONTH_REPORT_FROM +
                    + monthReport.getDateFrom().getYear() +
                    HYPHEN + monthReport.getDateFrom().getMonth().getValue() + HYPHEN +
                    monthReport.getDateFrom().getDayOfMonth());

            writer.write(MONTH_REPORT_TO + monthReport.getDateTo().getYear() + HYPHEN +
                    monthReport.getDateTo().getMonth().getValue() +
                    HYPHEN + monthReport.getDateTo().getDayOfMonth() +  NEW_LINE);

            writer.write(DOTTED_LINE);
            writer.write(ACTUAL_BALANCE + monthReport.getBalance() + TAB_AND_LINE);
            writer.write(TOTAL_EXPENSE + monthReport.getTotalExpense() + TAB_AND_LINE);
            writer.write(TOTAL_INCOME + monthReport.getTotalIncome() + TAB_AND_LINE);

            if(monthReport.getCategoryExpense().size() > 0) {
                writer.write(NEW_LINE + EXPENSES_BY_CATEGORIES);
                writer.write(DOTTED_LINE);
            }

            String compareParticipants = null;
            String name = null;
            for (CategoryExpenseReport exp :
                    monthReport.getCategoryExpense()) {
                try {
                    name = userDao.getUserById(exp.getUserReference()).getName();
                } catch (UserException e) {
                    logger.debug(e.getMessage(), e);
                }

                if (name != null) {
                    if(!name.equals(compareParticipants)){
                        writer.write(name + NEW_LINE);
                        compareParticipants = name;
                    }
                    writer.write(SPACE);
                }
                writer.write(exp.getCategoryExpense().toString() + DOUBLE_DOTS + exp.getAmount() + NEW_LINE);
            }

            if(monthReport.getCategoryIncome().size() > 0) {
                writer.write(INCOMES_BY_CATEGORIES);
                writer.write(DOTTED_LINE);
            }

            compareParticipants = null;
            name = null;

            for (CategoryIncomeReport inc :
                    monthReport.getCategoryIncome()) {

                try {
                    name = userDao.getUserById(inc.getUserReference()).getName();
                } catch (UserException e) {
                    logger.debug(e.getMessage(), e);
                }
                if (name != null) {
                    if(!name.equals(compareParticipants)){
                        writer.write(name + NEW_LINE);
                        compareParticipants = name;
                    }
                    writer.write(SPACE);
                }
                writer.write(inc.getCategoryIncome().toString() + DOUBLE_DOTS + inc.getAmount() + NEW_LINE);
            }
        } catch (IOException e) {
            logger.error("Error in writing to file", e);
            throw new MonthReportException(ExceptionMessages.ERROR_MESSAGE_MONTH_REPORT_WRITE);
        }
        return path;
    }


    @Override
    public MonthReport getMonthPersonalReport(BigInteger id, LocalDateTime date, boolean isJob) {
        logger.debug("Id " + id + " date " + date);

        ObjectsCheckUtils.isNotNull(id, date);

        TwoValue<LocalDateTime> dates = calculateDates(date, id, isJob, false);

        MonthReport monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dates.getValue1(), dates.getValue2());
        ObjectsCheckUtils.isNotNull(monthReport);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpensePersonalReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomePersonalReport(monthReport.getId());
        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public MonthReport getMonthFamilyReport(BigInteger id, LocalDateTime date, boolean isJob) {
        logger.debug("Id " + id + " dateFrom " + date);

        ObjectsCheckUtils.isNotNull(id, date);

        TwoValue<LocalDateTime> dates = calculateDates(date, id, isJob, true);

        MonthReport monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dates.getValue1(), dates.getValue2());
        ObjectsCheckUtils.isNotNull(monthReport);

        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpenseFamilyReport(monthReport.getId());

        String compare = null;
        boolean check = false;
        for (CategoryExpenseReport c: expenseReports) {
            if(check) {
                if (compare.equals(userDao.getUserById(c.getUserReference()).getName())) {
                    c.setUserName("");
                } else {
                    compare = userDao.getUserById(c.getUserReference()).getName();
                    c.setUserName(compare);
                }
            }
            if(compare == null) {
                compare = userDao.getUserById(c.getUserReference()).getName();
                c.setUserName(compare);
                check = true;
            }
        }

        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomeFamilyReport(monthReport.getId());

        compare = null;
        check = false;
        for (CategoryIncomeReport c: incomeReports) {
            if(check) {
                if (compare.equals(userDao.getUserById(c.getUserReference()).getName())) {
                    c.setUserName("");
                } else {
                    compare = userDao.getUserById(c.getUserReference()).getName();
                    c.setUserName(compare);
                }
            }
            if(compare == null) {
                compare = userDao.getUserById(c.getUserReference()).getName();
                c.setUserName(compare);
                check = true;
            }
        }

        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public String convertToString(Path path) {
        String report = null;
        try {
            report = new String(Files.readAllBytes(path.getFileName()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new MonthReportException(e.getMessage());
        }
        return report;
    }

    private TwoValue<LocalDateTime> calculateDates(LocalDateTime date, BigInteger id, boolean isJob, boolean isFamily) {
        logger.debug("Date " + id + " id" + " isJob " + isJob + " isFamily " + isFamily);
        LocalDateTime dateTo;
        LocalDateTime dateFrom;
        MonthReport monthReport;

        if(isJob) {
            dateTo = date;
            dateFrom = DateUtils.addMonthsToDate(date, -1);
        } else if (date.getMonth().getValue() == LocalDateTime.now().getMonth().getValue()) {
            dateTo = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),0);
            dateFrom = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), 1, 0, 0);

            try {
                if(isFamily){
                    monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom, dateTo);
                } else {
                    monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
                }
            } catch (EmptyResultDataAccessException e) {
                if(isFamily) {
                    formMonthFamilyReportFromDb(id, dateFrom, dateTo);
                } else {
                    formMonthPersonalReportFromDb(id, dateFrom, dateTo);
                }
            }
        } else {
            dateFrom = date;
            dateTo = DateUtils.addMonthsToDate(date, 1);
        }
        logger.debug("DateFrom " + dateFrom + " dateTo" + dateTo);
        return new TwoValue<>(dateFrom, dateTo);
    }
}
