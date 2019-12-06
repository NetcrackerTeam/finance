package com.netcracker.services;

import com.netcracker.models.CreditOperation;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface FamilyCreditService {

    void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount);

    void deleteFamilyCredit(BigInteger id);

    void addFamilyCreditPayment(BigInteger id, long amount, Date date);

    void addPersonalCreditPaymentAuto(BigInteger idAccount, BigInteger idCredit, long amount);

    Collection<FamilyCreditAccount> getFamilyCredits(BigInteger id);

    Collection<CreditOperation> getAllFamilyCreditOperations(BigInteger id);

    FamilyCreditAccount getFamilyCreditAcount(BigInteger id);

    long getMonthPaymentAmount(FamilyCreditAccount creditAccount);
}
