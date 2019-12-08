package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.impl.mapper.CategoryExpensePersonalReportMapper;
import com.netcracker.dao.impl.mapper.CategoryIncomePersonalReportMapper;
import com.netcracker.dao.impl.mapper.MonthReportMapper;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
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
        template.update(CREATE_PERSONAL_MONTH_REPORT, new Object[]{
                String.valueOf(monthReport.getTotalExpense()),
                String.valueOf(monthReport.getTotalIncome()),
                String.valueOf(monthReport.getBalance()),
                Date.valueOf(monthReport.getDate_from()),
                Date.valueOf(monthReport.getDate_to()),
                new BigDecimal(id)
        });
    }

    @Override
    public void createFamilyMonthReport(MonthReport monthReport, BigInteger id) {
        template.update(CREATE_FAMILY_MONTH_REPORT, new Object[]{
                String.valueOf(monthReport.getTotalIncome()),
                String.valueOf(monthReport.getTotalExpense()),
                String.valueOf(monthReport.getBalance()),
                Date.valueOf(monthReport.getDate_from()),
                Date.valueOf(monthReport.getDate_to()),
                new BigDecimal(id)
        });
    }



    @Override
    public Collection<MonthReport> getMonthReportsByFamilyAccountId(BigInteger id) {
        return template.query(GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID,
                new Object[]{new BigDecimal(id)}, new MonthReportMapper());
    }

    @Override
    public Collection<MonthReport> getMonthReportsByPersonalAccountId(BigInteger id) {
        return template.query(GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID,
                new Object[]{new BigDecimal(id)}, new MonthReportMapper());
    }

    @Override
    public void createCategoryIncomePersonalReport(BigInteger id, CategoryIncomeReport categoryIncomeReport) {
        template.update(CREATE_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, new BigDecimal(id), categoryIncomeReport.getCategoryIncome().getId(),
                categoryIncomeReport.getAmount());
    }

    @Override
    public void createCategoryIncomeFamilyReport(BigInteger idReport, BigInteger idUser, CategoryIncomeReport categoryIncomeReport) {
        template.update(CREATE_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new BigDecimal(idReport), new BigDecimal(idUser), categoryIncomeReport.getCategoryIncome(),
                categoryIncomeReport.getAmount(), new BigDecimal(categoryIncomeReport.getUserReference()));
    }

    @Override
    public void createCategoryExpensePersonalReport(BigInteger id, CategoryExpenseReport categoryExpenseReport) {
        template.update(CREATE_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, new BigDecimal(id), categoryExpenseReport.getCategoryExpense(),
                categoryExpenseReport.getAmount(), new BigDecimal(categoryExpenseReport.getUserReference()));
    }

    @Override
    public void createCategoryExpenseFamilyReport(BigInteger idReport, BigInteger idUser, CategoryExpenseReport categoryExpenseReport) {
        template.update(CREATE_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new Object[]{new BigDecimal(idReport), new BigDecimal(idUser),
        new BigDecimal(categoryExpenseReport.getCategoryExpense().getId()), categoryExpenseReport.getAmount()});
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomeFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpensePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpenseFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryExpensePersonalReportMapper());
    }
}
