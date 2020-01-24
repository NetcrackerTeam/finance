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
import java.time.LocalDateTime;
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
    public void createIncomePersonalByAccId(BigInteger id, double income, LocalDateTime date, CategoryIncome categoryIncome) {
        template.update(ADD_INCOME_PERSONAL_BY_ACCOUNT_ID, id, income, date, categoryIncome.getId());
    }

    @Override
    public void createExpensePersonaByAccId(BigInteger id, double expense, LocalDateTime date, CategoryExpense categoryExpense) {
        template.update(ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID, id, expense, date, categoryExpense.getId());
    }

    @Override
    public void createIncomeFamilyByAccId(BigInteger idUser, BigInteger idFamily, double income, LocalDateTime date, CategoryIncome categoryIncome) {
        template.update(ADD_INCOME_FAMILY_BY_ACCOUNT_ID, idUser, idFamily, income, date, categoryIncome.getId());
    }

    @Override
    public void createExpenseFamilyByAccId(BigInteger idUser, BigInteger idFamily, double expense, LocalDateTime date, CategoryExpense categoryExpense) {
        template.update(ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID, idUser, idFamily, expense, date, categoryExpense.getId());
    }

    @Override
    public List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, LocalDateTime date) {
        return template.query(GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, date}, new AccountIncomeMapper());
    }

    @Override
    public List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, LocalDateTime date) {
        return template.query(GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, date}, new AccountExpenseMapper());
    }

    @Override
    public List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, LocalDateTime date) {
        return template.query(GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, date}, new AccountIncomeMapper());
    }

    @Override
    public List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, LocalDateTime date) {
        return template.query(GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, date}, new AccountExpenseMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesPersonalGroupByCategories(BigInteger id, LocalDateTime date) {
        return template.query(GET_EXPENSES_PERSONAL_GROUP_BY_CATEGORIES, new Object[]{id, date}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesPersonalGroupByCategories(BigInteger id, LocalDateTime date) {
        return template.query(GET_INCOMES_PERSONAL_GROUP_BY_CATEGORIES, new Object[]{id, date}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesFamilyGroupByCategories(BigInteger id, LocalDateTime date) {
        return template.query(GET_EXPENSES_FAMILY_GROUP_BY_CATEGORIES, new Object[]{id, date}, new CategoryExpenseFamilyReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesFamilyGroupByCategories(BigInteger id, LocalDateTime date) {
        return template.query(GET_INCOMES_FAMILY_GROUP_BY_CATEGORIES, new Object[]{id, date}, new CategoryIncomeFamilyReportMapper());
    }

    @Override
    public Collection<CategoryExpenseReport> getExpensesFamilyGroupByCategoriesWithoutUser(BigInteger id, LocalDateTime date) {
        return template.query(GET_EXPENSES_FAMILY_GROUP_BY_CATEGORIES_WITHOUT_USER, new Object[]{id, date}, new CategoryExpensePersonalReportMapper());
    }

    @Override
    public Collection<CategoryIncomeReport> getIncomesFamilyGroupByCategoriesWithoutUser(BigInteger id, LocalDateTime date) {
        return template.query(GET_INCOMES_FAMILY_GROUP_BY_CATEGORIES_WITHOUT_USER, new Object[]{id, date}, new CategoryIncomePersonalReportMapper());
    }

    @Override
    public List<HistoryOperation> getHistoryByAccountId(BigInteger id, int period) {
        return template.query(GET_PERSONAL_FOR_DATE_BY_ACCOUNT_ID, new Object[]{id, period}, new PersonalHistoryOperationMapper());
    }

    @Override
    public List<HistoryOperation> getFamilyHistoryByAccountId(BigInteger id, int period){
        return template.query(GET_FAMILY_FOR_DATE_BY_ACCOUNT_ID, new Object[]{id}, new HistoryOperationMapper());
    }

    @Override
    public List<HistoryOperation> getFirstPersonalHistoryByAccountId(BigInteger id) {
        return template.query(GET_FIRST_15_PERSONAL_HISTORY_BY_ACCOUNT_ID, new Object[]{id}, new PersonalHistoryOperationMapper());
    }

    @Override
    public List<HistoryOperation> getFirstFamilyHistoryByAccountId(BigInteger id){
        return template.query(GET_FIRST_15_FAMILY_HISTORY_BY_ACCOUNT_ID, new Object[]{id}, new HistoryOperationMapper());
    }

}
