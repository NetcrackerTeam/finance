package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.PersonalCreditDebtService;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

@Service
public class PersonalCreditServiceImpl implements PersonalCreditService {

    @Autowired
    CreditAccountDao creditAccountDao;

    @Autowired
    CreditOperationDao creditOperationDao;

    @Autowired
    @Qualifier("personalCreditDebt")
    PersonalCreditDebtService creditDebtService;

    @Override
    public void createPersonalCredit(BigInteger id, PersonalCreditAccount creditAccount) {
        creditAccountDao.createPersonalCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deletePersonalCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusPersonalCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addPersonalCreditPayment(BigInteger id, long amount, Date date) {
        //ToDo: check with validation amount
        PersonalCreditAccount creditAccount = getPersonalCreditAcount(id);
        createCreditOperation(id, amount, date);
        long updatedAmount = creditAccount.getAmount() + amount;
        //ToDo: check if has debt
        creditAccountDao.updatePersonalCreditPayment(id, updatedAmount);
    }

    @Override
    public void increaseDebt(BigInteger id, long amount) {

    }

    @Override
    public void decreaseDebt(BigInteger id, long amount) {

    }

    @Override
    public Collection<PersonalCreditAccount> getPersonalCredits(BigInteger id) {
        return creditAccountDao.getAllPersonalCreditsByAccountId(id);
    }

    @Override
    public Collection<CreditOperation> getAllPersonalCreditOperations(BigInteger id) {
        return creditOperationDao.getAllCreditOperationsByCreditPersonalId(id);
    }

    @Override
    public PersonalCreditAccount getPersonalCreditAcount(BigInteger id) {
        return creditAccountDao.getPersonalCreditById(id);
    }

    private void createCreditOperation(BigInteger id, long amount, Date date) {
        CreditOperation creditOperation = new CreditOperation(amount, date);
        creditOperationDao.createPersonalCreditOperation(creditOperation, id);
    }

}
