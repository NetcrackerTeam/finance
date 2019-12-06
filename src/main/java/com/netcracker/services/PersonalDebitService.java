package com.netcracker.services;

import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface PersonalDebitService {

    PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount);

    public void deletePersonalDebitAccount(BigInteger account_id, BigInteger user_id);

    PersonalDebitAccount getPersonalDebitAccount(BigInteger id);

    public Collection<Object> getHistory(BigInteger personal_account_id, Date date);
}
