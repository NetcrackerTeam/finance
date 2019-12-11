package com.netcracker.services.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationDao operationDao;

    @Override
    public List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, Date afterDate) {
        List<AccountIncome> allFamilyOperationsIncome = new ArrayList<>(operationDao.getIncomesFamilyAfterDateByAccountId(accountId, afterDate));
        List<AccountExpense> allFamilyOperationsExpense = new ArrayList<>(operationDao.getExpensesFamilyAfterDateByAccountId(accountId, afterDate));
        List<AbstractAccountOperation> allFamilyOperations = new ArrayList<>(allFamilyOperationsIncome);
        allFamilyOperations.addAll(allFamilyOperationsExpense);
        allFamilyOperations.sort(Comparator.comparing(AbstractAccountOperation::getId));
        return allFamilyOperations;
    }

    @Override
    public List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, Date afterDate) {
        List<AccountIncome> allPersonalOperationsIncome = new ArrayList<>(operationDao.getIncomesPersonalAfterDateByAccountId(accountId, afterDate));
        List<AccountExpense> allPersonalOperationsExpense = new ArrayList<>(operationDao.getExpensesPersonalAfterDateByAccountId(accountId, afterDate));
        List<AbstractAccountOperation> allPersonalOperations = new ArrayList<>(allPersonalOperationsIncome);
        allPersonalOperations.addAll(allPersonalOperationsExpense);
        allPersonalOperations.sort(Comparator.comparing(AbstractAccountOperation::getId));
        return allPersonalOperations;
    }

    @Override
    public void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, long income, Date date, CategoryIncome categoryIncome) {
        operationDao.createIncomeFamilyByAccId(idUser, idFamily, income, date, categoryIncome);
    }

    @Override
    public void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, long expense, Date date, CategoryExpense categoryExpense) {
        operationDao.createExpenseFamilyByAccId(idUser, idFamily, expense, date, categoryExpense);
    }

    @Override
    public void createPersonalOperationIncome(BigInteger id, long income, Date date, CategoryIncome categoryIncome) {
        operationDao.createIncomePersonalByAccId(id, income, date, categoryIncome);
    }

    @Override
    public void createPersonalOperationExpense(BigInteger id, long expense, Date date, CategoryExpense categoryExpense) {
        operationDao.createExpensePersonaByAccId(id, expense, date, categoryExpense);
    }

}
