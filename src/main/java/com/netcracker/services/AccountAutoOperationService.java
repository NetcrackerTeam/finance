package com.netcracker.services;

import com.netcracker.models.AbstractAutoOperation;
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

    List<AbstractAutoOperation> getAllTodayOperationsPersonal(BigInteger debitAccountId, int dayOfMonth);

    List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth);

    List<AbstractAutoOperation> getAllTodayOperations(int dayOfMonth);

}
