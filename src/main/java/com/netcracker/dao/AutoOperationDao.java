package com.netcracker.dao;

import com.netcracker.models.CategoryExpense;
import com.netcracker.models.CategoryIncome;

import java.math.BigInteger;

public interface AutoOperationDao {
    void createFamilyIncomeAutoOperation(CategoryIncome income);

    void createPersonalIncomeAutoOperation(CategoryIncome income );

    void deleteFamilyIncomeAutoOperation(BigInteger autoOperationId);

    void deletePersonalIncomeAutoOperation(CategoryIncome income);

    void createFamilyExpenseAutoOperation(CategoryExpense expense);

    void createPersonalExpenseAutoOperation(CategoryExpense expense);

    void deleteFamilyIncExpenseOperation(BigInteger autoOperationId);

    void deletePersonalExpenseAutoOperation(BigInteger autoOperationId);

}
