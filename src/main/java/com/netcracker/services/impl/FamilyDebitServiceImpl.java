package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.services.FamilyDebitService;
import com.netcracker.services.UserService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

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
        ObjectsCheckUtils.isNotNull(id);
        return familyAccountDebitDao.getFamilyAccountById(id);
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger accountId, BigInteger userId) {
        Collection<User> participants = this.getParticipantsOfFamilyAccount(accountId);
        if (participants.size() == 1) {
            logger.debug("Entering update(deleteFamilyDebitAccount=" + accountId + ")");
            familyAccountDebitDao.deleteFamilyAccount(accountId, userId);
        } else {
            logger.error("the family debit account " + accountId + " has participants");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.ERROR_MESSAGE_FAMILY_PARTICIPANTS);
        }
    }

    @Override
    public boolean addUserToAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccount=" + accountId + " " + userId + ")");
        FamilyDebitAccount familyDebitAccount = this.getFamilyDebitAccount(accountId);
        FamilyAccountStatusActive statusFamily = familyDebitAccount.getStatus();
        User tempUser = userDao.getUserById(userId);
        boolean statusUser = userService.isUserActive(userId);
        boolean userFamilyAccount = userService.isUserHaveFamilyAccount(userId);
        if (!statusUser) {
            logger.error("The user " + userId + " is unActive");
            throw new UserException(UserException.ERROR_MESSAGE_USER_STATUS, tempUser);
        } else if (FamilyAccountStatusActive.NO.equals(statusFamily)) {
            logger.error("The family account " + accountId + " is unActive");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.ERROR_MESSAGE_FAMILY_STATUS, familyDebitAccount);
        } else if (!userFamilyAccount) {
            logger.error("The user " + tempUser + " own family account");
            throw new UserException(UserException.ERROR_MESSAGE_USER_EXIST_FAMILY, tempUser);
        } else {
            Collection<User> participants = this.getParticipantsOfFamilyAccount(accountId);
            for (User participant : participants) {
                if (participant.getId() == null) {
                    logger.error("The userId " + userId + " is NULL");
                    throw new UserException(UserException.ERROR_MESSAGE_USER, participant);
                } else if (userId.equals(participant.getId())) {
                    logger.error("The user " + participant.getId() + " is has family account");
                    throw new UserException(UserException.ERROR_MESSAGE_USER_EXIST_FAMILY, participant);
                }
            }
            familyAccountDebitDao.addUserToAccountById(accountId, userId);
            logger.debug("Entering insert success(addUserToAccount=" + accountId + " " + userId + ")");
            return true;
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering update(deleteUserFromAccount=" + accountId + " " + userId + ")");
        ObjectsCheckUtils.isNotNull(accountId, userId);
        User user = userDao.getUserById(userId);
        if (!accountId.equals(user.getFamilyDebitAccount())) {
            familyAccountDebitDao.deleteUserFromAccountById(accountId, userId);
        } else {
            logger.error("The user " + userId + "is owner of family account " + accountId);
            throw new UserException(UserException.ERROR_MESSAGE_OWNER);
        }
    }

    @Override
    public Collection<AbstractAccountOperation> getHistory(BigInteger familyAccountId, LocalDate date) {
        logger.debug("Entering select(getHistory=" + familyAccountId + " " + date + ")");
        ObjectsCheckUtils.isNotNull(familyAccountId, date);
        Collection<AbstractAccountOperation> transactions = new ArrayList<>();
        Collection<AccountIncome> incomes = operationDao.getIncomesFamilyAfterDateByAccountId(familyAccountId, date);
        Collection<AccountExpense> expenses = operationDao.getExpensesFamilyAfterDateByAccountId(familyAccountId, date);
        transactions.addAll(incomes);
        transactions.addAll(expenses);
        logger.debug("Entering select success(getHistory=" + familyAccountId + " " + date + ")");
        return transactions;

    }

    @Override
    public Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId) {
        ObjectsCheckUtils.isNotNull(accountId);
        logger.debug("Entering list(getParticipantsOfFamilyAccount=" + accountId + ")");
        return familyAccountDebitDao.getParticipantsOfFamilyAccount(accountId);
    }
}