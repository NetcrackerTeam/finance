package com.netcracker.services.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.AccountAutoOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AccountAutoOperationServiceImpl implements AccountAutoOperationService {
    @Autowired
    private AutoOperationDao autoOperationDao;

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                           int dayOfMonth, long amount, CategoryIncome categoryIncome) {
       return autoOperationDao.createFamilyIncomeAutoOperation(dayOfMonth, amount, categoryIncome, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                                 int dayOfMonth, long amount, CategoryIncome categoryIncome) {
        return autoOperationDao.createPersonalIncomeAutoOperation(dayOfMonth, amount, categoryIncome, userId, personalDebitAccountId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                                 int dayOfMonth, long amount, CategoryExpense categoryExpense) {
        return autoOperationDao.createFamilyExpenseAutoOperation(dayOfMonth, amount, categoryExpense, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                                   int dayOfMonth, long amount, CategoryExpense categoryExpense) {
        return autoOperationDao.createPersonalExpenseAutoOperation(dayOfMonth, amount, categoryExpense, userId, personalDebitAccountId);
    }

    @Override
    public AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId) {
        return autoOperationDao.getFamilyIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        return autoOperationDao.getFamilyExpenseAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId) {
        return autoOperationDao.getPersonalIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId) {
        return autoOperationDao.getPersonalExpenseAutoOperation(autoOperationId);
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsPersonal(BigInteger debitAccountId, int dayOfMonth) {
        List<AbstractAutoOperation> allTodayOperations = new ArrayList<>(autoOperationDao.getAllTodayOperationsPersonal(debitAccountId, dayOfMonth));
        allTodayOperations.sort(Comparator.comparing(AbstractAutoOperation::getId));
        return allTodayOperations;
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth) {
        List<AbstractAutoOperation> allTodayOperations = new ArrayList<>(autoOperationDao.getAllTodayOperationsFamily(debitAccountId, dayOfMonth));
        allTodayOperations.sort(Comparator.comparing(AbstractAutoOperation::getId));
        return allTodayOperations;
    }
}
