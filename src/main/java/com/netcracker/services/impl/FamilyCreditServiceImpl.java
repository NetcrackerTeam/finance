package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.FamilyCreditService;
import com.netcracker.services.OperationService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import static com.netcracker.services.utils.CreditUtils.getTotalCreditPayment;

@Service
public class FamilyCreditServiceImpl implements FamilyCreditService {
    private static final Logger logger = LoggerFactory.getLogger(FamilyCreditServiceImpl.class);

    @Autowired
    private CreditAccountDao creditAccountDao;

    @Autowired
    private CreditOperationDao creditOperationDao;

    @Autowired
    private FamilyAccountDebitDao debitAccountDao;

    @Autowired
    private CreditDeptDao creditDeptDao;

    @Autowired
    private PersonalCreditServiceImpl creditService;

    @Autowired
    private OperationService operationService;


    @Override
    public void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount, BigInteger userId) {
        if (!creditAccount.isCommodity()) {
            double amount = debitAccountDao.getFamilyAccountById(id).getAmount();
            debitAccountDao.updateAmountOfFamilyAccount(id,
                    amount + creditAccount.getAmount());
            operationService.createFamilyOperationIncome(userId, id, creditAccount.getAmount(), LocalDateTime.now(), CategoryIncome.CREDIT);
        }
        creditAccountDao.createFamilyCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deleteFamilyCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusFamilyCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addFamilyCreditPayment(BigInteger idDebitAccount, BigInteger idCredit, double amount, Date date, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
        FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);

        creditService.makeUserPayment(debitAccount, creditAccount, amount);
        addPayment(creditAccount, debitAccount, amount, userId);

    }

    @Override
    public boolean addFamilyCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, double amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
        FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDate(), creditAccount.getDateTo());

        boolean isSuccess = creditService.makeAutoPayment(debitAccount, creditAccount, amount);
        if (!isSuccess)
            return false;
        addPayment(creditAccount, debitAccount, amount, debitAccount.getOwner().getId());
        logger.debug("Payment was completed successfully");
        return true;
    }

    @Override
    public void increaseDebt(BigInteger idCredit, double amount) {
        ObjectsCheckUtils.isNotNull(idCredit);
        FamilyCreditAccount creditAccount = creditAccountDao.getFamilyCreditById(idCredit);

        ObjectsCheckUtils.isNotNull(creditAccount);
        Debt debt = creditAccount.getDebt();

        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

        Debt changedDebt = creditService.makeDebtIncrease(debt, amount);

        changeDebt(changedDebt);
    }


    @Override
    public void addAutoDebtRepayment(BigInteger idDebitAccount, BigInteger idCredit, double amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
        FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDebt());
        decreaseDebt(creditAccount.getDebt(), amount);
        addPayment(creditAccount, debitAccount, amount, debitAccount.getOwner().getId());
        logger.debug("Repayment was completed successfully");

    }

    private void decreaseDebt(Debt debt, double amount) {
        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

        Debt changedDebt = creditService.makeDebtDecrease(debt, amount);
        changeDebt(changedDebt);
    }

    @Override
    public Collection<FamilyCreditAccount> getFamilyCredits(BigInteger id) {
        Collection<FamilyCreditAccount> familyCreditAccounts = creditAccountDao.getAllFamilyCreditsByAccountId(id);
        for (FamilyCreditAccount creditAccount : familyCreditAccounts) {
            if (creditAccount.getRemainsToPay() <= 0) {
                creditAccountDao.updateIsPaidStatusPersonalCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
            }
        }
        return creditAccountDao.getAllFamilyCreditsByAccountId(id);
    }

    @Override
    public Collection<CreditOperation> getAllFamilyCreditOperations(BigInteger id) {
        return creditOperationDao.getAllCreditOperationsByCreditFamilyId(id);
    }

    @Override
    public FamilyCreditAccount getFamilyCreditAccount(BigInteger id) {
        return creditAccountDao.getFamilyCreditById(id);
    }

    @Override
    public boolean doesCreditWithNameNotExist(BigInteger debitId, String name) {
        return creditAccountDao.getCreditsIdByNameFam(debitId, name).isEmpty();
    }

    private void changeDebt(Debt debt) {
        changeDebtDateTo(debt.getDebtId(), debt.getDateTo());
        changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
        changeDebtAmount(debt.getDebtId(), debt.getAmountDebt());
    }

    private void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updateFamilyDebtDateTo(id, DateUtils.localDateToDateCredit(date));
    }

    private void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDateCredit(date));
    }

    private void changeDebtAmount(BigInteger id, double amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

    private void addPayment(FamilyCreditAccount creditAccount, FamilyDebitAccount debitAccount, double amount, BigInteger idUser) {
        double actualDebitAmount = debitAccount.getAmount();
        double newDebitAmount = new BigDecimal(actualDebitAmount - amount).setScale(2, RoundingMode.UP).doubleValue();
        debitAccountDao.updateAmountOfFamilyAccount(debitAccount.getId(), newDebitAmount);
        operationService.createFamilyOperationExpense(idUser,debitAccount.getId(), amount, LocalDateTime.now(), CategoryExpense.CREDIT);
        creditOperationDao.createFamilyCreditOperation(amount, LocalDate.now(), creditAccount.getCreditId(), idUser);
        double updatedAmount = new BigDecimal(creditAccount.getPaidAmount() + amount).setScale(2, RoundingMode.UP).doubleValue();
        creditAccountDao.updateFamilyCreditPayment(creditAccount.getCreditId(), updatedAmount);
        double totalPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate());
        if (totalPay == updatedAmount) {
            creditAccountDao.updateIsPaidStatusFamilyCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
        }
    }

}
