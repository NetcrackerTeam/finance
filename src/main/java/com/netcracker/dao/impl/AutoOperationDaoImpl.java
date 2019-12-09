package com.netcracker.dao.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.impl.mapper.AutoOperationExpenseMapper;
import com.netcracker.dao.impl.mapper.AutoOperationIncomeMapper;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.utils.ObjectsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class AutoOperationDaoImpl implements AutoOperationDao {
    private JdbcTemplate jdbcTemplate;

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
    public AutoOperationIncome createFamilyIncomeAutoOperation(int dayOfMonth, long amount, CategoryIncome categoryIncome,
                                                               BigInteger userId, BigInteger familyDebitAccountId) {
        BigInteger objectIdInteger = ObjectsCreator.createObject(familyIncome_object_type_id_1, familyIncome_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);
        BigDecimal objectId = new BigDecimal(objectIdInteger);

        jdbcTemplate.update(CREATE_FAMILY_INCOME_AO, objectId, dayOfMonth, objectId, amount, objectId,
                new BigDecimal(categoryIncome.getId()), objectId, objectId, new BigDecimal(familyDebitAccountId),
                objectId, new BigDecimal(userId));

        return getFamilyIncomeAutoOperation(objectIdInteger);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(int dayOfMonth, long amount, CategoryIncome categoryIncome,
                                                                 BigInteger userId, BigInteger personalDebitAccountId) {
        BigInteger objectIdInteger = ObjectsCreator.createObject(personalIncome_object_type_id_1, personalIncome_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);
        BigDecimal objectId = new BigDecimal(objectIdInteger);

        jdbcTemplate.update(CREATE_PERSONAL_INCOME_AO, objectId, dayOfMonth, objectId, amount, objectId,
                new BigDecimal(categoryIncome.getId()), objectId, objectId, new BigDecimal(personalDebitAccountId));

        return getPersonalIncomeAutoOperation(objectIdInteger);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(int dayOfMonth, long amount, CategoryExpense categoryExpense,
                                                                 BigInteger userId, BigInteger familyDebitAccountId) {
        BigInteger objectIdInteger = ObjectsCreator.createObject(familyExpense_object_type_id_1, familyExpense_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);
        BigDecimal objectId = new BigDecimal(objectIdInteger);

        jdbcTemplate.update(CREATE_FAMILY_EXPENSE_AO, objectId, dayOfMonth, objectId, amount, objectId,
                new BigDecimal(categoryExpense.getId()), objectId, objectId, new BigDecimal(familyDebitAccountId),
                objectId, new BigDecimal(userId));

        return getFamilyExpenseAutoOperation(objectIdInteger);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(int dayOfMonth, long amount, CategoryExpense categoryExpense,
                                                                   BigInteger userId, BigInteger personalDebitAccountId) {
        BigInteger objectIdInteger = ObjectsCreator.createObject(personalExpense_object_type_id_1, personalExpense_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);
        BigDecimal objectId = new BigDecimal(objectIdInteger);

        jdbcTemplate.update(CREATE_PERSONAL_EXPENSE_AO, objectId, dayOfMonth, objectId, amount, objectId,
                new BigDecimal(categoryExpense.getId()), objectId, objectId, new BigDecimal(personalDebitAccountId));

        return  getPersonalExpenseAutoOperation(objectIdInteger);
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, new BigDecimal(autoOperationId));
    }

    @Override
    public Collection<AbstractAutoOperation> getAllTodayOperations(BigInteger debitAccountId, int dayOfMonth) {
        Collection<AutoOperationIncome> allIncomes = jdbcTemplate.query(GET_ALL_TODAY_AO_INCOME,
                new Object[]{new BigDecimal(debitAccountId), dayOfMonth}, new AutoOperationIncomeMapper());
        Collection<AutoOperationExpense> allExpenses = jdbcTemplate.query(GET_ALL_TODAY_AO_EXPENSE,
                new Object[]{new BigDecimal(debitAccountId), dayOfMonth}, new AutoOperationExpenseMapper());
        Collection<AbstractAutoOperation> allOperations = new ArrayList<>(allIncomes);
        allOperations.addAll(allExpenses);
        return allOperations;
    }

}
