package com.netcracker.services;

import com.netcracker.models.CreditOperation;
import com.netcracker.models.FamilyCreditAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface FamilyCreditService {

    void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount, BigInteger userId);

    void deleteFamilyCredit(BigInteger id);

    void addFamilyCreditPayment(BigInteger idAccount, BigInteger idCredit, double amount, Date date, BigInteger userId);

    boolean addFamilyCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, double amount, BigInteger userId);

    void increaseDebt(BigInteger idCredit, double amount);

    void addAutoDebtRepayment(BigInteger idAccount, BigInteger idCredit, double amount);

    Collection<FamilyCreditAccount> getFamilyCredits(BigInteger id);

    Collection<CreditOperation> getAllFamilyCreditOperations(BigInteger id);

    FamilyCreditAccount getFamilyCreditAccount(BigInteger id);

    boolean doesCreditWithNameNotExist(BigInteger debitId, String name);
}
