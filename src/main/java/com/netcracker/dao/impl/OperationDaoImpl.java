package com.netcracker.dao.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.dao.impl.mapper.*;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
@Component
public class OperationDaoImpl implements OperationDao {

    protected JdbcTemplate template;

    @Autowired
    public OperationDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createIncomePersonalByAccId(BigInteger id, BigDecimal income, Date date,  CategoryIncome categoryIncome) {
        template.update(ADD_INCOME_PERSONAL_BY_ACCOUNT_ID, new Object[]{new BigDecimal(id), income.toString(), date, new BigDecimal(categoryIncome.getId())});
    }

    @Override
    public void createExpensePersonaByAccId(BigInteger id, BigDecimal expense, Date date, CategoryExpense categoryExpense) {
        template.update(ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID, new Object[]{new BigDecimal(id), expense.toString(), date, new BigDecimal(categoryExpense.getId()) });
    }

    @Override
    public void createIncomeFamilyByAccId(BigInteger idUser, BigInteger idFamily, BigDecimal income, Date date,CategoryIncome categoryIncome) {
        template.update(ADD_INCOME_FAMILY_BY_ACCOUNT_ID, new Object[]{new BigDecimal(idUser), new BigDecimal(idFamily), income.toString(),date, new BigDecimal(categoryIncome.getId()) });
    }

    @Override
    public void createExpenseFamilyByAccId(BigInteger idUser, BigInteger idFamily, BigDecimal expense, Date date, CategoryExpense categoryExpense ) {
        template.update(ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID, new Object[]{ new BigDecimal(idUser), new BigDecimal(idFamily) , expense.toString(), date, new BigDecimal(categoryExpense.getId())});
    }

    @Override
    public Collection<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, Date date) {
        return template.query(GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{new BigDecimal(id), date}, new AccountIncomeMapper());
    }

    @Override
    public Collection<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, Date date) {
        return template.query(GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{new BigDecimal(id), date}, new AccountExpenseMapper());
    }

    @Override
    public Collection<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, Date date) {
        return template.query(GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{new BigDecimal(id), date}, new AccountIncomeMapper());
    }

    @Override
    public Collection<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, Date date) {
        return template.query(GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{new BigDecimal(id), date}, new AccountExpenseMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesPersonalGroupByCategories(BigInteger id, Date date) {
        return template.query(GET_EXPENSES_PERSONAL_GROUP_BY_CATEGORIES, new Object[]{new BigDecimal(id), date}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesPersonalGroupByCategories(BigInteger id, Date date) {
        return template.query(GET_INCOMES_PERSONAL_GROUP_BY_CATEGORIES, new Object[]{new BigDecimal(id), date}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesFamilyGroupByCategories(BigInteger id, Date date) {
        return template.query(GET_EXPENSES_FAMILY_GROUP_BY_CATEGORIES, new Object[]{new BigDecimal(id), date}, new CategoryExpenseFamilyReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesFamilyGroupByCategories(BigInteger id, Date date) {
        return template.query(GET_INCOMES_FAMILY_GROUP_BY_CATEGORIES, new Object[]{new BigDecimal(id), date}, new CategoryIncomeFamilyReportMapper());
    }
}
