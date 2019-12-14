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
    public AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId, BigInteger familyDebitAccountId) {
        logger.debug("[createFamilyIncomeAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " + familyDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + autoOperationIncome.getDayOfMonth() + "], [amount = " + autoOperationIncome.getAmount() + "], [categoryIncome = " +
                autoOperationIncome.getCategoryIncome() + "]");

        CategoryIncome categoryIncome = autoOperationIncome.getCategoryIncome();
        int dayOfMonth = autoOperationIncome.getDayOfMonth();
        long amount = autoOperationIncome.getAmount();

        boolean categoryIncomeIsDefault = CategoryIncome.DEFAULT.equals(categoryIncome);
        boolean categoryIncomeIsNull = categoryIncome == null;
        boolean parametersNull = parametersIsNull(familyDebitAccountId, userId, dayOfMonth, amount);

        /*if (categoryIncomeIsDefault || categoryIncomeIsNull || parametersNull) {
            throw new OperationException("[createFamilyIncomeAutoOperation]" + exceptionMessageNull);
        } else*/ return autoOperationDao.createFamilyIncomeAutoOperation(autoOperationIncome, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger personalDebitAccountId) {
        logger.debug("[createPersonalIncomeAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " + personalDebitAccountId +
                "], [dayOfMonth = " + autoOperationIncome.getDayOfMonth() + "], [amount = " + autoOperationIncome.getAmount() + "], [categoryIncome = " +
                autoOperationIncome.getCategoryIncome() + "]");

        CategoryIncome categoryIncome = autoOperationIncome.getCategoryIncome();
        int dayOfMonth = autoOperationIncome.getDayOfMonth();
        long amount = autoOperationIncome.getAmount();

        boolean categoryIncomeIsDefault = CategoryIncome.DEFAULT.equals(categoryIncome);
        boolean categoryIncomeIsNull = categoryIncome == null;
        boolean parametersNull = parametersIsNull(personalDebitAccountId, personalDebitAccountId, dayOfMonth, amount);

        /*if (categoryIncomeIsDefault || categoryIncomeIsNull || parametersNull) {
            throw new OperationException("[createPersonalIncomeAutoOperation]" + exceptionMessageNull);
        } else*/ return autoOperationDao.createPersonalIncomeAutoOperation(autoOperationIncome, personalDebitAccountId);
    }

    @Override
    public AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId, BigInteger familyDebitAccountId) {
        int dayOfMonth = autoOperationExpense.getDayOfMonth();
        long amount = autoOperationExpense.getAmount();
        CategoryExpense categoryExpense = autoOperationExpense.getCategoryExpense();

        logger.debug("[createFamilyExpenseAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " + familyDebitAccountId +
                "], [userId = " + userId + "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryExpense = " +
                categoryExpense + "]");

        boolean categoryExpenseIsDefault = CategoryExpense.DEFAULT.equals(categoryExpense);
        boolean categoryExpenseIsNull = categoryExpense == null;
        boolean parametersNull = parametersIsNull(familyDebitAccountId, userId, dayOfMonth, amount);

        /*if (categoryExpenseIsDefault || categoryExpenseIsNull || parametersNull) {
            throw new OperationException("[createFamilyExpenseAutoOperation]" + exceptionMessageNull);
        } else*/ return autoOperationDao.createFamilyExpenseAutoOperation(autoOperationExpense, userId, familyDebitAccountId);
    }

    @Override
    public AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger personalDebitAccountId) {
        int dayOfMonth = autoOperationExpense.getDayOfMonth();
        long amount = autoOperationExpense.getAmount();
        CategoryExpense categoryExpense = autoOperationExpense.getCategoryExpense();

        logger.debug("[createPersonalExpenseAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " + personalDebitAccountId +
                "], [dayOfMonth = " + dayOfMonth + "], [amount = " + amount + "], [categoryExpense = " +
                categoryExpense + "]");

        boolean categoryExpenseIsDefault = CategoryExpense.DEFAULT.equals(categoryExpense);
        boolean categoryExpenseIsNull = categoryExpense == null;
        boolean parametersNull = parametersIsNull(personalDebitAccountId, personalDebitAccountId, dayOfMonth, amount);

        /*if (categoryExpenseIsDefault || categoryExpenseIsNull || parametersNull) {
            throw new OperationException("[createPersonalExpenseAutoOperation]" + exceptionMessageNull);
        }else*/return autoOperationDao.createPersonalExpenseAutoOperation(autoOperationExpense, personalDebitAccountId);
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

        /*if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getFamilyIncomeAutoOperation]" + exceptionMessageNull);
        else*/ return autoOperationDao.getFamilyIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getFamilyExpenseAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        /*if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getFamilyExpenseAutoOperation]" + exceptionMessageNull);
        else*/ return autoOperationDao.getFamilyExpenseAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getPersonalIncomeAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        /*if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getPersonalIncomeAutoOperation]" + exceptionMessageNull);
        else*/ return autoOperationDao.getPersonalIncomeAutoOperation(autoOperationId);
    }

    @Override
    public AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId) {
        logger.debug("[getPersonalExpenseAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        /*if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[getPersonalExpenseAutoOperation]" + exceptionMessageNull);
        else*/ return autoOperationDao.getPersonalExpenseAutoOperation(autoOperationId);
    }

    @Override
    public void deleteAutoOperation(BigInteger autoOperationId) {
        logger.debug("[deleteAutoOperation]" + debugStartMessage + "[autoOperationId = " + autoOperationId + "]");

        /*if (bigIntegerIsNull(autoOperationId)) throw new OperationException("[deleteAutoOperation]" + exceptionMessageNull);
        else*/ autoOperationDao.deleteAutoOperation(autoOperationId);
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
