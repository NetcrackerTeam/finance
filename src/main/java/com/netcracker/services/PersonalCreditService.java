package com.netcracker.services;

import com.netcracker.models.CreditOperation;
import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;
import java.util.Collection;

public interface PersonalCreditService {

    void createPersonalCredit(BigInteger id, PersonalCreditAccount creditAccount);

    void deletePersonalCredit(BigInteger id);

    void addPersonalCreditPayment(BigInteger idAccount, BigInteger idCredit, double amount);

    boolean addPersonalCreditPaymentAuto(BigInteger idAccount, BigInteger idCredit, double amount);

    void increaseDebt(BigInteger idCredit, double amount);

    void addAutoDebtRepayment(BigInteger idAccount, BigInteger idCredit, double amount);

    Collection<PersonalCreditAccount> getPersonalCredits(BigInteger id);

    Collection<CreditOperation> getAllPersonalCreditOperations(BigInteger id);

    PersonalCreditAccount getPersonalCreditAccount(BigInteger id);

    boolean doesCreditWithNameNotExist(BigInteger debitId, String name);
}
