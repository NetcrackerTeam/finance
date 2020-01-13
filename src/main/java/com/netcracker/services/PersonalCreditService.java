package com.netcracker.services;

import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.Status;

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

    Status checkCreditData(AbstractCreditAccount creditAccount);

    String NONE_NAME = "none";

    String REGULAR_FOR_NAME = "^[A-Za-z0-9 _]*$";
}
