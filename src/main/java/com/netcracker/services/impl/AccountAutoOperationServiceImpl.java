package com.netcracker.services.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.*;

@Service
public class AccountAutoOperationServiceImpl implements AccountAutoOperationService {
    @Autowired
    private AutoOperationDao autoOperationDao;

    private static final Logger logger = Logger.getLogger(AccountAutoOperationServiceImpl.class);
    private static final String debugStartMessage = " method start with parameters: ";

    private int dayOfMonth;
    private long amount;
    private CategoryExpense categoryExpense;
    private CategoryIncome categoryIncome;

    private void initializeVariables(AbstractAutoOperation autoOperation) {
        this.dayOfMonth = autoOperation.getDayOfMonth();
        this.amount = autoOperation.getAmount();

        if (autoOperation instanceof AutoOperationIncome) this.categoryIncome = ((AutoOperationIncome) autoOperation).getCategoryIncome();
        if (autoOperation instanceof AutoOperationExpense) this.categoryExpense = ((AutoOperationExpense) autoOperation).getCategoryExpense();
    }

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId, BigInteger familyDebitAccountId) {
        initializeVariables(autoOperationIncome);

        logger.debug("[createFamilyIncomeAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " + familyDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryIncome = " +
                categoryIncome + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth, amount, categoryIncome, userId, familyDebitAccountId);
        return autoOperationDao.createFamilyIncomeAutoOperation(autoOperationIncome, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger personalDebitAccountId) {
        initializeVariables(autoOperationIncome);

        logger.debug("[createPersonalIncomeAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " + personalDebitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryIncome = " + categoryIncome + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth, amount, categoryIncome, personalDebitAccountId);
        return autoOperationDao.createPersonalIncomeAutoOperation(autoOperationIncome, personalDebitAccountId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId, BigInteger familyDebitAccountId) {
        initializeVariables(autoOperationExpense);

        logger.debug("[createFamilyExpenseAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " + familyDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryExpense = " +
                categoryExpense + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth, amount, categoryExpense, userId, familyDebitAccountId);
        return autoOperationDao.createFamilyExpenseAutoOperation(autoOperationExpense, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger personalDebitAccountId) {
        initializeVariables(autoOperationExpense);

        logger.debug("[createPersonalExpenseAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " + personalDebitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryExpense = " +
                categoryExpense + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth, amount, categoryExpense, personalDebitAccountId);
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
        List<AbstractAutoOperation> allTodayOperationsList = autoOperationDao.getAllTodayOperationsPersonal(debitAccountId, dayOfMonth);
        return createAllTodayOperationsList(allTodayOperationsList);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth) {
        logger.debug("[getAllTodayOperationsFamily]" + debugStartMessage + "[debitAccountId = " + debitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(debitAccountId, dayOfMonth);
        List<AbstractAutoOperation> allTodayOperationsList = autoOperationDao.getAllTodayOperationsFamily(debitAccountId, dayOfMonth);
        return createAllTodayOperationsList(allTodayOperationsList);
    }

    private List<AbstractAutoOperation> createAllTodayOperationsList(List<AbstractAutoOperation> allTodayOperationsList) {
        if (CollectionUtils.isEmpty(allTodayOperationsList)) return Collections.emptyList();
        else {
            List<AbstractAutoOperation> allTodayOperations = new ArrayList<>(allTodayOperationsList);
            allTodayOperations.sort(Comparator.comparing(AbstractAutoOperation::getId));
            return allTodayOperations;
        }
    }
}
