package com.netcracker.services;

import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.FamilyDebitAccount;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface FamilyDebitService {
    public FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount);

    FamilyDebitAccount getFamilyDebitAccount(BigInteger debit_id);

    public void deleteFamilyDebitAccount(BigInteger debit_id);

    public void addUserToAccount(BigInteger account_id, BigInteger user_id);

    public void deleteUserFromAccount(BigInteger account_id, BigInteger user_id);

    // не знаю что вернуть
    public Collection<Object> getHistory(BigInteger family_account_id, Date date);
}