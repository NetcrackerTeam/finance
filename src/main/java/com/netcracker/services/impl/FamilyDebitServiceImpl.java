package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.dao.impl.FamilyAccountDebitDaoImpl;
import com.netcracker.models.*;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.FamilyDebitService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.*;

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
        return familyAccountDebitDao.createFamilyAccount(familyDebitAccount);
    }

    @Override
    public FamilyDebitAccount getFamilyDebitAccount(BigInteger id) {
        return familyAccountDebitDao.getFamilyAccountById(id);
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger id) {
        familyAccountDebitDao.deleteFamilyAccount(id);
    }

    @Override
    public void addUserToAccount(BigInteger accountId, BigInteger userId) {
        try {
            User tempUser = userDao.getUserById(userId);
            if(tempUser == null){
                throw new NullPointerException("User " + userId + " is NULL");
            }
            if (tempUser.getUserStatusActive() == UserStatusActive.NO ) {
                logger.error("The user " + userId + " is unActive");
            } else {
                Collection<User> partisipants = familyAccountDebitDao.getParticipantsOfFamilyAccount(accountId);
                if(partisipants == null){
                    throw new NullPointerException("Partisipants of family debit account by id " + accountId + " is NULL");
                }
                for (User participant : partisipants) {
                    if (participant.getId().equals(userId)) {
                        logger.error("The user " + participant.getId() + " is has family account");
                    }
                }
                familyAccountDebitDao.addUserToAccountById(accountId, userId);
            }
        }
        catch (NullPointerException e){
                logger.error(e.getMessage() , e);
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId) {
        familyAccountDebitDao.deleteUserFromAccountById(accountId, userId);
    }

    @Override
    public Collection<AbstractAccountOperation> getHistory(BigInteger familyAccountId, Date date) {
        Collection<AbstractAccountOperation> objects = new ArrayList<>();
        Collection<AccountIncome> incomes = operationDao.getIncomesFamilyAfterDateByAccountId(familyAccountId, date);
        Collection<AccountExpense> expenses = operationDao.getExpensesFamilyAfterDateByAccountId(familyAccountId, date);
        objects.addAll(incomes);
        objects.addAll(expenses);
        return objects;
    }
}