package com.netcracker.dao.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.impl.mapper.FamilyAccountDebitMapper;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;
import com.netcracker.services.utils.ExceptionMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Collection;

@Repository
public class FamilyAccountDebitDaoImpl implements FamilyAccountDebitDao {

    private static final Logger logger = Logger.getLogger(FamilyAccountDebitDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public FamilyAccountDebitDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public FamilyDebitAccount getFamilyAccountById(BigInteger id) {
        logger.debug("Entering select(getFamilyAccountById=" + id + ")");
        try {
            FamilyDebitAccount familyDebitAccount = this.template.queryForObject(FIND_FAMILY_ACCOUNT_BY_ID, new Object[]{id}, new FamilyAccountDebitMapper());
            logger.debug("Entering select success(getFamilyAccountById=" + id + ")");
            return familyDebitAccount;
        } catch (EmptyResultDataAccessException ex) {
            logger.error("the family debit account  id = " + id + " is wrong");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_FAMILY);
        }
    }

    @Override
    public FamilyDebitAccount createFamilyAccount(FamilyDebitAccount familyDebitAccount) {
        logger.debug("Entering insert(FamilyDebitAccount=" + familyDebitAccount + ")");
        this.template.update(ADD_NEW_FAMILY_ACCOUNT, familyDebitAccount.getObjectName(),
                familyDebitAccount.getAmount(),
                familyDebitAccount.getStatus().getId().toString(),
                familyDebitAccount.getOwner().getId(),
                familyDebitAccount.getOwner().getId());
        logger.debug("Entering insert success(FamilyDebitAccount=" + familyDebitAccount + ")");
        return familyDebitAccount;
    }

    @Override
    public void deleteFamilyAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering unactive(deleteFamilyAccount=" + accountId + ")");
        this.template.update(SET_FAMILY_ACCOUNT_UNACTIVE, accountId);
        logger.debug("Entering unactive success(deleteFamilyAccount=" + accountId + ")");
        logger.debug("Entering delete reference user(deleteFamilyAccount=" + userId + ")");
        this.template.update(DELETE_REFERENCE_FROM_USER_TO_ACCOUNT, userId, accountId);
        logger.debug("Entering delete success(deleteFamilyAccount=" + userId + ")");
    }

    @Override
    public void addUserToAccountById(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccountById=" + accountId + " " + userId + ")");
        this.template.update(ADD_USER_BY_ID, accountId, userId);
        logger.debug("Entering insert success(addUserToAccountById=" + accountId + " " + userId + ")");
    }

    @Override
    public void deleteUserFromAccountById(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering delete(deleteUserFromAccountById=" + accountId + " " + userId + ")");
        this.template.update(DELETE_USER_FROM_FAMILY_ACCOUNT, accountId, userId);
        logger.debug("Entering delete success(deleteUserFromAccountById=" + accountId + " " + userId + ")");
    }

    @Override
    public void updateAmountOfFamilyAccount(BigInteger accountId, Long amount) {
        logger.debug("Entering update amount(deleteFamilyAccount=" + accountId + " " + amount + ")");
        this.template.update(UPDATE_FALIMY_ACCOUNT_AMOUNT, amount, accountId);
        logger.debug("Entering update amount success(deleteFamilyAccount=" + accountId + " " + amount + ")");
    }

    @Override
    public Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId) {
        logger.debug("Entering list(getParticipantsOfFamilyAccount=" + accountId + ")");
        try {
            Collection<User> users = this.template.query(GET_PARTICIPANTS, new Object[]{accountId}, new UserDaoMapper());
            logger.debug("Entering list success(getParticipantsOfFamilyAccount=" + accountId + ")");
            return users;
        } catch (EmptyResultDataAccessException ex) {
            logger.error("the family debit account  " + accountId + " doesn`t exist");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_FAMILY);
        }
    }
    @Override
    public Collection<User> getAllParticipantsOfFamilyAccounts() {
        logger.debug("Entering list(getAllParticipantsOfFamilyAccount)");
        try {
            Collection<User> users = this.template.query(GET_ALL_PARTICIPANTS, new UserDaoMapper());
            logger.debug("Entering list success(getAllParticipantsOfFamilyAccount)");
            return users;
        } catch (EmptyResultDataAccessException ex) {
            logger.error("the family debit accounts doesn`t have participants");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_FAMILY);
        }
    }
    @Override
    public Collection<FamilyDebitAccount> getAllFamilyAccounts() {
        logger.debug("Entering list(getAllFamilyAccounts)");
        try {
            Collection<FamilyDebitAccount> accounts = this.template.query(GET_ALL_FAMILY_ACCOUNTS, new FamilyAccountDebitMapper());
            logger.debug("Entering list success(getAllFamilyAccounts)");
            return accounts;
        } catch (EmptyResultDataAccessException ex) {
            logger.error("the family debit accounts doesn`t exists");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_FAMILY);
        }
    }
}
