package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.models.*;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.models.enums.ErrorVisibility;
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
    private CreditAccountDao creditAccountDao;

    @Autowired
    private CreditOperationDao creditOperationDao;

    @Autowired
    private PersonalDebitAccountDao debitAccountDao;

    @Autowired
    private CreditDeptDao creditDeptDao;

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

        makeUserPayment(debitAccount, creditAccount, amount);
        addPayment(creditAccount, debitAccount, amount);
    }

    void makeUserPayment(AbstractDebitAccount debitAccount, AbstractCreditAccount creditAccount, long amount) {
        if (debitAccount.getAmount() > amount) {
            logger.error("Not enough money on debit account by id = {}", debitAccount.getId());
            throw new CreditAccountException("Not enough money on debit account", creditAccount, ErrorVisibility.VISIBLE);
        }
        long remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
        if (remainToPay < amount) {
            logger.error("Remain to pay for credit {}. Wanted {}", remainToPay, amount);
            throw new CreditAccountException(String.format("Left to pay only %d", remainToPay), creditAccount, ErrorVisibility.VISIBLE);
        }
    }

    @Override
    public boolean addPersonalCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDate(), creditAccount.getDateTo());

        boolean isSuccess = makeAutoPayment(debitAccount, creditAccount, amount);
        if (!isSuccess)
            return false;
        addPayment(creditAccount, debitAccount, amount);
        logger.debug("Payment was completed successfully");
        return true;
    }

    boolean makeAutoPayment(AbstractDebitAccount debitAccount, AbstractCreditAccount creditAccount, long amount) {
        long remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
        if (remainToPay < amount)
            amount = remainToPay;
        if (debitAccount.getAmount() < amount) {
            logger.debug("Not enough money on debit account by id = {}. Needed more then {}", debitAccount.getId(), amount);
            return false;
        }
        return true;
    }


    @Override
    public void increaseDebt(BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idCredit);
        PersonalCreditAccount creditAccount = creditAccountDao.getPersonalCreditById(idCredit);

        ObjectsCheckUtils.isNotNull(creditAccount);
        Debt debt = creditAccount.getDebt();


        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

        Debt changedDebt = makeDebtIncrease(debt, amount);

        changeDebt(changedDebt);
    }

    Debt makeDebtIncrease (Debt debt, long amount) {
        LocalDate newDateTo;
        if (debt.getAmountDebt() == 0) {
            debt.setDateFrom(LocalDate.now());
            debt.setAmountDebt(amount);
            newDateTo = DateUtils.addMonthsToDate(LocalDate.now(), 1);
        } else {
            newDateTo = DateUtils.addMonthsToDate(debt.getDateTo(), 1);
            long newAmount = debt.getAmountDebt() + amount;
            debt.setAmountDebt(newAmount);
        }
        debt.setDateTo(newDateTo);
        return debt;
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

        Debt changedDebt = makeDebtDecrease(debt, amount);
        changeDebt(changedDebt);
    }

    Debt makeDebtDecrease (Debt debt, long amount) {

        Debt changedDebt = debt;

        LocalDate newDateFrom = DateUtils.addMonthsToDate(changedDebt.getDateFrom(), 1);
        if (newDateFrom.equals(changedDebt.getDateTo())) {
            changedDebt.setAmountDebt(0);
            changedDebt.setDateFrom(null);
            changedDebt.setDateTo(null);
        } else {
            changedDebt.setDateFrom(newDateFrom);
            long newAmount = changedDebt.getAmountDebt() - amount;
            changedDebt.setAmountDebt(newAmount);
        }
        return changedDebt;
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

    private void addPayment(AbstractCreditAccount creditAccount, AbstractDebitAccount debitAccount, long amount) {
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

    private void changeDebt(Debt debt) {
        changeDebtDateTo(debt.getDebtId(), debt.getDateTo());
        changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
        changeDebtAmount(debt.getDebtId(), debt.getAmountDebt());
    }

    private void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateTo(id, DateUtils.localDateToDate(date));
    }

    private void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDate(date));
    }

    private void changeDebtAmount(BigInteger id, long amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

}
