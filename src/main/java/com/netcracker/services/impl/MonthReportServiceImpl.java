package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;

import com.netcracker.models.AbstractCategoryReport;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.MonthReport;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.utils.CreditUtils;
import com.netcracker.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

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

    @Override
    public void formMonthFamilyReportFromDb(BigInteger id, Date date) {
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



        LocalDate dateTo = CreditUtils.addMonthsToDate(DateUtils.dateToLocalDate(date), -1);
        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByFamilyAccountId(id, date,
                DateUtils.localDateToDate(dateTo)).getId();

        for (CategoryExpenseReport i : expenseReports) {
            monthReportDao.createCategoryExpenseFamilyReport(idOfRecentMonth, i.getUserReference(), i);
        }

        for (CategoryIncomeReport i : incomeReports) {
            monthReportDao.createCategoryIncomeFamilyReport(idOfRecentMonth, i.getUserReference(), i);
        }

    }

    @Override
    public void formMonthPersonalReportFromDb(BigInteger id, Date date) {

        logger.debug("Insert formFamilyReportFromDb " + id + " " + date );

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

        LocalDate dateTo = CreditUtils.addMonthsToDate(DateUtils.dateToLocalDate(date), -1);
        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByPersonalAccountId(id, date,
                DateUtils.localDateToDate(dateTo)).getId();

        for (CategoryExpenseReport i : expenseReports) {
            monthReportDao.createCategoryExpensePersonalReport(idOfRecentMonth, i);
        }

        for (CategoryIncomeReport i : incomeReports) {
            monthReportDao.createCategoryIncomePersonalReport(idOfRecentMonth, i);
        }
    }

    @Override
    public FileOutputStream convertToTxt(MonthReport monthReport) {

        logger.debug("Insert formFamilyReportFromDb " + monthReport);

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("report.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(monthReport);
            objectOut.close();
            System.out.println("The Object was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileOut;
    }
    @Override
    public MonthReport getMonthPersonalReport(BigInteger id, Date dateFrom, Date dateTo) {
        MonthReport monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpensePersonalReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomePersonalReport(monthReport.getId());
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public MonthReport getMonthFamilyReport(BigInteger id, Date dateFrom, Date dateTo) {
        MonthReport monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom, dateTo);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpenseFamilyReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomeFamilyReport(monthReport.getId());
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

}
