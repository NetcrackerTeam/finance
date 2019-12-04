package com.netcracker.services;

import com.netcracker.models.CreditOperation;
import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface PersonalCreditService {

    void createPersonalCredit(BigInteger id, PersonalCreditAccount creditAccount);

    void deletePersonalCredit(BigInteger id);

    void addPersonalCreditPayment(BigInteger id, long amount, Date date);

    void increaseDebt(BigInteger id, long amount);

    void decreaseDebt(BigInteger id, long amount);

    Collection<PersonalCreditAccount> getPersonalCredits(BigInteger id);

    Collection<CreditOperation> getAllPersonalCreditOperations(BigInteger id);

    PersonalCreditAccount getPersonalCreditAcount(BigInteger id);
}
