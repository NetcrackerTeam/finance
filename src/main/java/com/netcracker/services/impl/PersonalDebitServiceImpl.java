package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.services.PersonalDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonalDebitServiceImpl implements PersonalDebitService {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private CreditAccountDao creditAccountDao;

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
    public void addCreditAccount(BigInteger id, PersonalCreditAccount creditAccount) {
        logger.info(
                "addCreditAccount() method. projectId = " + id + "creditAccount = " + creditAccount );
        creditAccountDao.createPersonalCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public List<Object> getHistory(BigInteger personal_account_id) {
        logger.info(
                "getHistory() method. projectId = " + personal_account_id);
        ArrayList<AccountIncome> result;
        result = personalDebitAccountDao.getIncomesOfPersonalAccount(personal_account_id);
        ArrayList<AccountExpense> expenses;
        expenses = personalDebitAccountDao.getExpensesOfPersonalAccount(personal_account_id);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(result);
        objects.addAll(expenses);
        return objects;
    }
}
