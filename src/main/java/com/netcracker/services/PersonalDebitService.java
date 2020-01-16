package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PersonalDebitService {

    PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount);

    public void deletePersonalDebitAccount(BigInteger accountId, BigInteger userId);

    PersonalDebitAccount getPersonalDebitAccount(BigInteger id);

    List<AbstractAccountOperation> getHistory(BigInteger accountId, LocalDateTime date);

    Collection<PersonalDebitAccount> getAllPersonalAccounts();

    String ACCOUNT_NAME = "account";

}
