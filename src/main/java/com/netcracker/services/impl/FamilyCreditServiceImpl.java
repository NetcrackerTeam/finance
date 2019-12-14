package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.Debt;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.FamilyCreditService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
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

    @Override
    public void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount) {
        creditAccountDao.createFamilyCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deleteFamilyCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusFamilyCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addFamilyCreditPayment(BigInteger idDebitAccount, BigInteger idCredit, long amount, Date date) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
        FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);

        creditService.makeUserPayment(debitAccount, creditAccount, amount);
        addPayment(creditAccount, debitAccount, amount, null);

    }

    @Override
    public boolean addFamilyCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
        FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDate(), creditAccount.getDateTo());

        boolean isSuccess = creditService.makeAutoPayment(debitAccount, creditAccount, amount);
        if (!isSuccess)
            return false;
        addPayment(creditAccount, debitAccount, amount, null);
        logger.debug("Payment was completed successfully");
        return true;
    }

    @Override
    public void increaseDebt(BigInteger idCredit, long amount) {
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
    public void addAutoDebtRepayment(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        ObjectsCheckUtils.isNotNull(idDebitAccount, idCredit);

        FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
        FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);

        ObjectsCheckUtils.isNotNull(creditAccount, debitAccount);
        ObjectsCheckUtils.isNotNull(creditAccount.getDebt());
        decreaseDebt(creditAccount.getDebt(), amount);
        addPayment(creditAccount, debitAccount, amount, null);
        logger.debug("Repayment was completed successfully");

    }

    private void decreaseDebt(Debt debt, long amount) {
        ObjectsCheckUtils.isNotNull(debt);
        ObjectsCheckUtils.isNotNull(debt.getDebtId());

        Debt changedDebt = creditService.makeDebtDecrease(debt, amount);
        changeDebt(changedDebt);
    }

    @Override
    public Collection<FamilyCreditAccount> getFamilyCredits(BigInteger id) {
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

    private void changeDebt(Debt debt) {
        changeDebtDateTo(debt.getDebtId(), debt.getDateTo());
        changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
        changeDebtAmount(debt.getDebtId(), debt.getAmountDebt());
    }

    private void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updateFamilyDebtDateTo(id, DateUtils.localDateToDate(date));
    }

    private void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDate(date));
    }

    private void changeDebtAmount(BigInteger id, long amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

    private void addPayment(FamilyCreditAccount creditAccount, FamilyDebitAccount debitAccount, long amount, BigInteger idUser) {
        long actualDebitAmount = debitAccount.getAmount();
        debitAccountDao.updateAmountOfFamilyAccount(debitAccount.getId(), actualDebitAmount - amount);
        creditOperationDao.createFamilyCreditOperation(amount, LocalDate.now(), creditAccount.getCreditId(), idUser);
        long updatedAmount = creditAccount.getPaidAmount() + amount;
        creditAccountDao.updateFamilyCreditPayment(creditAccount.getCreditId(), updatedAmount);
        long monthPayment = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate());
        if (monthPayment == updatedAmount) {
            creditAccountDao.updateIsPaidStatusFamilyCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
        }
    }

}
