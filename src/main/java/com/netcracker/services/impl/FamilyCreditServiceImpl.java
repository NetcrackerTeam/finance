package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.FamilyCreditDebtService;
import com.netcracker.services.FamilyCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

@Service
public class FamilyCreditServiceImpl implements FamilyCreditService {

    @Autowired
    CreditAccountDao creditAccountDao;

    @Autowired
    CreditOperationDao creditOperationDao;

    @Autowired
    @Qualifier("familyCreditDebt")
    FamilyCreditDebtService familyCreditDebtService;

    @Override
    public void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount) {
        creditAccountDao.createFamilyCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deleteFamilyCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusFamilyCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addFamilyPayment(BigInteger id, long amount, Date date) {
        // ToDo
    }

    @Override
    public void increaseDebt(BigInteger id, long amount) {


    }

    @Override
    public void decreaseDebt(BigInteger id, long amount) {

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
    public FamilyCreditAccount getFamilyCreditAcount(BigInteger id) {
        return creditAccountDao.getFamilyCreditById(id);
    }

    @Override
    public long getMonthPaymentAmount(FamilyCreditAccount creditAccount) {
        return 0;
    }

    private void createCreditOperation(BigInteger id, long amount, Date date) {
        CreditOperation creditOperation = new CreditOperation(amount, date);
        creditOperationDao.createPersonalCreditOperation(creditOperation, id);
    }
}
