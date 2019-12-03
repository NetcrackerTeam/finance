package com.netcracker.services;

import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;

public interface PersonalCreditService {

    void createPersonalCredit(BigInteger id);

    void deletePersonalCredit(BigInteger id);

    void addPersonalCreditPayment(BigInteger id, long amount);

    void getPersonalCredits(BigInteger id);

    boolean isCommodityCredit();
}
