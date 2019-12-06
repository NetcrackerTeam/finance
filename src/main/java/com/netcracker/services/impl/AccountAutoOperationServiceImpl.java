package com.netcracker.services.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.AccountAutoOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class AccountAutoOperationServiceImpl implements AccountAutoOperationService {
    @Autowired
    private AutoOperationDao autoOperationDao;

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                               int dayOfMonth, long amount, CategoryIncome categoryIncome) {
        AutoOperationIncome autoOperationIncomeDao = autoOperationDao.createFamilyIncomeAutoOperation(dayOfMonth, amount,
                categoryIncome, userId, familyDebitAccountId);
        return getAutoOperationIncome(autoOperationIncomeDao);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                                 int dayOfMonth, long amount, CategoryIncome categoryIncome) {
        AutoOperationIncome autoOperationIncomeDao = autoOperationDao.createPersonalIncomeAutoOperation(dayOfMonth, amount,
                categoryIncome, userId, personalDebitAccountId);
        return getAutoOperationIncome(autoOperationIncomeDao);
    }

    @Override
    public void deleteFamilyIncomeAutoOperation(BigInteger autoOperationId) {
        autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    @Override
    public void deletePersonalIncomeAutoOperation(BigInteger autoOperationId) {
        autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                                 int dayOfMonth, long amount, CategoryExpense categoryExpense) {
        AutoOperationExpense autoOperationExpenseDao = autoOperationDao.createFamilyExpenseAutoOperation(dayOfMonth, amount,
                categoryExpense, userId, familyDebitAccountId);
        return getAutoOperationExpense(autoOperationExpenseDao);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                                   int dayOfMonth, long amount, CategoryExpense categoryExpense) {
        AutoOperationExpense autoOperationExpenseDao = autoOperationDao.createPersonalExpenseAutoOperation(dayOfMonth,
                amount, categoryExpense, userId, personalDebitAccountId);
        return getAutoOperationExpense(autoOperationExpenseDao);
    }

    @Override
    public void deleteFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    @Override
    public void deletePersonalExpenseAutoOperation(BigInteger autoOperationId) {
        autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    private AutoOperationIncome getAutoOperationIncome(AutoOperationIncome autoOperationIncomeDao) {
        return new AutoOperationIncome.Builder().accountId(autoOperationIncomeDao.getId())
                .accountUserId(autoOperationIncomeDao.getUserId()).accountAmount(autoOperationIncomeDao.getAmount())
                .dayOfMonth(autoOperationIncomeDao.getDayOfMonth()).categoryIncome(autoOperationIncomeDao.getCategoryIncome())
                .accountDate(autoOperationIncomeDao.getDate()).build();
    }

    private AutoOperationExpense getAutoOperationExpense(AutoOperationExpense autoOperationExpenseDao) {
        return new AutoOperationExpense.Builder().accountId(autoOperationExpenseDao.getId())
                .accountUserId(autoOperationExpenseDao.getUserId()).accountAmount(autoOperationExpenseDao.getAmount())
                .dayOfMonth(autoOperationExpenseDao.getDayOfMonth()).categoryExpense(autoOperationExpenseDao.getCategoryExpense())
                .accountDate(autoOperationExpenseDao.getDate()).build();
    }
}
