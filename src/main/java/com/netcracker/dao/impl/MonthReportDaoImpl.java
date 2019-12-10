package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.impl.mapper.CategoryExpensePersonalReportMapper;
import com.netcracker.dao.impl.mapper.CategoryIncomePersonalReportMapper;
import com.netcracker.dao.impl.mapper.MonthReportMapper;
import com.netcracker.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import java.util.Collection;

@Component
public class MonthReportDaoImpl implements MonthReportDao {

    protected JdbcTemplate template;

    @Autowired
    public MonthReportDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createPersonalMonthReport(MonthReport monthReport, BigInteger id) {
        template.update(CREATE_PERSONAL_MONTH_REPORT,
                String.valueOf(monthReport.getTotalIncome()),
                String.valueOf(monthReport.getTotalExpense()),
                String.valueOf(monthReport.getBalance()),
                monthReport.getDateFrom(),
                monthReport.getDateTo(),
                id);
    }

    @Override
    public void createFamilyMonthReport(MonthReport monthReport, BigInteger id) {
        template.update(CREATE_FAMILY_MONTH_REPORT,
                String.valueOf(monthReport.getTotalIncome()),
                String.valueOf(monthReport.getTotalExpense()),
                String.valueOf(monthReport.getBalance()),
                monthReport.getDateFrom(),
                monthReport.getDateTo(),
                id);
    }



    @Override
    public MonthReport getMonthReportByFamilyAccountId(BigInteger id, Date dateFrom, Date dateTo) {
        return template.queryForObject(GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID,
                new Object[]{id, dateFrom, dateTo }, new MonthReportMapper());
    }

    @Override
    public MonthReport getMonthReportByPersonalAccountId(BigInteger id, Date dateFrom, Date dateTo) {
        return template.queryForObject(GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID,
                new Object[]{id, dateFrom, dateTo}, new MonthReportMapper());
    }

    @Override
    public void createCategoryIncomePersonalReport(BigInteger id, CategoryIncomeReport categoryIncomeReport) {
        template.update(CREATE_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, id, categoryIncomeReport.getCategoryIncome().getId(),
                categoryIncomeReport.getAmount());
    }

    @Override
    public void createCategoryIncomeFamilyReport(BigInteger idReport, BigInteger idUser, CategoryIncomeReport categoryIncomeReport) {
        template.update(CREATE_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new BigDecimal(idReport), new BigDecimal(idUser), categoryIncomeReport.getCategoryIncome().getId(),
                categoryIncomeReport.getAmount(), new BigDecimal(categoryIncomeReport.getUserReference()));
    }

    @Override
    public void createCategoryExpensePersonalReport(BigInteger id, CategoryExpenseReport categoryExpenseReport) {
        template.update(CREATE_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, id, categoryExpenseReport.getCategoryExpense().getId(),
                categoryExpenseReport.getAmount(), new BigDecimal(categoryExpenseReport.getUserReference()));
    }

    @Override
    public void createCategoryExpenseFamilyReport(BigInteger idReport, BigInteger idUser, CategoryExpenseReport categoryExpenseReport) {
        template.update(CREATE_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new BigDecimal(idReport), new BigDecimal(idUser),
        new BigDecimal(categoryExpenseReport.getCategoryExpense().getId()), categoryExpenseReport.getAmount());
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, new Object[]{id}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomeFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new Object[]{id}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpensePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, new Object[]{id}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpenseFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new Object[]{id}, new CategoryExpensePersonalReportMapper());
    }
}
