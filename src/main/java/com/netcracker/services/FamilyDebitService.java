package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.FamilyDebitAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface FamilyDebitService {
    public FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount);

    FamilyDebitAccount getFamilyDebitAccount(BigInteger debitId);

    public void deleteFamilyDebitAccount(BigInteger debitId);

    public boolean addUserToAccount(BigInteger accountId, BigInteger userId);

    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId);

    // не знаю что вернуть
    public Collection<AbstractAccountOperation> getHistory(BigInteger family_accountId, Date date);
}