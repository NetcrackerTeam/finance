package com.netcracker.services.impl;

import com.netcracker.controllers.MessageController;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.models.enums.ErrorVisibility;
import com.netcracker.services.OperationService;
import com.netcracker.services.PersonalCreditService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private OperationService operationService;

    @Override
    public void createPersonalCredit(BigInteger id, PersonalCreditAccount creditAccount) {
        if(!creditAccount.isCommodity()){
            double amount = debitAccountDao.getPersonalAccountById(id).getAmount();
            debitAccountDao.updateAmountOfPersonalAccount(id,
                    amount + creditAccount.getAmount());
            operationService.createPersonalOperationIncome(id, creditAccount.getAmount(), LocalDateTime.now(), CategoryIncome.CREDIT);
        }
        creditAccountDao.createPersonalCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deletePersonalCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusPersonalCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addPersonalCreditPayment(BigInteger idDebitAccount, BigInteger idCredit, double amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);

        makeUserPayment(debitAccount, creditAccount, amount);
        addPayment(creditAccount, debitAccount, amount);
    }

    void makeUserPayment(AbstractDebitAccount debitAccount, AbstractCreditAccount creditAccount, double amount) {
        if (debitAccount.getAmount() < amount) {
            logger.error("Not enough money on debit account by id = {}", debitAccount.getId());
            throw new CreditAccountException(ExceptionMessages.NOT_ENOUGH_MONEY_ERROR, creditAccount, ErrorVisibility.VISIBLE);
        }
        double remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
        if (remainToPay < amount) {
            logger.error("Remain to pay for credit {}. Wanted {}", remainToPay, amount);
            throw new CreditAccountException(String.format(ExceptionMessages.LEFT_TO_PAY_ERROR, remainToPay), creditAccount, ErrorVisibility.VISIBLE);
        }
    }

    @Override
    public boolean addPersonalCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, double amount) {
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

    boolean makeAutoPayment(AbstractDebitAccount debitAccount, AbstractCreditAccount creditAccount, double amount) {
        double remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
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
    public void increaseDebt(BigInteger idCredit, double amount) {
        ObjectsCheckUtils.isNotNull(idCredit);
        PersonalCreditAccount creditAccount = creditAccountDao.getPersonalCreditById(idCredit);

        ObjectsCheckUtils.isNotNull(creditAccount);
        Debt debt = creditAccount.getDebt();


        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

        Debt changedDebt = makeDebtIncrease(debt, amount);

        changeDebt(changedDebt);
    }

    Debt makeDebtIncrease (Debt debt, double amount) {
        LocalDate newDateTo;
        if (debt.getAmountDebt() == 0) {
            debt.setDateFrom(LocalDate.now());
            debt.setAmountDebt(amount);
            newDateTo = DateUtils.addMonthsToDateCredit(LocalDate.now(), 1);
        } else {
            newDateTo = DateUtils.addMonthsToDateCredit(debt.getDateTo(), 1);
            double newAmount = debt.getAmountDebt() + amount;
            debt.setAmountDebt(newAmount);
        }
        debt.setDateTo(newDateTo);
        return debt;
    }



    @Override
    public void addAutoDebtRepayment(BigInteger idDebitAccount, BigInteger idCredit, double amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
        PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDebt());
        decreaseDebt(creditAccount.getDebt(), amount);
        addPayment(creditAccount, debitAccount, amount);
        logger.debug("Repayment was completed successfully");
    }

    private void decreaseDebt(Debt debt, double amount) {
        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

        Debt changedDebt = makeDebtDecrease(debt, amount);
        changeDebt(changedDebt);
    }

    Debt makeDebtDecrease (Debt debt, double amount) {

        Debt changedDebt = debt;

        LocalDate newDateFrom = DateUtils.addMonthsToDateCredit(changedDebt.getDateFrom(), 1);
        if (newDateFrom.equals(changedDebt.getDateTo())) {
            changedDebt.setAmountDebt(0);
            changedDebt.setDateFrom(null);
            changedDebt.setDateTo(null);
        } else {
            changedDebt.setDateFrom(newDateFrom);
            double newAmount = changedDebt.getAmountDebt() - amount;
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

    @Override
    public boolean doesCreditWithNameNotExist(BigInteger debitId, String name) {
        return creditAccountDao.getCreditsIdByNamePers(debitId, name).isEmpty();
    }

    public Status checkCreditName(String name) {
        if (NONE_NAME.equals(name))
            return new Status(false, MessageController.INVALID_NAME);
        if (!name.matches(REGULAR_FOR_NAME))
            return new Status(false, MessageController.CREDIT_NAME_ERROR);
        return new Status();
    }

    @Override
    public Status checkCreditData(AbstractCreditAccount creditAccount) {
        ObjectsCheckUtils.isNotNull(creditAccount, creditAccount.getName(), creditAccount.getAmount(),
                creditAccount.getDate(), creditAccount.getCreditRate(), creditAccount.getDateTo(), creditAccount.getMonthDay(),
                creditAccount.isCommodity());
        if (creditAccount.getAmount() < 1 || creditAccount.getAmount() > 100000000)
            return new Status(false, MessageController.INCORRECT_AMOUNT_BETWEEN);
        if (LocalDate.now().minusWeeks(1).isAfter(creditAccount.getDate()))
            return new Status(false, MessageController.START_DATE_ERROR);
        if (LocalDate.now().plusMonths(600).isBefore(creditAccount.getDateTo()))
            return new Status(false, MessageController.END_DATE_ERROR);
        if (creditAccount.getCreditRate() < 0 || creditAccount.getCreditRate() > 60)
            return new Status(false, MessageController.CREDIT_RATE_ERROR);
        if (creditAccount.getMonthDay() < 1 || creditAccount.getMonthDay() > 31)
            return new Status(false, MessageController.INVALID_DAY_OF_MONTH);
        return checkCreditName(creditAccount.getName());
    }

    private void addPayment(AbstractCreditAccount creditAccount, AbstractDebitAccount debitAccount, double amount) {
        double actualDebitAmount = debitAccount.getAmount();
        debitAccountDao.updateAmountOfPersonalAccount(debitAccount.getId(), actualDebitAmount - amount);
        operationService.createPersonalOperationExpense(debitAccount.getId(), amount, LocalDateTime.now(), CategoryExpense.CREDIT);
        creditOperationDao.createPersonalCreditOperation(amount, LocalDate.now(), creditAccount.getCreditId());
        double updatedAmount = creditAccount.getPaidAmount() + amount;
        creditAccountDao.updatePersonalCreditPayment(creditAccount.getCreditId(), updatedAmount);
        double totalPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate());
        if (totalPay == updatedAmount) {
            creditAccountDao.updateIsPaidStatusPersonalCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
        }
    }

    private void changeDebt(Debt debt) {
        changeDebtDateTo(debt.getDebtId(), debt.getDateTo());
        changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
        changeDebtAmount(debt.getDebtId(), debt.getAmountDebt());
    }

    private void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateTo(id, DateUtils.localDateToDateCredit(date));
    }

    private void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDateCredit(date));
    }

    private void changeDebtAmount(BigInteger id, double amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

}
