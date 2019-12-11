package com.netcracker.services;

import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface OperationService {
    List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, Date afterDate);

    List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, Date afterDate);

    void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, long income, Date date, CategoryIncome categoryIncome);

    void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, long expense, Date date, CategoryExpense categoryExpense);

    void createPersonalOperationIncome(BigInteger id, long income, Date date, CategoryIncome categoryIncome);

    void createPersonalOperationExpense(BigInteger id, long expense, Date date, CategoryExpense categoryExpense);
}
