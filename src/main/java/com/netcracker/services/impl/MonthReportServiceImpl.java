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

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
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
        logger.debug("Insert formFamilyReportFromDb " + id + " " + date );

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesFamilyGroupByCategories(id, date);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesFamilyGroupByCategories(id, date);

        long totalIncome = incomeReports.stream().mapToLong(AbstractCategoryReport::getAmount).sum();
        long totalExpense = expenseReports.stream().mapToLong(AbstractCategoryReport::getAmount).sum();

        long sum = familyAccountDebitDao.getFamilyAccountById(id).getAmount();


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
        logger.debug("Insert formPersonalReportFromDb " + id + " " + date );

        if(id == null){
            logger.debug("Id is null");
            throw new IllegalArgumentException();
        }

        if(date == null) {
            logger.debug("Date if null");
            throw new IllegalArgumentException();
        }

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesPersonalGroupByCategories(id, date);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesPersonalGroupByCategories(id, date);

        Long totalIncome = expenseReports.stream().mapToLong(AbstractCategoryReport::getAmount).sum();
        Long totalExpense = expenseReports.stream().mapToLong(AbstractCategoryReport::getAmount).sum();

        long sum = personalDebitAccountDao.getPersonalAccountById(id).getAmount();

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
    public FileOutputStream convertFamilyToTxt(MonthReport monthReport) {
        logger.debug("Convertation " + monthReport);

        if(monthReport == null) {
            logger.debug("Undefined report");
            throw new MonthReportException("Uncorrect monthreport");
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("report.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(monthReport.getBalance());
            objectOut.writeObject(monthReport.getTotalExpense());
            objectOut.writeObject(monthReport.getTotalIncome());
            objectOut.writeObject(monthReport.getDateFrom());
            objectOut.writeObject(monthReport.getDateTo());

            for (CategoryIncomeReport report: monthReport.getCategoryIncome()) {
                objectOut.writeObject(userDao.getUserById(report.getUserReference()).getName());
                objectOut.writeObject(report.getCategoryIncome());
                objectOut.writeObject(report.getAmount());
            }

            for (CategoryExpenseReport report: monthReport.getCategoryExpense()) {
                objectOut.writeObject(userDao.getUserById(report.getUserReference()).getName());
                objectOut.writeObject(report.getCategoryExpense());
                objectOut.writeObject(report.getAmount());
            }

            objectOut.close();
            logger.debug("The Object was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileOut;
    }

    @Override
    public FileOutputStream convertPersonalToTxt(MonthReport monthReport) {

        logger.debug("Convertation " + monthReport);

        if(monthReport == null) {
            logger.debug("Undefined report");
            throw new MonthReportException("Uncorrect monthreport");
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("report.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(monthReport.getBalance());
            objectOut.writeObject(monthReport.getTotalExpense());
            objectOut.writeObject(monthReport.getTotalIncome());
            objectOut.writeObject(monthReport.getDateFrom());
            objectOut.writeObject(monthReport.getDateTo());

            for (CategoryIncomeReport report: monthReport.getCategoryIncome()) {
                objectOut.writeObject(report.getCategoryIncome());
                objectOut.writeObject(report.getAmount());
            }

            for (CategoryExpenseReport report: monthReport.getCategoryExpense()) {
                objectOut.writeObject(report.getCategoryExpense());
                objectOut.writeObject(report.getAmount());
            }

            objectOut.close();
            logger.debug("The Object was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileOut;
    }
    @Override
    public MonthReport getMonthPersonalReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo) {
        logger.debug("Id " + id + " dateFrom " + dateFrom + " dateTo " + dateTo );

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
