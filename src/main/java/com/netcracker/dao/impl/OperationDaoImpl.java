package com.netcracker.dao.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.dao.impl.mapper.AccountExpenseMapper;
import com.netcracker.dao.impl.mapper.AccountIncomeMapper;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OperationDaoImpl implements OperationDao {

    protected JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void addIncomePersonalByAccId(BigInteger id, int income) {
        template.update(ADD_INCOME_PERSONAL_BY_ACCOUNT_ID, new Object[]{id, income});
    }

    @Override
    public void addExpensePersonaByAccId(BigInteger id, int expense) {
        template.update(ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID, new Object[]{id, expense});
    }

    @Override
    public void addIncomeFamilyByAccId(BigInteger id, int income) {
        template.update(ADD_INCOME_FAMILY_BY_ACCOUNT_ID, new Object[]{id, income});
    }

    @Override
    public void addExpenseFamilyByAccId(BigInteger id, int expense) {
        template.update(ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID, new Object[]{id, expense});
    }

    @Override
    public List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, Date date) {
        return template.query(GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, date}, new AccountIncomeMapper());
    }

    @Override
    public List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, Date date) {
        return template.query(GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, date}, new AccountExpenseMapper());
    }

    @Override
    public List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, Date data) {
        return template.query(GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, data}, new AccountIncomeMapper());
    }

    @Override
    public List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, Date data) {
        return template.query(GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID,
                new Object[]{id, data}, new AccountExpenseMapper());
    }


}
