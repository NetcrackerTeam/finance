package com.netcracker.services.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Service
public class AccountAutoOperationServiceImpl implements AccountAutoOperationService {
    @Autowired
    private AutoOperationDao autoOperationDao;

    private static final Logger logger = Logger.getLogger(AccountAutoOperationServiceImpl.class);

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId,
                                                               BigInteger familyDebitAccountId) {
        logger.debug("[createFamilyIncomeAutoOperation]" + debugStartMessage + "[ familyDebitAccountId = " +
                familyDebitAccountId + "], [userId = " + userId + "], " + autoOperationIncome.toString());

        ObjectsCheckUtils.isNotNull(autoOperationIncome.getDayOfMonth(), autoOperationIncome.getAmount(),
                autoOperationIncome.getCategoryIncome(), userId, familyDebitAccountId);
        return autoOperationDao.createFamilyIncomeAutoOperation(autoOperationIncome, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome,
                                                                 BigInteger personalDebitAccountId) {
        logger.debug("[createPersonalIncomeAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " +
                personalDebitAccountId + "], " + autoOperationIncome.toString());

        ObjectsCheckUtils.isNotNull(autoOperationIncome.getDayOfMonth(), autoOperationIncome.getAmount(),
                autoOperationIncome.getCategoryIncome(), personalDebitAccountId);
        return autoOperationDao.createPersonalIncomeAutoOperation(autoOperationIncome, personalDebitAccountId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId,
                                                                 BigInteger familyDebitAccountId) {
        logger.debug("[createFamilyExpenseAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " +
                familyDebitAccountId + "], [userId = " + userId + "], " + autoOperationExpense.toString());

        ObjectsCheckUtils.isNotNull(autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount(),
                autoOperationExpense.getCategoryExpense(), userId, familyDebitAccountId);
        return autoOperationDao.createFamilyExpenseAutoOperation(autoOperationExpense, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense,
                                                                   BigInteger personalDebitAccountId) {
        logger.debug("[createPersonalExpenseAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " +
                personalDebitAccountId + "], " + autoOperationExpense.toString());

        ObjectsCheckUtils.isNotNull(autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount(),
                autoOperationExpense.getCategoryExpense(), personalDebitAccountId);
        return autoOperationDao.createPersonalExpenseAutoOperation(autoOperationExpense, personalDebitAccountId);
    }

    @Override
    public AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getFamilyIncomeAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        ObjectsCheckUtils.isNotNull(autoOperationId);
        return autoOperationDao.getFamilyIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getFamilyExpenseAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        ObjectsCheckUtils.isNotNull(autoOperationId);
        return autoOperationDao.getFamilyExpenseAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getPersonalIncomeAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        ObjectsCheckUtils.isNotNull(autoOperationId);
        return autoOperationDao.getPersonalIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getPersonalExpenseAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        ObjectsCheckUtils.isNotNull(autoOperationId);
        return autoOperationDao.getPersonalExpenseAutoOperation(autoOperationId);
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        logger.debug("[deleteAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        ObjectsCheckUtils.isNotNull(autoOperationId);
        autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsPersonal(BigInteger debitAccountId, int dayOfMonth) {
        logger.debug("[getAllTodayOperationsPersonal]" + debugStartMessage + "[debitAccountId = " + debitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(debitAccountId, dayOfMonth);
        List<AbstractAutoOperation> allTodayOperationsList = autoOperationDao.getAllTodayOperationsPersonal(debitAccountId,
                dayOfMonth);
        return createAllTodayOperationsList(allTodayOperationsList);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth) {
        logger.debug("[getAllTodayOperationsFamily]" + debugStartMessage + "[debitAccountId = " + debitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(debitAccountId, dayOfMonth);
        List<AbstractAutoOperation> allTodayOperationsList = autoOperationDao.getAllTodayOperationsFamily(debitAccountId,
                dayOfMonth);
        return createAllTodayOperationsList(allTodayOperationsList);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperations(int dayOfMonth) {
        logger.debug("[getAllTodayOperationsFamily]" + debugStartMessage + "[dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth);
        List<AbstractAutoOperation> allTodayOperationsList = autoOperationDao.getAllTodayOperations(dayOfMonth);
        return createAllTodayOperationsList(allTodayOperationsList);
    }

    private List<AbstractAutoOperation> createAllTodayOperationsList(List<AbstractAutoOperation> allTodayOperationsList) {
        if (CollectionUtils.isEmpty(allTodayOperationsList)) return Collections.emptyList();
        else return allTodayOperationsList;
    }
}
