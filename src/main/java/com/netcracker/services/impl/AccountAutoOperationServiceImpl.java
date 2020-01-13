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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class AccountAutoOperationServiceImpl implements AccountAutoOperationService {
    @Autowired
    private AutoOperationDao autoOperationDao;

    private static final Logger logger = Logger.getLogger(AccountAutoOperationServiceImpl.class);

    @Override
    public void createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId,
                                                BigInteger familyDebitAccountId) {
        logger.debug("[createFamilyIncomeAutoOperation]" + debugStartMessage + "[ familyDebitAccountId = " +
                familyDebitAccountId + "], [userId = " + userId + "], " + autoOperationIncome.toString());

        ObjectsCheckUtils.isNotNull(autoOperationIncome.getDayOfMonth(), autoOperationIncome.getAmount(),
                autoOperationIncome.getCategoryIncome(), userId, familyDebitAccountId);
        boolean checksMaxDay = checkMaxDayInCurrentMonth(autoOperationIncome.getDayOfMonth());
        if (checksMaxDay) {
            autoOperationDao.createFamilyIncomeAutoOperation(autoOperationIncome, userId, familyDebitAccountId);
        }
    }

    @Override
    public void createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome,
                                                  BigInteger personalDebitAccountId) {
        logger.debug("[createPersonalIncomeAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " +
                personalDebitAccountId + "], " + autoOperationIncome.toString());

        ObjectsCheckUtils.isNotNull(autoOperationIncome.getDayOfMonth(), autoOperationIncome.getAmount(),
                autoOperationIncome.getCategoryIncome(), personalDebitAccountId);
        boolean checksMaxDay = checkMaxDayInCurrentMonth(autoOperationIncome.getDayOfMonth());
        if (checksMaxDay) {
            autoOperationDao.createPersonalIncomeAutoOperation(autoOperationIncome, personalDebitAccountId);
        }
    }

    @Override
    public void createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId,
                                                 BigInteger familyDebitAccountId) {
        logger.debug("[createFamilyExpenseAutoOperation]" + debugStartMessage + "[familyDebitAccountId = " +
                familyDebitAccountId + "], [userId = " + userId + "], " + autoOperationExpense.toString());

        ObjectsCheckUtils.isNotNull(autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount(),
                autoOperationExpense.getCategoryExpense(), userId, familyDebitAccountId);
        boolean checksMaxDay = checkMaxDayInCurrentMonth(autoOperationExpense.getDayOfMonth());
        if (checksMaxDay) {
            autoOperationDao.createFamilyExpenseAutoOperation(autoOperationExpense, userId, familyDebitAccountId);
        }
    }

    @Override
    public void createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense,
                                                   BigInteger personalDebitAccountId) {
        logger.debug("[createPersonalExpenseAutoOperation]" + debugStartMessage + "[personalDebitAccountId = " +
                personalDebitAccountId + "], " + autoOperationExpense.toString());

        ObjectsCheckUtils.isNotNull(autoOperationExpense.getDayOfMonth(), autoOperationExpense.getAmount(),
                autoOperationExpense.getCategoryExpense(), personalDebitAccountId);
        boolean checksMaxDay = checkMaxDayInCurrentMonth(autoOperationExpense.getDayOfMonth());
        if (checksMaxDay) {
            autoOperationDao.createPersonalExpenseAutoOperation(autoOperationExpense, personalDebitAccountId);
        }
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
    public List<AutoOperationIncome> getAllTodayOperationsPersonalIncome(int dayOfMonth) {
        logger.debug("[getAllTodayOperationsPersonalIncome]" + debugStartMessage + "[dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth);
        List<AutoOperationIncome> autoOperationIncomeList = autoOperationDao.getAllTodayOperationsPersonalIncome(dayOfMonth);
        ObjectsCheckUtils.collectionIsEmpty(Collections.singletonList(autoOperationIncomeList));
        return autoOperationIncomeList;
    }

    @Override
    public List<AutoOperationExpense> getAllTodayOperationsPersonalExpense(int dayOfMonth) {
        logger.debug("[getAllTodayOperationsPersonalExpense]" + debugStartMessage + "[dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth);
        List<AutoOperationExpense> autoOperationExpenseList = autoOperationDao.getAllTodayOperationsPersonalExpense(dayOfMonth);
        ObjectsCheckUtils.collectionIsEmpty(Collections.singletonList(autoOperationExpenseList));
        return autoOperationExpenseList;
    }

    @Override
    public List<AutoOperationIncome> getAllTodayOperationsFamilyIncome(int dayOfMonth) {
        logger.debug("[getAllTodayOperationsFamilyIncome]" + debugStartMessage + "[dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth);
        List<AutoOperationIncome> autoOperationIncomeList = autoOperationDao.getAllTodayOperationsFamilyIncome(dayOfMonth);
        ObjectsCheckUtils.collectionIsEmpty(Collections.singletonList(autoOperationIncomeList));
        return autoOperationIncomeList;
    }

    @Override
    public List<AutoOperationExpense> getAllTodayOperationsFamilyExpense(int dayOfMonth) {
        logger.debug("[getAllTodayOperationsFamilyExpense]" + debugStartMessage + "[dayOfMonth = " + dayOfMonth + "]");

        ObjectsCheckUtils.isNotNull(dayOfMonth);
        List<AutoOperationExpense> autoOperationExpenseList = autoOperationDao.getAllTodayOperationsFamilyExpense(dayOfMonth);
        ObjectsCheckUtils.collectionIsEmpty(Collections.singletonList(autoOperationExpenseList));
        return autoOperationExpenseList;
    }

    public List<AutoOperationExpense> getAllOperationsFamilyExpense(BigInteger debitId) {
        logger.debug("[getAllTodayOperationsFamilyExpense]" + debugStartMessage + "[debitId = " + debitId + "]");
        ObjectsCheckUtils.isNotNull(debitId);
        return autoOperationDao.getAllOperationsFamilyExpense(debitId);
    }

    public List<AutoOperationIncome> getAllOperationsFamilyIncome(BigInteger debitId) {
        logger.debug("[getAllTodayOperationsFamilyIncome]" + debugStartMessage + "[debitId = " + debitId + "]");
        ObjectsCheckUtils.isNotNull(debitId);
        return autoOperationDao.getAllOperationsFamilyIncome(debitId);
    }

    public List<AutoOperationIncome> getAllOperationsPersonalIncome(BigInteger debitId) {
        logger.debug("[getAllTodayOperationsPersonalIncome]" + debugStartMessage + "[debitId = " + debitId + "]");
        ObjectsCheckUtils.isNotNull(debitId);
        return autoOperationDao.getAllOperationsPersonalIncome(debitId);
    }

    public List<AutoOperationExpense> getAllOperationsPersonalExpense(BigInteger debitId) {
        logger.debug("[getAllTodayOperationsPersonalExpense]" + debugStartMessage + "[debitId = " + debitId + "]");
        ObjectsCheckUtils.isNotNull(debitId);
        return autoOperationDao.getAllOperationsPersonalExpense(debitId);
    }

    public List<AbstractAutoOperation> getAllOperationsPersonal(BigInteger debitId) {
        logger.debug("[getAllTodayOperationsPersonal]" + debugStartMessage + "[debitId = " + debitId + "]");
        ObjectsCheckUtils.isNotNull(debitId);
        List<AbstractAutoOperation> allOperationsList = new ArrayList<>(getAllOperationsPersonalExpense(debitId));
        allOperationsList.addAll(getAllOperationsPersonalIncome(debitId));
        ObjectsCheckUtils.collectionIsEmpty(Collections.singletonList(allOperationsList));
        return allOperationsList;
    }

    public List<AbstractAutoOperation> getAllOperationsFamily(BigInteger debitId) {
        logger.debug("[getAllTodayOperationsFamily]" + debugStartMessage + "[debitId = " + debitId + "]");
        ObjectsCheckUtils.isNotNull(debitId);
        List<AbstractAutoOperation> allOperationsList = new ArrayList<>(getAllOperationsFamilyExpense(debitId));
        allOperationsList.addAll(getAllOperationsFamilyIncome(debitId));
        ObjectsCheckUtils.collectionIsEmpty(Collections.singletonList(allOperationsList));
        return allOperationsList;
    }

    public boolean checkMaxDayInCurrentMonth(int days) {
        Calendar myCalendar = (Calendar) Calendar.getInstance().clone();
        int max_date = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days > max_date) {
            return false;
        }
        return true;
    }
}
