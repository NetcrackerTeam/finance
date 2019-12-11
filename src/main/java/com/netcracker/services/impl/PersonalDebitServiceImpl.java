package com.netcracker.services.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.services.PersonalDebitService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class PersonalDebitServiceImpl implements PersonalDebitService {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private OperationDao operationDao;

    private static final Logger logger = Logger.getLogger(PersonalDebitServiceImpl.class);

    @Override
    public PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount) {
        logger.debug(
                "createPersonalDebitAccount() method. projectId = " + personalDebitAccount);
        return personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }

    @Override
    public void deletePersonalDebitAccount(BigInteger accountId, BigInteger userId) {
        logger.debug(
                "deletePersonalDebitAccount method. account_id = " + accountId + " user_id = " + userId);
        personalDebitAccountDao.deletePersonalAccountById(accountId, userId);
    }

    @Override
    public PersonalDebitAccount getPersonalDebitAccount(BigInteger id) {
        logger.debug(
                "getPersonalDebitAccount() method. projectId = " + id);
        return personalDebitAccountDao.getPersonalAccountById(id);
    }


    @Override
    public Collection<AbstractAccountOperation> getHistory(BigInteger personalAccountId, Date date) {
        logger.debug(
                "getHistory() method. projectId = " + personalAccountId + " " + date);
        Collection<AbstractAccountOperation> trans = new ArrayList<>();
        Collection<AccountIncome> incomes = operationDao.getIncomesPersonalAfterDateByAccountId(personalAccountId, date);
        Collection<AccountExpense> expenses = operationDao.getExpensesPersonalAfterDateByAccountId(personalAccountId, date);
        trans.addAll(incomes);
        trans.addAll(expenses);
        return trans;
    }
}
