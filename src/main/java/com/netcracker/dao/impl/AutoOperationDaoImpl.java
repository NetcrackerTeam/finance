package com.netcracker.dao.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.impl.mapper.AutoOperationExpenseMapper;
import com.netcracker.dao.impl.mapper.AutoOperationIncomeMapper;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class AutoOperationDaoImpl implements AutoOperationDao {
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public AutoOperationDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_FAMILY_AO, new Object[]{familyIncome_family_ref_attr_id_1,
        familyIncome_users_ref_attr_id_2, new BigDecimal(autoOperationId), familyIncome_day_of_month_attr_id_4,
                income_amount_attr_id_5, income_category_attr_id_6, income_dates_attr_id_7},
                new AutoOperationIncomeMapper());
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_FAMILY_AO, new Object[]{familyExpense_family_ref_attr_id_1,
        familyExpense_users_ref_attr_id_2, new BigDecimal(autoOperationId), familyExpense_day_of_month_attr_id_4,
                expense_amount_attr_id_5, expense_category_attr_id_6, expense_dates_attr_id_7},
                new AutoOperationExpenseMapper());
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_AO, new Object[]{personalIncome_user_debit_acc_ref_attr_id_1,
                new BigDecimal(autoOperationId), personalIncome_day_of_month_attr_id_3, income_amount_attr_id_5,
        income_category_attr_id_6, income_dates_attr_id_7}, new AutoOperationIncomeMapper());
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_AO, new Object[]{personalExpense_user_debit_acc_ref_attr_id_1,
                new BigDecimal(autoOperationId), personalExpense_day_of_month_attr_id_3, expense_amount_attr_id_5,
        expense_category_attr_id_6, expense_dates_attr_id_7}, new AutoOperationExpenseMapper());
    }

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome,
                                                               BigInteger familyDebitAccountId) {
        jdbcTemplate.update(CREATE_FAMILY_INCOME_AO, autoOperationIncome.getDayOfMonth(),
                autoOperationIncome.getAmount().toString(), new BigDecimal(autoOperationIncome.getCategoryIncome().getId()),
                new BigDecimal(familyDebitAccountId), autoOperationIncome.getUserId().toString());
        return autoOperationIncome;
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome) {
        jdbcTemplate.update(CREATE_PERSONAL_INCOME_AO, autoOperationIncome.getDayOfMonth(),
                autoOperationIncome.getAmount().toString(), new BigDecimal(autoOperationIncome.getCategoryIncome().getId()),
                autoOperationIncome.getUserId().toString());
        return autoOperationIncome;
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense,
                                                                 BigInteger familyDebitAccountId) {
        jdbcTemplate.update(CREATE_FAMILY_EXPENSE_AO, autoOperationExpense.getDayOfMonth(),
                autoOperationExpense.getAmount().toString(), new BigDecimal(autoOperationExpense.getCategoryExpense().getId()),
                new BigDecimal(familyDebitAccountId), autoOperationExpense.getUserId().toString());
        return autoOperationExpense;
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense) {
        jdbcTemplate.update(CREATE_PERSONAL_EXPENSE_AO, autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount().toString(),
                new BigDecimal(autoOperationExpense.getCategoryExpense().getId()), autoOperationExpense.getUserId().toString());
        return autoOperationExpense;
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, new BigDecimal(autoOperationId));
        jdbcTemplate.update(DELETE_FROM_ATTRIBUTES, new BigDecimal(autoOperationId));
        jdbcTemplate.update(DELETE_FROM_OBJREFERENCE, new BigDecimal(autoOperationId));
    }

    @Override
    public Collection<AbstractAutoOperation> getAllTodayOperations(BigInteger debitAccountId) {
        Collection<AutoOperationIncome> allIncomes = jdbcTemplate.query(GET_ALL_TODAY_AO_INCOME,
                new Object[]{new BigDecimal(debitAccountId)}, new AutoOperationIncomeMapper());
        Collection<AutoOperationExpense> allExpenses = jdbcTemplate.query(GET_ALL_TODAY_AO_EXPENSE,
                new Object[]{new BigDecimal(debitAccountId)}, new AutoOperationExpenseMapper());
        Collection<AbstractAutoOperation> allOperations = new ArrayList<>(allIncomes);
        allOperations.addAll(allExpenses);
        return allOperations;
    }

}
