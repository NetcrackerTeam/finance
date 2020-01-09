package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.exception.MonthReportException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.services.FamilyDebitService;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
    public void formMonthFamilyReportFromDb(BigInteger id) {
        logger.debug("Insert formFamilyReportFromDb " + id);

        ObjectsCheckUtils.isNotNull(id);

        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = DateUtils.addMonthsToDate(dateTo, -1);

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
    public void formMonthPersonalReportFromDb(BigInteger id) {
        logger.debug("Insert formPersonalReportFromDb " + id);

        ObjectsCheckUtils.isNotNull(id);

        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = DateUtils.addMonthsToDate(dateTo, -1);

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesPersonalGroupByCategories(id, dateFrom);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesPersonalGroupByCategories(id, dateFrom);

        double totalIncome = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        double sum = personalDebitAccountDao.getPersonalAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense).build();

        monthReportDao.createPersonalMonthReport(monthReport, id);

        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom,
                dateTo).getId();

        ObjectsCheckUtils.isNotNull(idOfRecentMonth);

        expenseReports.forEach(exp -> {
            monthReportDao.createCategoryExpensePersonalReport(idOfRecentMonth, exp);
        });
        incomeReports.forEach(inc -> {
            monthReportDao.createCategoryIncomePersonalReport(idOfRecentMonth, inc);
        });
    }

    @Override
    public Path convertToTxt(MonthReport monthReport) {
        logger.debug("Convertation " + monthReport);

        ObjectsCheckUtils.isNotNull(monthReport);

        Path path = Paths.get(monthReport.getDateFrom() + UNDERLINE + monthReport.getDateTo() + TXT_FORMAT);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(MONTH_REPORT_FROM + monthReport.getDateFrom());
            writer.write(MONTH_REPORT_TO + monthReport.getDateTo() + NEW_LINE);
            writer.write(DOTTED_LINE);
            writer.write(ACTUAL_BALANCE + monthReport.getBalance() + TAB_AND_LINE);
            writer.write(TOTAL_EXPENSE + monthReport.getTotalExpense() + TAB_AND_LINE);
            writer.write(TOTAL_INCOME + monthReport.getTotalIncome() + TAB_AND_LINE);

            writer.write(NEW_LINE + EXPENSES_BY_CATEGORIES);
            writer.write(DOTTED_LINE);

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

            writer.write(INCOMES_BY_CATEGORIES);
            writer.write(DOTTED_LINE);

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
    public MonthReport getMonthPersonalReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo) {
        logger.debug("Id " + id + " dateFrom " + dateFrom + " dateTo " + dateTo);

        ObjectsCheckUtils.isNotNull(id, dateFrom, dateTo);

        MonthReport monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
        ObjectsCheckUtils.isNotNull(monthReport);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpensePersonalReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomePersonalReport(monthReport.getId());
        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public MonthReport getMonthFamilyReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo) {
        logger.debug("Id " + id + " dateFrom " + dateFrom + " dateTo " + dateTo);

        ObjectsCheckUtils.isNotNull(id, dateFrom, dateTo);

        MonthReport monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom, dateTo);
        ObjectsCheckUtils.isNotNull(monthReport);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpenseFamilyReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomeFamilyReport(monthReport.getId());
        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

}
