package com.netcracker.dao.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.dao.impl.mapper.*;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
public class OperationDaoImpl implements OperationDao {

    protected JdbcTemplate template;

    @Autowired
    public OperationDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createIncomePersonalByAccId(BigInteger id, double income, LocalDate date, CategoryIncome categoryIncome) {
        template.update(ADD_INCOME_PERSONAL_BY_ACCOUNT_ID, id, income, Date.valueOf(date), categoryIncome.getId());
    }

    @Override
    public void createExpensePersonaByAccId(BigInteger id, double expense, LocalDate date, CategoryExpense categoryExpense) {
        template.update(ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID, id, expense, Date.valueOf(date), categoryExpense.getId());
    }

    @Override
    public void createIncomeFamilyByAccId(BigInteger idUser, BigInteger idFamily, double income, LocalDate date, CategoryIncome categoryIncome) {
        template.update(ADD_INCOME_FAMILY_BY_ACCOUNT_ID, idUser, idFamily, income, Date.valueOf(date), categoryIncome.getId());
    }

    @Override
    public void createExpenseFamilyByAccId(BigInteger idUser, BigInteger idFamily, double expense, LocalDate date, CategoryExpense categoryExpense) {
        template.update(ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID, idUser, idFamily, expense, Date.valueOf(date), categoryExpense.getId());
    }

    @Override
    public List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, LocalDate date) {
        return template.query(GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, Date.valueOf(date)}, new AccountIncomeMapper());
    }

    @Override
    public List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, LocalDate date) {
        return template.query(GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, Date.valueOf(date)}, new AccountExpenseMapper());
    }

    @Override
    public List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, LocalDate date) {
        return template.query(GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, Date.valueOf(date)}, new AccountIncomeMapper());
    }

    @Override
    public List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, LocalDate date) {
        return template.query(GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, Date.valueOf(date)}, new AccountExpenseMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesPersonalGroupByCategories(BigInteger id, LocalDate date) {
        return template.query(GET_EXPENSES_PERSONAL_GROUP_BY_CATEGORIES, new Object[]{id, Date.valueOf(date)}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesPersonalGroupByCategories(BigInteger id, LocalDate date) {
        return template.query(GET_INCOMES_PERSONAL_GROUP_BY_CATEGORIES, new Object[]{id, Date.valueOf(date)}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesFamilyGroupByCategories(BigInteger id, LocalDate date) {
        return template.query(GET_EXPENSES_FAMILY_GROUP_BY_CATEGORIES, new Object[]{id, Date.valueOf(date)}, new CategoryExpenseFamilyReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesFamilyGroupByCategories(BigInteger id, LocalDate date) {
        return template.query(GET_INCOMES_FAMILY_GROUP_BY_CATEGORIES, new Object[]{id, Date.valueOf(date)}, new CategoryIncomeFamilyReportMapper());
    }

    @Override
    public List<HistoryOperation> getHistoryByAccountId(BigInteger id, int period) {
        return template.query(GET__PERSONAL_FOR_DATE_BY_ACCOUNT_ID, new Object[]{id, period, id, period}, new HistoryOperationMapper());
    }
}
