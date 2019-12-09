package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.FamilyAccountDebitDaoImpl;
import com.netcracker.models.*;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.FamilyDebitService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class FamilyDebitServiceImpl implements FamilyDebitService {

    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OperationDao operationDao;

    private static final Logger logger = Logger.getLogger(FamilyAccountDebitDaoImpl.class);

    @Override
    public FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount) {
        logger.debug("Entering insert(createFamilyDebitAccount=" + familyDebitAccount + ")");
        return familyAccountDebitDao.createFamilyAccount(familyDebitAccount);
    }

    @Override
    public FamilyDebitAccount getFamilyDebitAccount(BigInteger id) {
        logger.debug("Entering select(getFamilyDebitAccount=" + id + ")");
        return familyAccountDebitDao.getFamilyAccountById(id);
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger id) {
        logger.debug("Entering update(deleteFamilyDebitAccount=" + id + ")");
        familyAccountDebitDao.deleteFamilyAccount(id);
    }

    @Override
    public void addUserToAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccount=" + accountId + " " + userId + ")");
        try {
            User tempUser = userDao.getUserById(userId);
            if (tempUser.getUserStatusActive() == UserStatusActive.NO) {
                logger.error("The user " + userId + " is unActive");
            } else {
                Collection<User> participants = familyAccountDebitDao.getParticipantsOfFamilyAccount(accountId);
                for (User participant : participants) {
                    if (participant.getId().equals(userId)) {
                        logger.error("The user " + participant.getId() + " is has family account");
                    }
                }
                familyAccountDebitDao.addUserToAccountById(accountId, userId);
                logger.debug("Entering insert success(addUserToAccount=" + accountId + " " + userId + ")");
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering update(deleteUserFromAccount=" + accountId + " " + userId + ")");
        familyAccountDebitDao.deleteUserFromAccountById(accountId, userId);
    }

    @Override
    public Collection<AbstractAccountOperation> getHistory(BigInteger familyAccountId, Date date) {
        logger.debug("Entering select(getHistory=" + familyAccountId + " " + date + ")");
        Collection<AbstractAccountOperation> objects = new ArrayList<>();
        Collection<AccountIncome> incomes = operationDao.getIncomesFamilyAfterDateByAccountId(familyAccountId, date);
        Collection<AccountExpense> expenses = operationDao.getExpensesFamilyAfterDateByAccountId(familyAccountId, date);
        objects.addAll(incomes);
        objects.addAll(expenses);
        logger.debug("Entering select success(getHistory=" + familyAccountId + " " + date + ")");
        return objects;
    }
}