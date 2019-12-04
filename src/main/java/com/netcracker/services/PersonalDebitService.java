package com.netcracker.services;

import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;
import java.util.List;

public interface PersonalDebitService {

    PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount);

    public void deletePersonalDebitAccount(BigInteger account_id, BigInteger user_id);

    PersonalDebitAccount getPersonalDebitAccount(BigInteger id);

    public void addCreditAccount(BigInteger id, PersonalCreditAccount creditAccount);

    public List<Object> getHistory(BigInteger personal_account_id);
}
