package com.netcracker.dao.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.impl.mapper.PersonalDebitAccountMapper;
import com.netcracker.exception.PersonalDebitAccountException;
import com.netcracker.models.PersonalDebitAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;

@Repository
public class PersonalDebitAccountDaoImpl implements PersonalDebitAccountDao {
    private static final Logger logger = Logger.getLogger(PersonalDebitAccountDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public PersonalDebitAccountDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public PersonalDebitAccount getPersonalAccountById(BigInteger id) {
        logger.debug("Entering select(getPersonalAccountBy=" + id + ")");
        try {
            return this.template.queryForObject(GET_PERSONAL_ACCOUNT_BY_ID, new Object[]{(id)}, new PersonalDebitAccountMapper());
        } catch (EmptyResultDataAccessException ex) {
            logger.error("the Personal debit account  " + id + " is null");
            throw new PersonalDebitAccountException(PersonalDebitAccountException.getErrorMessagePersonal());
        }
    }

    @Override
    public PersonalDebitAccount createPersonalAccount(PersonalDebitAccount personalDebitAccount) {
        logger.debug("Entering insert(createPersonalAccount=" + personalDebitAccount + ")");
        this.template.update(CREATE_PERSONAL_ACCOUNT, new Object[]{
                personalDebitAccount.getObjectName(),
                personalDebitAccount.getAmount(),
                personalDebitAccount.getStatus().getId(),
                personalDebitAccount.getOwner().getId()
        });
        logger.debug("Entering insert success(createPersonalAccount=" + personalDebitAccount + ")");
        return personalDebitAccount;
    }

    @Override
    public void deletePersonalAccountById(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering unactive(deletePersonalAccount=" + accountId + ")");
        this.template.update(
                PERSONAL_ACCOUNT_IS_UNACTIVE,
                accountId
        );
        logger.debug("Entering unactive success(deletePersonalAccount=" + accountId + ")");

        logger.debug("Entering delete reference user(deletePersonalAccount=" + userId + ")");
        this.template.update(
                DELETE_PERSONAL_ACCOUNT,
                userId,
                accountId
        );
        logger.debug("Entering delete success(deletePersonalAccount=" + userId + ")");
    }

    @Override
    public void deletePersonalAccountByUserId(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering unactive(deletePersonalAccountByUser=" + accountId + ")");
        this.template.update(
                UNACTIVE_USER_FROM_PERSONAL_ACCOUNT,
                accountId,
                userId
        );
    }

    @Override
    public void updateAmountOfPersonalAccount(BigInteger accountId, double amount) {
        logger.debug("Entering update_amount(updateAmountPersonalAccount=" + accountId + " " + amount + ")");
        this.template.update(UPDATE_PERSONAL_ACCOUNT_AMOUNT, new Object[]{amount, accountId});
        logger.debug("Entering update amount success(updateAmountPersonalAccount=" + accountId + " " + amount + ")");
    }
}
