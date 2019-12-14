package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.exception.OperationException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.services.FamilyDebitService;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        if (id == null) {
            logger.error("the family debit account  " + id + " is null");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamily());
        } else {
            try {
                return familyAccountDebitDao.getFamilyAccountById(id);
            } catch (EmptyResultDataAccessException ex) {
                logger.error("the family debit account  " + id + " is null");
                throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamily());
            }
        }
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger id) {
        Collection<User> participants = this.getParticipantsOfFamilyAccount(id);
        if (participants.size() == 1) {
            logger.debug("Entering update(deleteFamilyDebitAccount=" + id + ")");
            familyAccountDebitDao.deleteFamilyAccount(id);
        } else {
            logger.error("the family debit account " + id + " has participants");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamilyParticipants());
        }
    }

    @Override
    public boolean addUserToAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccount=" + accountId + " " + userId + ")");
        FamilyDebitAccount familyDebitAccount = this.getFamilyDebitAccount(accountId);
        FamilyAccountStatusActive statusFamily = familyDebitAccount.getStatus();
        User tempUser = userDao.getUserById(userId);
        boolean statusUser = userService.isUserActive(userId);
        boolean userFamilyAccount = userService.isUserHaveFamilyAccount(accountId);
        if (!statusUser) {
            logger.error("The user " + userId + " is unActive");
            throw new UserException(UserException.getErrorMessageUserStatus(), tempUser);
        } else if (FamilyAccountStatusActive.NO.equals(statusFamily)) {
            logger.error("The family account " + accountId + " is unActive");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamilyStatus(), familyDebitAccount);
        } else if (!userFamilyAccount) {
            logger.error("The user " + tempUser + " own family account");
            throw new UserException(UserException.getErrorMessageUserExistFamily(),tempUser);
        } else {
            Collection<User> participants = this.getParticipantsOfFamilyAccount(accountId);
            for (User participant : participants) {
                if (participant.getId() == null) {
                    logger.error("The userId " + userId + " is NULL");
                    throw new UserException(UserException.getErrorMessageUser(), participant);
                } else if (userId.equals(participant.getId())) {
                    logger.error("The user " + participant.getId() + " is has family account");
                    throw new UserException(UserException.getErrorMessageUserExistFamily(), participant);
                }
            }
            familyAccountDebitDao.addUserToAccountById(accountId, userId);
            logger.debug("Entering insert success(addUserToAccount=" + accountId + " " + userId + ")");
            return true;
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId) {
        if (accountId == null || userId == null) {
            if (accountId == null) {
                logger.error("the family debit account  " + accountId + "  is null");
                throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamily());
            } else {
                logger.error("The userId " + userId + " is NULL");
                throw new UserException(UserException.getErrorMessageUser());
            }
        } else {
            logger.debug("Entering update(deleteUserFromAccount=" + accountId + " " + userId + ")");
            familyAccountDebitDao.deleteUserFromAccountById(accountId, userId);
        }
    }

    @Override
    public Collection<AbstractAccountOperation> getHistory(BigInteger familyAccountId, LocalDate date) {
        if (familyAccountId == null) {
            logger.error("the family debit account  " + familyAccountId + "  is null");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamily());
        } else if (date == null) {
            logger.error("The transactions " + date + " is NULL");
            throw new OperationException("The transactions is doesn`t exist");
        } else {
            logger.debug("Entering select(getHistory=" + familyAccountId + " " + date + ")");
            Collection<AbstractAccountOperation> transactions = new ArrayList<>();
            try {
                Collection<AccountIncome> incomes = operationDao.getIncomesFamilyAfterDateByAccountId(familyAccountId, date);
                Collection<AccountExpense> expenses = operationDao.getExpensesFamilyAfterDateByAccountId(familyAccountId, date);
                transactions.addAll(incomes);
                transactions.addAll(expenses);
                logger.debug("Entering select success(getHistory=" + familyAccountId + " " + date + ")");
                return transactions;
            } catch (EmptyResultDataAccessException ex) {
                logger.error("The transactions " + date + " " + familyAccountId + " is NULL");
                throw new OperationException("The transactions is doesn`t exist");
            }
        }

    }

    @Override
    public Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId) {
        if (accountId == null) {
            logger.error("the family debit account  " + accountId + "  is null");
            throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamily());
        } else {
            logger.debug("Entering list(getParticipantsOfFamilyAccount=" + accountId + ")");
            try {
                return familyAccountDebitDao.getParticipantsOfFamilyAccount(accountId);
            } catch (EmptyResultDataAccessException ex) {
                logger.error("the family debit account  " + accountId + " doesn`t exist");
                throw new FamilyDebitAccountException(FamilyDebitAccountException.getErrorMessageFamily());
            }
        }
    }
}