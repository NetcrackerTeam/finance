package com.netcracker.services;

import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface AccountAutoOperationService {
    AutoOperationIncome createFamilyIncomeAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                        int dayOfMonth, long amount, CategoryIncome categoryIncome);

    AutoOperationIncome createPersonalIncomeAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                          int dayOfMonth, long amount, CategoryIncome categoryIncome);

    AutoOperationExpense createFamilyExpenseAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                          int dayOfMonth, long amount, CategoryExpense categoryExpense);

    AutoOperationExpense createPersonalExpenseAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                            int dayOfMonth, long amount, CategoryExpense categoryExpense);

    AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId);

    AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId);

    void deleteAutoOperation(BigInteger autoOperationId);

    List<AbstractAutoOperation> getAllTodayOperations(BigInteger debitAccountId, int dayOfMonth);

}
