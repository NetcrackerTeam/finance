package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.services.FamilyDebitService;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class FamilyDebitServiceImpl implements FamilyDebitService {

    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OperationDao operationDao;
    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(FamilyDebitServiceImpl.class);

    @Override
    public FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount) {
        logger.debug("Entering insert(createFamilyDebitAccount=" + familyDebitAccount + ")");
        return familyAccountDebitDao.createFamilyAccount(familyDebitAccount);
    }

    @Override
    public FamilyDebitAccount getFamilyDebitAccount(BigInteger id) {
        logger.debug("Entering select(getFamilyDebitAccount=" + id + ")");
        if (id == null) {
            logger.debug("the family debit account  " + id + " is null");
            throw new FamilyDebitAccountException("the family debit account doesn`t exist");
        } else  {
            FamilyDebitAccount familyDebitAccount = familyAccountDebitDao.getFamilyAccountById(id);
            if(familyDebitAccount == null){
                logger.debug("the family debit account  " + id + " is null");
                throw new FamilyDebitAccountException("the family debit account doesn`t exist");
            } else {
                return familyDebitAccount;
            }
        }
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger id) {
            ArrayList<User> participants = (ArrayList<User>) this.getParticipantsOfFamilyAccount(id);
            if (participants.size() == 1) {
                logger.debug("Entering update(deleteFamilyDebitAccount=" + id + ")");
                familyAccountDebitDao.deleteFamilyAccount(id);
            } else {
                logger.debug("the family debit account " + id + " has participants");
                throw new FamilyDebitAccountException("the family debit account has participants");
            }
    }

    @Override
    public boolean addUserToAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccount=" + accountId + " " + userId + ")");
        User tempUser = userDao.getUserById(userId);
        if (tempUser == null) {
            logger.debug("The user " + userId + " is NULL");
            throw new UserException("The user is doesn`t exist");
        } else {
            boolean statusUser = userService.isUserActive(userId);
            if (!statusUser) {
                logger.debug("The user " + userId + " is unActive");
                throw new UserException("The user is unactive", tempUser);
            } else {
                Collection<User> participants = this.getParticipantsOfFamilyAccount(accountId);
                    for (User participant : participants) {
                        if (participant.getId() == null) {
                            logger.debug("The userId " + userId + " is NULL");
                            throw new UserException("The user is doesn`t exist", participant);
                        } else if (userId.equals(participant.getId())) {
                            logger.debug("The user " + participant.getId() + " is has family account");
                            throw new UserException("The user has family debit account", participant);
                        }
                    }
                familyAccountDebitDao.addUserToAccountById(accountId, userId);
                logger.debug("Entering insert success(addUserToAccount=" + accountId + " " + userId + ")");
                return true;
            }
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId) {
        if (accountId == null || userId == null){
            if (accountId == null){
                logger.debug("the family debit account  " + accountId + "  is null");
                throw new FamilyDebitAccountException("the family debit account doesn`t exist");
            } else {
                logger.debug("The userId " + userId + " is NULL");
                throw new UserException("The user is doesn`t exist");
            }
        } else {
            logger.debug("Entering update(deleteUserFromAccount=" + accountId + " " + userId + ")");
            familyAccountDebitDao.deleteUserFromAccountById(accountId, userId);
        }
    }

    @Override
    public Collection<AbstractAccountOperation> getHistory(BigInteger familyAccountId, Date date) {
        if (familyAccountId == null || date == null){
            if (familyAccountId == null){
                logger.debug("the family debit account  " + familyAccountId + "  is null");
                throw new FamilyDebitAccountException("the family debit account doesn`t exist");
            } else {
                logger.debug("The transactions " + date + " is NULL");
/*?*/           throw new UserException("The transactions is doesn`t exist");
            }
        } else {
            logger.debug("Entering select(getHistory=" + familyAccountId + " " + date + ")");
            Collection<AbstractAccountOperation> transactions = new ArrayList<>();
            Collection<AccountIncome> incomes = operationDao.getIncomesFamilyAfterDateByAccountId(familyAccountId, date);
            Collection<AccountExpense> expenses = operationDao.getExpensesFamilyAfterDateByAccountId(familyAccountId, date);
            transactions.addAll(incomes);
            transactions.addAll(expenses);
            logger.debug("Entering select success(getHistory=" + familyAccountId + " " + date + ")");
            return transactions;
        }
    }

    @Override
    public Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId) {
        if (accountId == null){
                logger.debug("the family debit account  " + accountId + "  is null");
                throw new FamilyDebitAccountException("the family debit account doesn`t exist");
        } else {
            logger.debug("Entering list(getParticipantsOfFamilyAccount=" + accountId + ")");
            Collection<User> users =  familyAccountDebitDao.getParticipantsOfFamilyAccount(accountId);
            if (users == null){
                logger.debug("the family debit account  " + accountId + " doesn`t exist");
                throw new FamilyDebitAccountException("the family debit account doesn`t exist");
            } else {
                return users;
            }
        }
    }
}