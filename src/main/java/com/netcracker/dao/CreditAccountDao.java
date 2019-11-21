package com.netcracker.dao;

import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;
import java.util.List;

public interface CreditAccountDao {
    String SELECT_PERSONAL_CREDIT_QUERY = "";
    String SELECT_FAMILY_CREDIT_QUERY = "";
    String SELECT_PERSONAL_CREDITS_BY_ACCOUNT_QUERY = "";
    String SELECT_FAMILY_CREDITS_BY_ACCOUNT_QUERY = "";

    PersonalCreditAccount getPersonalCreditById(BigInteger id);

    FamilyCreditAccount getFamilyCreditById(BigInteger id);

    List<PersonalCreditAccount> getAllPersonalCreditsByAccountId(BigInteger id);

    List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(BigInteger id);

    boolean addPersonalCreditPayment(BigInteger id, long amount);

    boolean addFamilyCreditPayment(BigInteger id, long amount);
}
