package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface OperationService {
    List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, LocalDate afterDate);

    List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, LocalDate afterDate);

    void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, long income, LocalDate date, CategoryIncome categoryIncome);

    void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, long expense, LocalDate date, CategoryExpense categoryExpense);

    void createPersonalOperationIncome(BigInteger id, long income, LocalDate date, CategoryIncome categoryIncome);

    void createPersonalOperationExpense(BigInteger id, long expense, LocalDate date, CategoryExpense categoryExpense);
}
