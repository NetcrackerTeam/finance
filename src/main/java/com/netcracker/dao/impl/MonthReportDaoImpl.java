package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.impl.mapper.*;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.MonthReport;
import com.netcracker.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDate;
import java.util.Collection;

@Repository
public class MonthReportDaoImpl implements MonthReportDao {

    private JdbcTemplate template;

    @Autowired
    public MonthReportDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createPersonalMonthReport(MonthReport monthReport, BigInteger id) {
        template.update(CREATE_PERSONAL_MONTH_REPORT,
                monthReport.getTotalIncome(),
                monthReport.getTotalExpense(),
                monthReport.getBalance(),
                DateUtils.localDateToDate(monthReport.getDateFrom()),
                DateUtils.localDateToDate(monthReport.getDateTo()),
                id);
    }

    @Override
    public void createFamilyMonthReport(MonthReport monthReport, BigInteger id) {
        template.update(CREATE_FAMILY_MONTH_REPORT,
                monthReport.getTotalIncome(),
                monthReport.getTotalExpense(),
                monthReport.getBalance(),
                DateUtils.localDateToDate(monthReport.getDateFrom()),
                DateUtils.localDateToDate(monthReport.getDateTo()),
                id);
    }



    @Override
    public MonthReport getMonthReportByFamilyAccountId(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return template.queryForObject(GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID,
                new Object[]{id, DateUtils.localDateToDate(dateFrom), DateUtils.localDateToDate(dateTo) }, new MonthReportMapper());
    }

    @Override
    public MonthReport getMonthReportByPersonalAccountId(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return template.queryForObject(GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID,
                new Object[]{id, DateUtils.localDateToDate(dateFrom), DateUtils.localDateToDate(dateTo)}, new MonthReportMapper());
    }

    @Override
    public void createCategoryIncomePersonalReport(BigInteger id, CategoryIncomeReport categoryIncomeReport) {
        template.update(CREATE_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, id, categoryIncomeReport.getCategoryIncome().getId(),
                categoryIncomeReport.getAmount());
    }

    @Override
    public void createCategoryIncomeFamilyReport(BigInteger idReport, BigInteger idUser, CategoryIncomeReport categoryIncomeReport) {
        template.update(CREATE_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, idReport, idUser, categoryIncomeReport.getCategoryIncome().getId(),
                categoryIncomeReport.getAmount());
    }

    @Override
    public void createCategoryExpensePersonalReport(BigInteger id, CategoryExpenseReport categoryExpenseReport) {
        template.update(CREATE_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, id, categoryExpenseReport.getCategoryExpense().getId(),
                categoryExpenseReport.getAmount());
    }

    @Override
    public void createCategoryExpenseFamilyReport(BigInteger idReport, BigInteger idUser, CategoryExpenseReport categoryExpenseReport) {
        template.update(CREATE_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, idReport, idUser,
        categoryExpenseReport.getCategoryExpense().getId(), categoryExpenseReport.getAmount());
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, new Object[]{id}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomeFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new Object[]{id}, new CategoryIncomeFamilyReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpensePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, new Object[]{id}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpenseFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new Object[]{id}, new CategoryExpenseFamilyReportMapper());
    }

    @Override
    public Collection<MonthReport> getAllPersonalReports(BigInteger id) {
        return template.query(GET_ALL_PERSONAL_REPORTS, new Object[]{id}, new MonthReportMapper());
    }

    @Override
    public Collection<MonthReport> getAllFamilyReports(BigInteger id) {
        return template.query(GET_ALL_FAMILY_REPORTS, new Object[]{id}, new MonthReportMapper());
    }
}
