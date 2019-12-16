package com.netcracker.services;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;

import java.math.BigInteger;
import java.util.List;

public interface AccountAutoOperationService {
    String debugStartMessage = " method start with parameters: ";

    AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId,
                                                        BigInteger familyDebitAccountId);

    AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome,
                                                          BigInteger personalDebitAccountId);

    AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId,
                                                          BigInteger familyDebitAccountId);

    AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense,
                                                            BigInteger personalDebitAccountId);

    AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId);

    AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId);

    void deleteAutoOperation(BigInteger autoOperationId);

    List getAllTodayOperationsPersonalIncome(int dayOfMonth);
    List<AutoOperationExpense> getAllTodayOperationsPersonalExpense(int dayOfMonth);
    List<AutoOperationIncome> getAllTodayOperationsFamilyIncome(int dayOfMonth);
    List<AutoOperationExpense> getAllTodayOperationsFamilyExpense(int dayOfMonth);

}
