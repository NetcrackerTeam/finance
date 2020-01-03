package com.netcracker.services;

import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;

import java.math.BigInteger;
import java.util.List;

public interface AccountAutoOperationService {
    String debugStartMessage = " method start with parameters: ";

    void createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId,
                                                        BigInteger familyDebitAccountId);

    void createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome,
                                                          BigInteger personalDebitAccountId);

    void createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId,
                                                          BigInteger familyDebitAccountId);

    void createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense,
                                                            BigInteger personalDebitAccountId);

    AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId);

    AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId);

    void deleteAutoOperation(BigInteger autoOperationId);

    List<AutoOperationIncome> getAllTodayOperationsPersonalIncome(int dayOfMonth);
    List<AutoOperationExpense> getAllTodayOperationsPersonalExpense(int dayOfMonth);
    List<AutoOperationIncome> getAllTodayOperationsFamilyIncome(int dayOfMonth);
    List<AutoOperationExpense> getAllTodayOperationsFamilyExpense(int dayOfMonth);
    List<AutoOperationExpense> getAllOperationsFamilyExpense(BigInteger debitId);
    List<AutoOperationIncome> getAllOperationsFamilyIncome(BigInteger debitId);
    List<AutoOperationIncome> getAllOperationsPersonalIncome(BigInteger debitId);
    List<AutoOperationExpense> getAllOperationsPersonalExpense(BigInteger debitId);
    List<AbstractAutoOperation> getAllOperationsPersonal(BigInteger debitId);
    List<AbstractAutoOperation> getAllOperationsFamily(BigInteger debitId);
}
