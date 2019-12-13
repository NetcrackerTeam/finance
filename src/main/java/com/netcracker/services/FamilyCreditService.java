package com.netcracker.services;

import com.netcracker.models.CreditOperation;
import com.netcracker.models.FamilyCreditAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface FamilyCreditService {

    void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount);

    void deleteFamilyCredit(BigInteger id);

    void addFamilyCreditPayment(BigInteger idAccount, BigInteger idCredit, long amount, Date date);

    boolean addFamilyCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount);

    void increaseDebt(BigInteger idCredit, long amount);

    void addAutoDebtRepayment(BigInteger idAccount, BigInteger idCredit, long amount);

    Collection<FamilyCreditAccount> getFamilyCredits(BigInteger id);

    Collection<CreditOperation> getAllFamilyCreditOperations(BigInteger id);

    FamilyCreditAccount getFamilyCreditAccount(BigInteger id);
}
