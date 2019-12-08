package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.impl.mapper.CategoryExpenseReportMapper;
import com.netcracker.dao.impl.mapper.CategoryIncomeReportMapper;
import com.netcracker.dao.impl.mapper.MonthReportMapper;
import com.netcracker.models.*;
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
    public void createCategoryIncomePersonalReport() {
        template.update(CREATE_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, new Object[]{});
    }

    @Override
    public void createCategoryIncomeFamilyReport() {
        template.update(CREATE_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new Object[]{});
    }

    @Override
    public void createCategoryExpensePersonalReport() {
        template.update(CREATE_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, new Object[]{});
    }

    @Override
    public void createCategoryExpenseFamilyReport() {
        template.update(CREATE_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new Object[]{});
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_PERSONAL_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryIncomeReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getCategoryIncomeFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_INCOME_FAMILY_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryIncomeReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpensePersonalReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_PERSONAL_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryExpenseReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getCategoryExpenseFamilyReport(BigInteger id) {
        return template.query(GET_CATEGORY_EXPENSE_FAMILY_REPORT_BY_ID, new Object[]{new BigDecimal(id)}, new CategoryExpenseReportMapper());
    }
}
