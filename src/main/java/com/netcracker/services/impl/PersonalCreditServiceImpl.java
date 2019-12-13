package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.Debt;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.PersonalCreditService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

import static com.netcracker.services.utils.CreditUtils.getTotalCreditPayment;

@Service
public class PersonalCreditServiceImpl implements PersonalCreditService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalCreditServiceImpl.class);

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
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);

        if (debitAccount.getAmount() < amount) {
            long remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                    creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
            if (remainToPay > amount)
                addPayment(creditAccount, debitAccount, amount);
            else {
                logger.error("Remain to pay for credit {}. Wanted {}", remainToPay, amount);
                throw new CreditAccountException(String.format("Left to pay only %d", remainToPay), creditAccount);
            }
        } else {
            logger.error("Not enough money on debit account by id = {}", idDebitAccount);
            throw new CreditAccountException("Not enough money on debit account", creditAccount);
        }
    }


    @Override
    public boolean addPersonalCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDate(), creditAccount.getDateTo());

        long remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
        if (remainToPay < amount)
            amount = remainToPay;
        if (debitAccount.getAmount() < amount) {
            logger.debug("Not enough money on debit account by id = {}. Needed more then {}", idDebitAccount, amount);
            return false;
        }
        addPayment(creditAccount, debitAccount, amount);
        logger.debug("Payment was completed successfully");
        return true;
    }


    @Override
    public void increaseDebt(BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idCredit);
        PersonalCreditAccount creditAccount = creditAccountDao.getPersonalCreditById(idCredit);

        ObjectsCheckUtils.isNotNull(creditAccount);
        Debt debt = creditAccount.getDebt();

        LocalDate newDateTo;
        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

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
        logger.debug("Debt increase was completed successfully");
    }

    @Override
    public void addAutoDebtRepayment(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDebt());
        decreaseDebt(creditAccount.getDebt(), amount);
        addPayment(creditAccount, debitAccount, amount);
        logger.debug("Repayment was completed successfully");
    }

    private void decreaseDebt(Debt debt, long amount) {
        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

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

    private void addPayment(PersonalCreditAccount creditAccount, PersonalDebitAccount debitAccount, long amount) {
        long actualDebitAmount = debitAccount.getAmount();
        debitAccountDao.updateAmountOfPersonalAccount(debitAccount.getId(), actualDebitAmount - amount);
        creditOperationDao.createPersonalCreditOperation(amount, DateUtils.localDateToDate(LocalDate.now()), creditAccount.getCreditId());
        long updatedAmount = creditAccount.getPaidAmount() + amount;
        creditAccountDao.updatePersonalCreditPayment(creditAccount.getCreditId(), updatedAmount);
        long monthPayment = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
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
