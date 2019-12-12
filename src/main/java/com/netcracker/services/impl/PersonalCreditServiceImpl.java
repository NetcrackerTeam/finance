package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.Debt;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.PersonalCreditService;
import com.netcracker.services.utils.CreditUtils;
import com.netcracker.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

@Service
public class PersonalCreditServiceImpl implements PersonalCreditService {

    @Autowired
    CreditAccountDao creditAccountDao;

    @Autowired
    CreditOperationDao creditOperationDao;

    @Autowired
    PersonalDebitAccountDao debitAccountDao;

    @Autowired
    CreditDeptDao creditDeptDao;

    @Override
    public void createPersonalCredit(BigInteger id, PersonalCreditAccount creditAccount) {
        creditAccountDao.createPersonalCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deletePersonalCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusPersonalCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addPersonalCreditPayment(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);
        addPayment(creditAccount, debitAccount, amount);
    }

    @Override
    public boolean addPersonalCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);
        if (debitAccount.getAmount() < amount) {
            return false;
        } else
            addPayment(creditAccount, debitAccount, amount);
        return true;
    }

    @Override
    public void increaseDebt(BigInteger idCredit, long amount) {
        Debt debt = creditAccountDao.getPersonalCreditById(idCredit).getDebt();
        LocalDate newDateTo;
        if (debt.getAmountDebt() == 0) {
            debt.setDateFrom(LocalDate.now());
            changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
            changeDebtAmount(debt.getDebtId(), amount);
            newDateTo = DateUtils.addMonthsToDate(LocalDate.now(), 1);
        } else {
            newDateTo = DateUtils.addMonthsToDate(debt.getDateTo(), 1);
            changeDebtAmount(debt.getDebtId(), debt.getAmountDebt() + amount);
        }
        changeDebtDateTo(debt.getDebtId(), newDateTo);
    }

    @Override
    public void addAutoDebtRepayment(BigInteger idAccount, BigInteger idCredit, long amount) {
        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idAccount);
        decreaseDebt(creditAccount.getDebt(), amount);
        addPayment(creditAccount, debitAccount, amount);
    }

    private void decreaseDebt(Debt debt, long amount) {
        LocalDate newDateFrom = DateUtils.addMonthsToDate(debt.getDateFrom(), 1);
        if (newDateFrom.equals(debt.getDateTo())) {
            changeDebtDateFrom(debt.getDebtId(), null);
            changeDebtDateTo(debt.getDebtId(), null);
            changeDebtAmount(debt.getDebtId(), 0);
        } else {
            changeDebtDateFrom(debt.getDebtId(), newDateFrom);
            changeDebtAmount(debt.getDebtId(), debt.getAmountDebt() - amount);
        }
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
    public PersonalCreditAccount getPersonalCreditAccount(BigInteger id) {
        return creditAccountDao.getPersonalCreditById(id);
    }

    @Override
    public long getMonthPaymentAmount(PersonalCreditAccount personalCreditAccount) {
        return CreditUtils.calculateMonthPayment(personalCreditAccount.getDate(),
                personalCreditAccount.getDateTo(),
                personalCreditAccount.getAmount(),
                personalCreditAccount.getCreditRate());
    }

    private void addPayment(PersonalCreditAccount creditAccount, PersonalDebitAccount debitAccount, long amount) {
        long actualDebitAmount = debitAccount.getAmount();
        debitAccountDao.updateAmountOfPersonalAccount(debitAccount.getId(), actualDebitAmount - amount);
        creditOperationDao.createPersonalCreditOperation(amount, DateUtils.localDateToDate(LocalDate.now()), creditAccount.getCreditId());
        long updatedAmount = creditAccount.getPaidAmount() + amount;
        creditAccountDao.updatePersonalCreditPayment(creditAccount.getCreditId(), updatedAmount);
        long monthPayment = CreditUtils.getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate());
        if (monthPayment == updatedAmount) {
            creditAccountDao.updateIsPaidStatusPersonalCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
        }
    }

    public void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateTo(id, DateUtils.localDateToDate(date));
    }

    public void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDate(date));
    }

    public void changeDebtAmount(BigInteger id, long amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

}
