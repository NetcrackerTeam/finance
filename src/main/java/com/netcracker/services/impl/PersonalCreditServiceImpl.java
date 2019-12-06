package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.Debt;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.PersonalCreditDebtService;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
public class PersonalCreditServiceImpl implements PersonalCreditService {

    @Autowired
    CreditAccountDao creditAccountDao;

    @Autowired
    CreditOperationDao creditOperationDao;

    @Autowired
    PersonalDebitAccountDao debitAccountDao;

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
    public void addPersonalCreditPayment(BigInteger idAccount, BigInteger idCredit, long amount) {
        PersonalCreditAccount creditAccount = getPersonalCreditAcount(idCredit);
        addPayment(creditAccount, idAccount, amount);
    }

    @Override
    public void addPersonalCreditPaymentAuto(BigInteger idAccount, BigInteger idCredit, long amount) {
        PersonalCreditAccount creditAccount = getPersonalCreditAcount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idAccount);
        long debitAmount = debitAccount.getAmount();
        if (debitAmount < amount) {
            increaseDebt(creditAccount.getDebt(), amount);
        } else {
            if (creditAccount.getDebt().getDateFrom() != null) {
                decreaseDebt(creditAccount.getDebt(), amount);
            }
            addPayment(creditAccount, idAccount, amount);
        }
    }

    private void increaseDebt(Debt debt, long amount) {
        Date newDateTo;
        if (debt.getDateFrom() == null) {
            debt.setDateFrom(new Date());
            creditDebtService.changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
            newDateTo = addOneMonth(debt.getDateFrom(), 1);
            creditDebtService.changeDebtAmount(debt.getDebtId(), amount);
        } else {
            newDateTo = addOneMonth(debt.getDateFrom(), 1);
            creditDebtService.changeDebtAmount(debt.getDebtId(), debt.getAmountDebt() + amount);
        }
        debt.setDateTo(newDateTo);
        creditDebtService.changeDebtDateTo(debt.getDebtId(), debt.getDateTo());
    }

    private void decreaseDebt(Debt debt, long amount) {

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

    @Override
    public long getMonthPaymentAmount(PersonalCreditAccount personalCreditAccount) {
        return calculateMonthPayment(personalCreditAccount.getDate(),
                personalCreditAccount.getDateTo(),
                personalCreditAccount.getAmount(),
                personalCreditAccount.getCreditRate());
    }

    private void createCreditOperation(BigInteger id, long amount, Date date) {
        CreditOperation creditOperation = new CreditOperation(amount, date);
        creditOperationDao.createPersonalCreditOperation(creditOperation, id);
    }

    private long calculateMonthPayment(LocalDate dateFrom, LocalDate dateTo, long amount, long rate) {
        int months = Period.between(dateFrom, dateTo).getMonths();
        long oneMonthRate = rate / 12;
        long allowance = (amount / 100) * oneMonthRate;
        long paymentWithoutRate = amount / months;
        return paymentWithoutRate + allowance;
    }

    private void addPayment(PersonalCreditAccount creditAccount, BigInteger accountDebitId, long amount) {
        creditOperationDao.createPersonalCreditOperation(new CreditOperation(amount, new Date()), creditAccount.getCreditId());
        // ToDo: decrease money on debit account
        long updatedAmount = creditAccount.getAmount() + amount;
        creditAccountDao.updatePersonalCreditPayment(creditAccount.getCreditId(), updatedAmount);
    }

    public static Date addOneMonth(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }


}
