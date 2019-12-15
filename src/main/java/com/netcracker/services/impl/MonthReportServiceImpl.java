package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.exception.MonthReportException;
import com.netcracker.models.AbstractCategoryReport;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.MonthReport;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.utils.DateUtils;
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
    public void formMonthFamilyReportFromDb(BigInteger id, LocalDate date) {
        logger.debug("Insert formFamilyReportFromDb " + id + " " + date);

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesFamilyGroupByCategories(id, date);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesFamilyGroupByCategories(id, date);

        double totalIncome = incomeReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        double sum = familyAccountDebitDao.getFamilyAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense).build();

        monthReportDao.createFamilyMonthReport(monthReport, id);

        LocalDate dateTo = DateUtils.addMonthsToDate(date, -1);
        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByFamilyAccountId(id, date,
                dateTo).getId();

        for (CategoryExpenseReport i : expenseReports) {
            monthReportDao.createCategoryExpenseFamilyReport(idOfRecentMonth, i.getUserReference(), i);
        }

        for (CategoryIncomeReport i : incomeReports) {
            monthReportDao.createCategoryIncomeFamilyReport(idOfRecentMonth, i.getUserReference(), i);
        }

    }

    @Override
    public void formMonthPersonalReportFromDb(BigInteger id, LocalDate date) {
        logger.debug("Insert formPersonalReportFromDb " + id + " " + date);

        if (id == null) {
            logger.debug("Id is null");
            throw new IllegalArgumentException();
        }

        if (date == null) {
            logger.debug("Date if null");
            throw new IllegalArgumentException();
        }

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesPersonalGroupByCategories(id, date);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesPersonalGroupByCategories(id, date);

        double totalIncome = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        double sum = personalDebitAccountDao.getPersonalAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense).build();

        monthReportDao.createPersonalMonthReport(monthReport, id);

        LocalDate dateTo = DateUtils.addMonthsToDate(date, -1);
        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByPersonalAccountId(id, date,
                dateTo).getId();

        for (CategoryExpenseReport report : expenseReports) {
            monthReportDao.createCategoryExpensePersonalReport(idOfRecentMonth, report);
        }

        for (CategoryIncomeReport report : incomeReports) {
            monthReportDao.createCategoryIncomePersonalReport(idOfRecentMonth, report);
        }
    }

    @Override
    public Path convertToTxt(MonthReport monthReport) {
        logger.debug("Convertation " + monthReport);

        if (monthReport == null) {
            logger.debug("Undefined report");
            throw new MonthReportException("Incorrect month report");
        }

        Path path = Paths.get("report.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(MONTH_REPORT_FROM + monthReport.getDateFrom());
            writer.write(MONTH_REPORT_TO + monthReport.getDateTo() + NEW_LINE);
            writer.write(DOTTED_LINE);
            writer.write(ACTUAL_BALANCE + monthReport.getBalance() + TAB_AND_LINE);
            writer.write(TOTAL_EXPENSE + monthReport.getTotalExpense() + TAB_AND_LINE);
            writer.write(TOTAL_INCOME + monthReport.getTotalIncome() + TAB_AND_LINE);

            writer.write(EXPENSES_BY_CATEGORIES);
            writer.write(DOTTED_LINE);
            for (CategoryExpenseReport exp :
                    monthReport.getCategoryExpense()) {

                String name = userDao.getUserById(exp.getUserReference()).getName();
                if(name != null) {
                    writer.write(name + SPACE);
                }
                writer.write(exp.getCategoryExpense().toString() + DOUBLE_DOTS + exp.getAmount() + NEW_LINE);
            }

            writer.write(INCOMES_BY_CATEGORIES);
            writer.write(DOTTED_LINE);
            for (CategoryIncomeReport inc :
                    monthReport.getCategoryIncome()) {
                String name = userDao.getUserById(inc.getUserReference()).getName();
                if(name != null) {
                    writer.write(name + SPACE);
                }
                writer.write(inc.getCategoryIncome().toString() + DOUBLE_DOTS + inc.getAmount() + NEW_LINE);
            }
        } catch (IOException e) {
            logger.error("Error in writing to file", e);
        }
        return path;
    }


    @Override
    public MonthReport getMonthPersonalReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo) {
        logger.debug("Id " + id + " dateFrom " + dateFrom + " dateTo " + dateTo);

        MonthReport monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpensePersonalReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomePersonalReport(monthReport.getId());
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public MonthReport getMonthFamilyReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo) {
        MonthReport monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom, dateTo);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpenseFamilyReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomeFamilyReport(monthReport.getId());
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

}
