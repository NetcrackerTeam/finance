package com.netcracker.services;

import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
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

    List<AbstractAutoOperation> getAllTodayOperationsPersonal(BigInteger debitAccountId, int dayOfMonth);

    List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth);

}
