package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface OperationService {
    String debugStartMessage = " method start with parameters: ";

    List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, LocalDate afterDate);

    List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, LocalDate afterDate);
    List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, LocalDate afterDate);
    List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, LocalDate afterDate);
    List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, LocalDate afterDate);

    List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, LocalDate afterDate);

    void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, double income, LocalDate date, CategoryIncome categoryIncome);

    void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, double expense, LocalDate date, CategoryExpense categoryExpense);

    void createPersonalOperationIncome(BigInteger id, double income, LocalDate date, CategoryIncome categoryIncome);

    void createPersonalOperationExpense(BigInteger id, double expense, LocalDate date, CategoryExpense categoryExpense);
}
