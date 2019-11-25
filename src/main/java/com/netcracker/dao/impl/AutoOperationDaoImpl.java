package com.netcracker.dao.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.CategoryExpense;

import java.math.BigInteger;

public class AutoOperationDaoImpl implements AutoOperationDao {

    @Override
    public void getFamilyIncomeAutoOperation(AutoOperationIncome familyIncome) {

    }

    @Override
    public void getFamilyExpenseAutoOperation(AutoOperationExpense familyExpense) {

    }

    @Override
    public void getPersonalIncomeAutoOperation(AutoOperationIncome personalIncome) {

    }

    @Override
    public void getPersonalExpenseAutoOperation(AutoOperationExpense personalExpense) {

    }

    @Override
    public void createFamilyIncomeAutoOperation(CategoryIncome income) {

    }

    @Override
    public void createPersonalIncomeAutoOperation(CategoryIncome income) {

    }

    @Override
    public void deleteFamilyIncomeAutoOperation(BigInteger autoOperationId) {

    }

    @Override
    public void deletePersonalIncomeAutoOperation(CategoryIncome income) {

    }

    @Override
    public void createPersonalExpenseAutoOperation(CategoryExpense expense) {

    }

    @Override
    public void createFamilyExpenseAutoOperation(CategoryExpense expense) {

    }


    @Override
    public void deleteFamilyIncExpenseOperation(BigInteger autoOperationId) {

    }

    @Override
    public void deletePersonalExpenseAutoOperation(BigInteger autoOperationId) {

    }
}
