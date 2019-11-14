package com.netcracker.dao;

import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;

public interface PersonalDebitAccountDao {
    public PersonalDebitAccount getPersonalAccountById(BigInteger id);

    public void createPersonalAccount();

    public void deletePersonalAccountById(BigInteger id);

    public void deletePersonalAccountByUserId(BigInteger id);
}
