package com.netcracker.services.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.exception.OperationException;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.AccountAutoOperationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class AccountAutoOperationServiceImpl implements AccountAutoOperationService {
    @Autowired
    private AutoOperationDao autoOperationDao;

    private static final Logger logger = Logger.getLogger(AccountAutoOperationServiceImpl.class);
    private final String exceptionMessageNull = " method has null";
    private static final String debugStartMessage = " method start with parameters: ";

    @Override
    public AutoOperationIncome createFamilyIncomeAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                           int dayOfMonth, long amount, CategoryIncome categoryIncome) {
        logger.debug("[createFamilyIncomeAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " + familyDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryIncome = " +
                categoryIncome + "]");

        boolean categoryIncomeIsDefault = CategoryIncome.DEFAULT.equals(categoryIncome);
        boolean categoryIncomeIsNull = categoryIncome == null;
        boolean parametersNull = parametersIsNull(familyDebitAccountId, userId, dayOfMonth, amount);

        if (categoryIncomeIsDefault || categoryIncomeIsNull || parametersNull) {
            throw new OperationException("[createFamilyIncomeAutoOperation]" + exceptionMessageNull);
        } else return autoOperationDao.createFamilyIncomeAutoOperation(dayOfMonth, amount, categoryIncome, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                                 int dayOfMonth, long amount, CategoryIncome categoryIncome) {
        logger.debug("[createPersonalIncomeAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " + personalDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryIncome = " +
                categoryIncome + "]");

        boolean categoryIncomeIsDefault = CategoryIncome.DEFAULT.equals(categoryIncome);
        boolean categoryIncomeIsNull = categoryIncome == null;
        boolean parametersNull = parametersIsNull(personalDebitAccountId, userId, dayOfMonth, amount);

        if (categoryIncomeIsDefault || categoryIncomeIsNull || parametersNull) {
            throw new OperationException("[createPersonalIncomeAutoOperation]" + exceptionMessageNull);
        } else return autoOperationDao.createPersonalIncomeAutoOperation(dayOfMonth, amount, categoryIncome, userId, personalDebitAccountId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(BigInteger familyDebitAccountId, BigInteger userId,
                                                                 int dayOfMonth, long amount, CategoryExpense categoryExpense) {
        logger.debug("[createFamilyExpenseAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " + familyDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryExpense = " +
                categoryExpense + "]");

        boolean categoryExpenseIsDefault = CategoryExpense.DEFAULT.equals(categoryExpense);
        boolean categoryExpenseIsNull = categoryExpense == null;
        boolean parametersNull = parametersIsNull(familyDebitAccountId, userId, dayOfMonth, amount);

        if (categoryExpenseIsDefault || categoryExpenseIsNull || parametersNull) {
            throw new OperationException("[createFamilyExpenseAutoOperation]" + exceptionMessageNull);
        } else return autoOperationDao.createFamilyExpenseAutoOperation(dayOfMonth, amount, categoryExpense, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(BigInteger personalDebitAccountId, BigInteger userId,
                                                                   int dayOfMonth, long amount, CategoryExpense categoryExpense) {
        logger.debug("[createPersonalExpenseAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " + personalDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryExpense = " +
                categoryExpense + "]");

        boolean categoryExpenseIsDefault = CategoryExpense.DEFAULT.equals(categoryExpense);
        boolean categoryExpenseIsNull = categoryExpense == null;
        boolean parametersNull = parametersIsNull(personalDebitAccountId, userId, dayOfMonth, amount);

        if (categoryExpenseIsDefault || categoryExpenseIsNull || parametersNull) {
            throw new OperationException("[createPersonalExpenseAutoOperation]" + exceptionMessageNull);
        } else return autoOperationDao.createPersonalExpenseAutoOperation(dayOfMonth, amount, categoryExpense, userId, personalDebitAccountId);
    }

    private boolean parametersIsNull(BigInteger debitAccountId, BigInteger userId, int dayOfMonth, long amount) {
        boolean dayOfMonthIsNull = dayOfMonth == 0;
        boolean debitAccountIdIsNull = bigIntegerIsNull(debitAccountId);
        boolean userIdIsNull = bigIntegerIsNull(userId);
        boolean amountIsNull = amount == 0;

        return dayOfMonthIsNull || debitAccountIdIsNull || userIdIsNull || amountIsNull;
    }

    private boolean bigIntegerIsNull(BigInteger bigInteger) {
        boolean bigIntegerZero = BigInteger.ZERO.equals(bigInteger);
        boolean bigIntegerNull = bigInteger == null;

        return bigIntegerZero || bigIntegerNull;
    }

    @Override
    public AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getFamilyIncomeAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getFamilyIncomeAutoOperation]" + exceptionMessageNull);
        else return autoOperationDao.getFamilyIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getFamilyExpenseAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getFamilyExpenseAutoOperation]" + exceptionMessageNull);
        else return autoOperationDao.getFamilyExpenseAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getPersonalIncomeAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getPersonalIncomeAutoOperation]" + exceptionMessageNull);
        else return autoOperationDao.getPersonalIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getPersonalExpenseAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getPersonalExpenseAutoOperation]" + exceptionMessageNull);
        else return autoOperationDao.getPersonalExpenseAutoOperation(autoOperationId);
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        logger.debug("[deleteAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[deleteAutoOperation]" + exceptionMessageNull);
        else autoOperationDao.deleteAutoOperation(autoOperationId);
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsPersonal(BigInteger debitAccountId, int dayOfMonth) {
        logger.debug("[getAllTodayOperationsPersonal]" + debugStartMessage + "[debitAccountId = " + debitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "]");

        boolean debitAccountIdIsNull = bigIntegerIsNull(debitAccountId);
        boolean dayOfMonthIsNull = dayOfMonth == 0;

        if (debitAccountIdIsNull || dayOfMonthIsNull) throw new OperationException("[getAllTodayOperationsPersonal]" + exceptionMessageNull);
        else {
            Collection<AbstractAutoOperation> allTodayOperationsCollection = autoOperationDao.getAllTodayOperationsPersonal(debitAccountId, dayOfMonth);
            if (CollectionUtils.isEmpty(allTodayOperationsCollection)) {
                throw new OperationException("Method [getAllTodayOperationsPersonal] has empty [allTodayOperationsCollection]");
            } else {
                List<AbstractAutoOperation> allTodayOperations = new ArrayList<>(allTodayOperationsCollection);
                allTodayOperations.sort(Comparator.comparing(AbstractAutoOperation::getId));
                return allTodayOperations;
            }
        }
    }

    @Override
    public List<AbstractAutoOperation> getAllTodayOperationsFamily(BigInteger debitAccountId, int dayOfMonth) {
        logger.debug("[getAllTodayOperationsFamily]" + debugStartMessage + "[debitAccountId = " + debitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "]");

        boolean debitAccountIdIsNull = bigIntegerIsNull(debitAccountId);
        boolean dayOfMonthIsNull = dayOfMonth == 0;

        if (debitAccountIdIsNull || dayOfMonthIsNull) throw new OperationException("[getAllTodayOperationsFamily]" + exceptionMessageNull);
        else {
            Collection<AbstractAutoOperation> allTodayOperationsCollection = autoOperationDao.getAllTodayOperationsFamily(debitAccountId, dayOfMonth);
            if (CollectionUtils.isEmpty(allTodayOperationsCollection)) {
                throw new OperationException("Method [getAllTodayOperationsFamily] has empty [allTodayOperationsCollection]");
            } else {
                List<AbstractAutoOperation> allTodayOperations = new ArrayList<>(allTodayOperationsCollection);
                allTodayOperations.sort(Comparator.comparing(AbstractAutoOperation::getId));
                return allTodayOperations;
            }
        }
    }
}
