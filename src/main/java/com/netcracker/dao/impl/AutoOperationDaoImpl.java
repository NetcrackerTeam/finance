package com.netcracker.dao.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.impl.mapper.AutoOperationExpenseMapper;
import com.netcracker.dao.impl.mapper.AutoOperationIncomeMapper;
import com.netcracker.dao.utils.ObjectsCreator;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
                        familyIncome_users_ref_attr_id_2, autoOperationId, familyIncome_day_of_month_attr_id_4,
                        income_amount_attr_id_5, income_category_attr_id_6, income_dates_attr_id_7},
                new AutoOperationIncomeMapper());
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_FAMILY_AO, new Object[]{familyExpense_family_ref_attr_id_1,
                        familyExpense_users_ref_attr_id_2, autoOperationId, familyExpense_day_of_month_attr_id_4,
                        expense_amount_attr_id_5, expense_category_attr_id_6, expense_dates_attr_id_7},
                new AutoOperationExpenseMapper());
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_AO, new Object[]{personalIncome_user_debit_acc_ref_attr_id_1,
                autoOperationId, personalIncome_day_of_month_attr_id_3, income_amount_attr_id_5,
                income_category_attr_id_6, income_dates_attr_id_7}, new AutoOperationIncomeMapper());
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_AO, new Object[]{personalExpense_user_debit_acc_ref_attr_id_1,
                autoOperationId, personalExpense_day_of_month_attr_id_3, expense_amount_attr_id_5,
                expense_category_attr_id_6, expense_dates_attr_id_7}, new AutoOperationExpenseMapper());
    }

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId,
                                                               BigInteger familyDebitAccountId) {
        BigInteger objectId = ObjectsCreator.createObject(familyIncome_object_type_id_1, familyIncome_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);

        jdbcTemplate.update(CREATE_FAMILY_INCOME_AO, objectId, autoOperationIncome.getDayOfMonth(), objectId, autoOperationIncome.getAmount(), objectId,
                autoOperationIncome.getCategoryIncome().getId(), objectId, objectId, familyDebitAccountId, objectId, userId);

        return getFamilyIncomeAutoOperation(objectId);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger personalDebitAccountId) {
        BigInteger objectId = ObjectsCreator.createObject(personalIncome_object_type_id_1, personalIncome_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);

        jdbcTemplate.update(CREATE_PERSONAL_INCOME_AO, objectId, autoOperationIncome.getDayOfMonth(), objectId,
                autoOperationIncome.getAmount(), objectId,
                autoOperationIncome.getCategoryIncome().getId(), objectId, objectId, personalDebitAccountId);

        return getPersonalIncomeAutoOperation(objectId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId,
                                                                 BigInteger familyDebitAccountId) {
        BigInteger objectId = ObjectsCreator.createObject(familyExpense_object_type_id_1, familyExpense_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);

        jdbcTemplate.update(CREATE_FAMILY_EXPENSE_AO, objectId, autoOperationExpense.getDayOfMonth(), objectId,
                autoOperationExpense.getAmount(), objectId,
                autoOperationExpense.getCategoryExpense().getId(), objectId, objectId, familyDebitAccountId, objectId, userId);

        return getFamilyExpenseAutoOperation(objectId);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense,
                                                                   BigInteger personalDebitAccountId) {
        BigInteger objectId = ObjectsCreator.createObject(personalExpense_object_type_id_1, personalExpense_name_2,
                jdbcTemplate, CREATE_OBJECT_AUTO_OPERATION);

        jdbcTemplate.update(CREATE_PERSONAL_EXPENSE_AO, objectId, autoOperationExpense.getDayOfMonth(), objectId,
                autoOperationExpense.getAmount(), objectId, autoOperationExpense.getCategoryExpense().getId(), objectId,
                objectId, personalDebitAccountId);

        return getPersonalExpenseAutoOperation(objectId);
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        jdbcTemplate.update(DELETE_FROM_OBJECTS, autoOperationId);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsPersonal(BigInteger debitAccountId, int dayOfMonth) {
        return getAllTodayOperations(debitAccountId, dayOfMonth, GET_ALL_TODAY_AO_INCOME_PERSONAL, GET_ALL_TODAY_AO_EXPENSE_PERSONAL);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth) {
        return getAllTodayOperations(debitAccountId, dayOfMonth, GET_ALL_TODAY_AO_INCOME_FAMILY, GET_ALL_TODAY_AO_EXPENSE_FAMILY);
    }

    private List<AbstractAutoOperation> getAllTodayOperations(BigInteger debitAccountId, int dayOfMonth,
                                                                    String queryForIncome, String queryForExpense) {
        List<AutoOperationIncome> allIncomes = jdbcTemplate.query(queryForIncome,
                new Object[]{debitAccountId, dayOfMonth}, new AutoOperationIncomeMapper());
        List<AutoOperationExpense> allExpenses = jdbcTemplate.query(queryForExpense,
                new Object[]{debitAccountId, dayOfMonth}, new AutoOperationExpenseMapper());
        List<AbstractAutoOperation> allOperations = new ArrayList<>(allIncomes);
        allOperations.addAll(allExpenses);
        return allOperations;
    }
}
