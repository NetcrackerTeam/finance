package com.netcracker.dao.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;

public class AutoOperationDaoImpl implements AutoOperationDao {

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
