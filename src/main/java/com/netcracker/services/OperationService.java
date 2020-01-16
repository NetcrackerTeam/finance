package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OperationService {
    String debugStartMessage = " method start with parameters: ";

    List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, LocalDateTime afterDate);

    List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, LocalDateTime afterDate);
    List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, LocalDateTime afterDate);
    List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, LocalDateTime afterDate);
    List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, LocalDateTime afterDate);

    List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, LocalDateTime afterDate);

    void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, double income, LocalDateTime date, CategoryIncome categoryIncome);

    void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, double expense, LocalDateTime date, CategoryExpense categoryExpense);

    void createPersonalOperationIncome(BigInteger id, double income, LocalDateTime date, CategoryIncome categoryIncome);

    void createPersonalOperationExpense(BigInteger id, double expense, LocalDateTime date, CategoryExpense categoryExpense);
}
