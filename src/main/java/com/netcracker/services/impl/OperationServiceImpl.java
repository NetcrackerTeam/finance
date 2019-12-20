package com.netcracker.services.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.OperationService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationDao operationDao;

    private static final Logger logger = Logger.getLogger(OperationServiceImpl.class);

    @Override
    public List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, LocalDate afterDate) {
        logger.debug("[getAllFamilyOperations]" + debugStartMessage + "[accountId = " + accountId + "], [afterDate = " + afterDate + "]");

        ObjectsCheckUtils.isNotNull(accountId, afterDate);

        List<AccountIncome> allFamilyOperationsIncomeList = operationDao.getIncomesFamilyAfterDateByAccountId(accountId, afterDate);
        List<AccountExpense> allFamilyOperationsExpenseList = operationDao.getExpensesFamilyAfterDateByAccountId(accountId, afterDate);
        return createAllOperationsList(allFamilyOperationsIncomeList, allFamilyOperationsExpenseList);
    }

    @Override
    public List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, LocalDate afterDate) {
        logger.debug("[getExpensesFamilyAfterDateByAccountId]" + debugStartMessage + "[id = " + id + "], afterDate = " + afterDate + "]");
        ObjectsCheckUtils.isNotNull(id, afterDate);
        return operationDao.getExpensesFamilyAfterDateByAccountId(id, afterDate);
    }

    @Override
    public List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, LocalDate afterDate) {
        logger.debug("[getIncomesFamilyAfterDateByAccountId]" + debugStartMessage + "[id = " + id + "], afterDate = " + afterDate + "]");
        ObjectsCheckUtils.isNotNull(id, afterDate);
        return operationDao.getIncomesFamilyAfterDateByAccountId(id, afterDate);
    }

    @Override
    public List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, LocalDate afterDate) {
        logger.debug("[getExpensesPersonalAfterDateByAccountId]" + debugStartMessage + "[id = " + id + "], afterDate = " + afterDate + "]");
        ObjectsCheckUtils.isNotNull(id, afterDate);
        return operationDao.getExpensesPersonalAfterDateByAccountId(id, afterDate);
    }

    @Override
    public List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, LocalDate afterDate) {
        logger.debug("[getIncomesPersonalAfterDateByAccountId]" + debugStartMessage + "[id = " + id + "], afterDate = " + afterDate + "]");
        ObjectsCheckUtils.isNotNull(id, afterDate);
        return operationDao.getIncomesPersonalAfterDateByAccountId(id, afterDate);
    }

    @Override
    public List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, LocalDate afterDate) {
        logger.debug("[getAllPersonalOperations]" + debugStartMessage + "[accountId = " + accountId + "], [afterDate = " + afterDate + "]");

        ObjectsCheckUtils.isNotNull(accountId, afterDate);

        List<AccountIncome> allPersonalOperationsIncomeList = operationDao.getIncomesPersonalAfterDateByAccountId(accountId, afterDate);
        List<AccountExpense> allPersonalOperationsExpenseList = operationDao.getExpensesPersonalAfterDateByAccountId(accountId, afterDate);
        return createAllOperationsList(allPersonalOperationsIncomeList, allPersonalOperationsExpenseList);
    }

    private List<AbstractAccountOperation> createAllOperationsList(List<AccountIncome> allOperationsIncomeList,
                                                                   List<AccountExpense> allOperationsExpenseList) {
        List<AbstractAccountOperation> allOperationsList = new ArrayList<>(allOperationsIncomeList);
        allOperationsList.addAll(allOperationsExpenseList);
        if (CollectionUtils.isEmpty(allOperationsList)) return Collections.emptyList();
        else {
            allOperationsList.sort(Comparator.comparing(AbstractAccountOperation::getId));
            return allOperationsList;
        }
    }

    @Override
    public void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, double income, LocalDate date, CategoryIncome categoryIncome) {
        logger.debug("[createFamilyOperationIncome]" + debugStartMessage + "[idUser = " + idUser + "], [idFamily = "
                + idFamily + "], [income = " + income + "], [date = " + date + "], [categoryIncome = " + categoryIncome + "]");

        ObjectsCheckUtils.isNotNull(idUser, idFamily, income, date, categoryIncome);
        operationDao.createIncomeFamilyByAccId(idUser, idFamily, income, date, categoryIncome);
    }

    @Override
    public void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, double expense, LocalDate date, CategoryExpense categoryExpense) {
        logger.debug("[createFamilyOperationExpense]" + debugStartMessage + "[idUser = " + idUser + "], [idFamily = "
                + idFamily + "], [expense = " + expense + "], [date = " + date + "], [categoryExpense = " + categoryExpense + "]");

        ObjectsCheckUtils.isNotNull(idUser, idFamily, expense, date, categoryExpense);
        operationDao.createExpenseFamilyByAccId(idUser, idFamily, expense, date, categoryExpense);
    }

    @Override
    public void createPersonalOperationIncome(BigInteger id, double income, LocalDate date, CategoryIncome categoryIncome) {
        logger.debug("[createPersonalOperationIncome]" + debugStartMessage + "[id = " + id + "], [income = " + income +
                "], [date = " + date + "], [categoryIncome = " + categoryIncome + "]");

        ObjectsCheckUtils.isNotNull(id, income, date, categoryIncome);
        operationDao.createIncomePersonalByAccId(id, income, date, categoryIncome);
    }

    @Override
    public void createPersonalOperationExpense(BigInteger id, double expense, LocalDate date, CategoryExpense categoryExpense) {
        logger.debug("[createPersonalOperationExpense]" + debugStartMessage + "[id = " + id + "], [expense = " + expense +
                "], [date = " + date + "], [categoryExpense = " + categoryExpense + "]");

        ObjectsCheckUtils.isNotNull(id, expense, date, categoryExpense);
        operationDao.createExpensePersonaByAccId(id, expense, date, categoryExpense);
    }
}
