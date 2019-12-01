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
        return this.jdbcTemplate.queryForObject(GET_FAMILY_INCOME_AO, new Object[]{new BigDecimal(autoOperationId)}, new AutoOperationIncomeMapper());
        /*return jdbcTemplate.queryForObject(GET_FAMILY_AO, new Object[]{65, 66, autoOperationId, 68},
                new AutoOperationIncomeMapper());
        /*return jdbcTemplate.queryForObject(GET_FAMILY_AO, new Object[]{familyIncome_family_ref_attr_id_1,
        familyIncome_users_ref_attr_id_2, autoOperationId, familyIncome_day_of_month_attr_id_4},
                new AutoOperationIncomeMapper());*/
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(Integer autoOperationId) {
        return jdbcTemplate.queryForObject(GET_FAMILY_AO, new Object[]{familyExpense_family_ref_attr_id_1,
        familyExpense_users_ref_attr_id_2, autoOperationId, familyExpense_day_of_month_attr_id_4},
                new AutoOperationExpenseMapper());
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(Integer autoOperationId) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_AO, new Object[]{personalIncome_user_debit_acc_ref_attr_id_1,
        autoOperationId, personalIncome_day_of_month_attr_id_3}, new AutoOperationIncomeMapper());
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(Integer autoOperationId) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_AO, new Object[]{personalExpense_user_debit_acc_ref_attr_id_1,
        autoOperationId, personalExpense_day_of_month_attr_id_3}, new AutoOperationExpenseMapper());
    }

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome) {
        jdbcTemplate.update(CREATE_FAMILY_INCOME_AO, autoOperationIncome.getDayOfMonth(), autoOperationIncome.getAmount().toString(),
        autoOperationIncome.getCategoryIncome().toString(), autoOperationIncome.getUserId().toString(), 3);
        return autoOperationIncome;
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome) {
        jdbcTemplate.update(CREATE_PERSONAL_INCOME_AO, autoOperationIncome.getDayOfMonth(), autoOperationIncome.getAmount().toString(),
                new BigDecimal(autoOperationIncome.getCategoryIncome().getId()),
                autoOperationIncome.getUserId().toString());
        return autoOperationIncome;
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense) {
        jdbcTemplate.update(CREATE_FAMILY_EXPENSE_AO, autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount().toString(),
                autoOperationExpense.getCategoryExpense().toString(), autoOperationExpense.getId().toString(),
                autoOperationExpense.getUserId().toString());
        return autoOperationExpense;
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense) {
        jdbcTemplate.update(CREATE_PERSONAL_EXPENSE_AO, autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount().toString(),
                autoOperationExpense.getCategoryExpense().toString(), autoOperationExpense.getId().toString(),
                autoOperationExpense.getUserId().toString());
        return autoOperationExpense;
    }

    @Override
    public void deleteAutoOperation(Integer autoOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, autoOperationId);
        jdbcTemplate.update(DELETE_FROM_ATTRIBUTES, autoOperationId);
        jdbcTemplate.update(DELETE_FROM_OBJREFERENCE, autoOperationId);
    }

    @Override
    public Collection<AbstractAutoOperation> getAllTodayOperations(Integer debitAccountId) {
        Collection<AutoOperationIncome> allIncomes = jdbcTemplate.query(GET_ALL_TODAY_AO_INCOME, new Object[]{debitAccountId,
        debitAccountId}, new AutoOperationIncomeMapper());
        Collection<AutoOperationExpense> allExpenses = jdbcTemplate.query(GET_ALL_TODAY_AO_EXPENSE, new Object[]{debitAccountId,
        debitAccountId}, new AutoOperationExpenseMapper());
        Collection<AbstractAutoOperation> allOperations = new ArrayList<>(allIncomes);
        allOperations.addAll(allExpenses);
        return allOperations;
    }

}
