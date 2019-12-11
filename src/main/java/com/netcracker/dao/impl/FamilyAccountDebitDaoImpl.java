package com.netcracker.dao.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.impl.mapper.AccountExpenseMapper;
import com.netcracker.dao.impl.mapper.AccountIncomeMapper;
import com.netcracker.dao.impl.mapper.FamilyAccountDebitMapper;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        FamilyDebitAccount  familyDebitAccount = this.template.queryForObject(FIND_FAMILY_ACCOUNT_BY_ID, new Object[]{id}, new FamilyAccountDebitMapper());
        logger.debug("Entering select success(getFamilyAccountById=" + id + ")");
        return familyDebitAccount;
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
    public void deleteFamilyAccount(BigInteger id) {
        logger.debug("Entering unactive(deleteFamilyAccount=" + id + ")");
        this.template.update(SET_FAMILY_ACCOUNT_UNACTIVE, id);
        logger.debug("Entering unactive success(deleteFamilyAccount=" + id + ")");
    }

    @Override
    public void addUserToAccountById(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccountById=" + accountId + " " + userId + ")");
        this.template.update(ADD_USER_BY_ID, accountId,  userId);
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
        Collection<User> users =  this.template.query(GET_PARTICIPANTS, new Object[]{accountId}, new UserDaoMapper());
        logger.debug("Entering list success(getParticipantsOfFamilyAccount=" + accountId + ")");
        return users;
    }

    @Override
    public Collection<AccountIncome> getIncomesOfFamilyAccount(BigInteger accountId) {
        logger.debug("Entering list(getIncomesOfFamilyAccount=" + accountId + ")");
        Collection<AccountIncome> incomes = this.template.query(GET_INCOME_LIST, new Object[]{accountId}, new AccountIncomeMapper());
        logger.debug("Entering list succsess(getIncomesOfFamilyAccount=" + accountId + ")");
        return incomes;
    }

    @Override
    public Collection<AccountExpense> getExpensesOfFamilyAccount(BigInteger accountId) {
        logger.debug("Entering list(getExpensesOfFamilyAccount=" + accountId+ ")");
        Collection<AccountExpense> expenses = this.template.query(GET_EXPENSE_LIST, new Object[]{accountId},  new AccountExpenseMapper());
        logger.debug("Entering list success(getExpensesOfFamilyAccount=" + accountId + ")");
        return expenses;
    }
}
