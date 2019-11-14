package com.netcracker.dao;

import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;
import java.util.List;

public interface CreditAccountDao {
    public PersonalCreditAccount getPersonalCreditById(BigInteger id);

    public FamilyCreditAccount getFamilyCreditById(BigInteger id);

    public List<PersonalCreditAccount> getAllPersonalCreditsByAccountId();

    public List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(List<BigInteger> id);
}
