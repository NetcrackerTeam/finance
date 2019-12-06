package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.services.PersonalDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class PersonalDebitServiceImpl implements PersonalDebitService {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private CreditAccountDao creditAccountDao;
    @Autowired
    private OperationDao operationDao;
    private static final Logger logger = Logger.getLogger(String.valueOf(PersonalDebitServiceImpl.class));

    @Override
    public PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount) {
        logger.info(
                "createPersonalDebitAccount() method. projectId = " + personalDebitAccount);
        return personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }

    @Override
    public void deletePersonalDebitAccount(BigInteger account_id, BigInteger user_id) {
        logger.info(
                "deletePersonalDebitAccount method. account_id = " + account_id + " user_id = " + user_id);
        personalDebitAccountDao.deletePersonalAccountById(account_id, user_id);
    }

    @Override
    public PersonalDebitAccount getPersonalDebitAccount(BigInteger id) {
        logger.info(
                "getPersonalDebitAccount() method. projectId = " + id);
        return personalDebitAccountDao.getPersonalAccountById(id);
    }


    @Override
    public Collection<Object> getHistory(BigInteger personal_account_id, Date date) {
        logger.info(
                "getHistory() method. projectId = " + personal_account_id);
        Collection<AccountIncome> incomes;
        incomes = operationDao.getIncomesPersonalAfterDateByAccountId(personal_account_id, date);
        Collection<AccountExpense> expenses;
        expenses = operationDao.getExpensesPersonalAfterDateByAccountId(personal_account_id, date);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(incomes);
        objects.addAll(expenses);
        return objects;
    }
}
