package com.netcracker.services;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;

public interface AccountAutoOperationService {
    AutoOperationIncome createFamilyIncomeAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                        int dayOfMonth, long amount, CategoryIncome categoryIncome);

    AutoOperationIncome createPersonalIncomeAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                          int dayOfMonth, long amount, CategoryIncome categoryIncome);

    void deleteFamilyIncomeAutoOperation(BigInteger autoOperationId);

    void deletePersonalIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense createFamilyExpenseAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                          int dayOfMonth, long amount, CategoryExpense categoryExpense);

    AutoOperationExpense createPersonalExpenseAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                            int dayOfMonth, long amount, CategoryExpense categoryExpense);

    void deleteFamilyExpenseAutoOperation(BigInteger autoOperationId);

    void deletePersonalExpenseAutoOperation(BigInteger autoOperationId);
}
