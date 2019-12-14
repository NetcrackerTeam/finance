package com.netcracker.services.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.exception.OperationException;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.OperationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationDao operationDao;

    private static final Logger logger = Logger.getLogger(OperationServiceImpl.class);
    private final String exceptionMessageNull = " method has null";
    private static final String debugStartMessage = " method start with parameters: ";

    @Override
    public List<AbstractAccountOperation> getAllFamilyOperations(BigInteger accountId, LocalDate afterDate) {
        logger.debug("[getAllFamilyOperations]" + debugStartMessage + "[accountId = " + accountId + "], [afterDate = " + afterDate + "]");

        boolean accountIdIsNull = bigIntegerIsNull(accountId);
        boolean afterDateIsNull = afterDate == null;

        if (accountIdIsNull || afterDateIsNull) throw new OperationException("[getAllFamilyOperations]" + exceptionMessageNull);
        else {
            Collection<AccountIncome> allFamilyOperationsIncomeCollection = operationDao.getIncomesFamilyAfterDateByAccountId(accountId, afterDate);
            Collection<AccountExpense> allFamilyOperationsExpenseCollection = operationDao.getExpensesFamilyAfterDateByAccountId(accountId, afterDate);

            if (CollectionUtils.isEmpty(allFamilyOperationsIncomeCollection)) {
                throw new OperationException("Method [getAllFamilyOperations] has empty [allFamilyOperationsIncomeCollection]");
            } else if (CollectionUtils.isEmpty(allFamilyOperationsExpenseCollection)) {
                throw new OperationException("Method [getAllFamilyOperations] has empty [allFamilyOperationsExpenseCollection]");
            } else {
                return createAllPersonalOperationsList(allFamilyOperationsIncomeCollection, allFamilyOperationsExpenseCollection);
            }
        }
    }

    @Override
    public List<AbstractAccountOperation> getAllPersonalOperations(BigInteger accountId, LocalDate afterDate) {
        logger.debug("[getAllPersonalOperations]" + debugStartMessage + "[accountId = " + accountId + "], [afterDate = " + afterDate + "]");

        boolean accountIdIsNull = bigIntegerIsNull(accountId);
        boolean afterDateIsNull = afterDate == null;

        if (accountIdIsNull || afterDateIsNull) throw new OperationException("[getAllPersonalOperations]" + exceptionMessageNull);
        else {
            Collection<AccountIncome> allPersonalOperationsIncomeCollection = operationDao.getIncomesPersonalAfterDateByAccountId(accountId, afterDate);
            Collection<AccountExpense> allPersonalOperationsExpenseCollection = operationDao.getExpensesPersonalAfterDateByAccountId(accountId, afterDate);

            if (CollectionUtils.isEmpty(allPersonalOperationsIncomeCollection)) {
                throw new OperationException("Method [getAllPersonalOperations] has empty [allPersonalOperationsIncomeCollection]");
            } else if (CollectionUtils.isEmpty(allPersonalOperationsExpenseCollection)) {
                throw new OperationException("Method [getAllPersonalOperations] has empty [allPersonalOperationsExpenseCollection]");
            } else {
                return createAllPersonalOperationsList(allPersonalOperationsIncomeCollection, allPersonalOperationsExpenseCollection);
            }
        }
    }

    private List<AbstractAccountOperation> createAllPersonalOperationsList(Collection<AccountIncome> allOperationsIncomeCollection,
                                                                           Collection<AccountExpense> allOperationsExpenseCollection) {
        List<AccountIncome> allOperationsIncome = new ArrayList<>(allOperationsIncomeCollection);
        List<AccountExpense> allOperationsExpense = new ArrayList<>(allOperationsExpenseCollection);
        List<AbstractAccountOperation> allOperations = new ArrayList<>(allOperationsIncome);
        allOperations.addAll(allOperationsExpense);
        allOperations.sort(Comparator.comparing(AbstractAccountOperation::getId));
        return allOperations;
    }

    @Override
    public void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, long income, LocalDate date, CategoryIncome categoryIncome) {
        logger.debug("[createFamilyOperationIncome]" + debugStartMessage + "[idUser = " + idUser + "], [idFamily = "
                + idFamily + "], [income = " + income + "], [date = " + date + "], [categoryIncome = " + categoryIncome + "]");

        boolean idFamilyIsNull = bigIntegerIsNull(idFamily);
        boolean categoryIncomeIsDefault = CategoryIncome.DEFAULT.equals(categoryIncome);
        boolean categoryIncomeIsNull = categoryIncome == null;
        boolean parametersNull = parametersIsNull(idUser, income, date);

        if (idFamilyIsNull || categoryIncomeIsDefault || categoryIncomeIsNull || parametersNull) {
            throw new OperationException("[createFamilyOperationIncome]" + exceptionMessageNull);
        } else operationDao.createIncomeFamilyByAccId(idUser, idFamily, income, date, categoryIncome);
    }

    @Override
    public void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, long expense, LocalDate date, CategoryExpense categoryExpense) {
        logger.debug("[createFamilyOperationExpense]" + debugStartMessage + "[idUser = " + idUser + "], [idFamily = "
                + idFamily + "], [expense = " + expense + "], [date = " + date + "], [categoryExpense = " + categoryExpense + "]");

        boolean idFamilyIsNull = bigIntegerIsNull(idFamily);
        boolean categoryExpenseIsDefault = CategoryExpense.DEFAULT.equals(categoryExpense);
        boolean categoryExpenseIsNull = categoryExpense == null;
        boolean parametersNull = parametersIsNull(idUser, expense, date);

        if (idFamilyIsNull || categoryExpenseIsDefault || categoryExpenseIsNull || parametersNull) {
            throw new OperationException("[createFamilyOperationExpense]" + exceptionMessageNull);
        } else operationDao.createExpenseFamilyByAccId(idUser, idFamily, expense, date, categoryExpense);
    }

    @Override
    public void createPersonalOperationIncome(BigInteger id, long income, LocalDate date, CategoryIncome categoryIncome) {
        logger.debug("[createPersonalOperationIncome]" + debugStartMessage + "[id = " + id + "], [income = " + income +
                "], [date = " + date + "], [categoryIncome = " + categoryIncome + "]");

        boolean categoryIncomeIsDefault = CategoryIncome.DEFAULT.equals(categoryIncome);
        boolean categoryIncomeIsNull = categoryIncome == null;
        boolean parametersNull = parametersIsNull(id, income, date);

        if (categoryIncomeIsDefault || categoryIncomeIsNull || parametersNull) {
            throw new OperationException("[createPersonalOperationIncome]" + exceptionMessageNull);
        } else operationDao.createIncomePersonalByAccId(id, income, date, categoryIncome);
    }

    @Override
    public void createPersonalOperationExpense(BigInteger id, long expense, LocalDate date, CategoryExpense categoryExpense) {
        logger.debug("[createPersonalOperationExpense]" + debugStartMessage + "[id = " + id + "], [expense = " + expense +
                "], [date = " + date + "], [categoryExpense = " + categoryExpense + "]");

        boolean categoryExpenseIsDefault = CategoryExpense.DEFAULT.equals(categoryExpense);
        boolean categoryExpenseIsNull = categoryExpense == null;
        boolean parametersNull = parametersIsNull(id, expense, date);

        if (categoryExpenseIsDefault || categoryExpenseIsNull || parametersNull) {
            throw new OperationException("[createPersonalOperationExpense]" + exceptionMessageNull);
        } else operationDao.createExpensePersonaByAccId(id, expense, date, categoryExpense);
    }

    private boolean parametersIsNull(BigInteger idUser, long amount, LocalDate date) {
        boolean idUserIsNull = bigIntegerIsNull(idUser);
        boolean amountIsNull = amount == 0;
        boolean dateIsNull = date == null;

        return idUserIsNull || amountIsNull || dateIsNull;
    }

    private boolean bigIntegerIsNull(BigInteger bigInteger) {
        boolean bigIntegerZero = BigInteger.ZERO.equals(bigInteger);
        boolean bigIntegerNull = bigInteger == null;

        return bigIntegerZero || bigIntegerNull;
    }

}
