package com.netcracker.dao.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

import java.util.Date;

public class OperationDaoImpl implements OperationDao {

    protected JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void addIncomePersonalByAccId(BigInteger id, int income) {
//        return template.execute(ADD_INCOME_PERSONAL_BY_ACCOUNT_ID, new Object[]{id, income});
    }

    @Override
    public void addExpensePersonaByAccId(BigInteger id, int expense) {

    }

    @Override
    public void addIncomeFamilyByAccId(BigInteger id, int income) {

    }

    @Override
    public void addExpenseFamilyByAccId(BigInteger id, int expense) {

    }

    @Override
    public void getPersonalIncomeOperationsByIdDate(BigInteger id, Date date) {

    }

    @Override
    public void getPersonalExpenseOperationsByIdDate(BigInteger id, Date date) {

    }

    @Override
    public void getFamilyIncomeOperationsByIdDate(BigInteger id, Date data) {

    }

    @Override
    public void getFamilyExpenseOperationsByIdDate(BigInteger id, Date data) {

    }

    @Override
    public CategoryIncome getCategoriesIncome() {
        return null;
    }

    @Override
    public CategoryExpense getCategoriesExpense() {
        return null;
    }
}
