package com.netcracker.services.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.services.OperationService;
import com.netcracker.services.PersonalDebitService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PersonalDebitServiceImpl implements PersonalDebitService {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private OperationService operationService;

    private static final Logger logger = Logger.getLogger(PersonalDebitServiceImpl.class);

    @Override
    public PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount) {
        personalDebitAccount.setObjectName(personalDebitAccount.getOwner().getName() + ACCOUNT_NAME);
        logger.debug("createPersonalDebitAccount() method. projectId = " + personalDebitAccount);
        return personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }

    @Override
    public void deletePersonalDebitAccount(BigInteger accountId, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(accountId, userId);
        logger.debug("deletePersonalDebitAccount method. account_id = " + accountId + " user_id = " + userId);
        personalDebitAccountDao.deletePersonalAccountById(accountId, userId);
    }

    @Override
    public PersonalDebitAccount getPersonalDebitAccount(BigInteger id) {
        logger.debug("get personal debit account by id:" + id);
        ObjectsCheckUtils.isNotNull(id);
        PersonalDebitAccount debitAccount = personalDebitAccountDao.getPersonalAccountById(id);
        LocalDate startMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        Collection<AccountIncome> incomes = operationService.getIncomesPersonalAfterDateByAccountId(id, startMonthDate);
        Collection<AccountExpense> expenses = operationService.getExpensesPersonalAfterDateByAccountId(id, startMonthDate);
        debitAccount.setMonthIncome(incomes.stream().mapToDouble(AccountIncome::getAmount).sum());
        debitAccount.setMonthExpense(expenses.stream().mapToDouble(AccountExpense::getAmount).sum());
        return debitAccount;
    }


    public List<AbstractAccountOperation> getHistory(BigInteger accountId, LocalDate date) {
        logger.debug("Entering select(getHistory=" + accountId + " date :" + date + ")");
        return operationService.getAllPersonalOperations(accountId, date);
    }

    @Override
    public Collection<PersonalDebitAccount> getAllPersonalAccounts() {
        return personalDebitAccountDao.getAllPersonalAccounts();
    }

}
