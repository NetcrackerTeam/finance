package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface FamilyDebitService {
    FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount);

    FamilyDebitAccount getFamilyDebitAccount(BigInteger debitId);

    void deleteFamilyDebitAccount(BigInteger debitId);

    boolean addUserToAccount(BigInteger accountId, BigInteger userId);

    void deleteUserFromAccount(BigInteger accountId, BigInteger userId);


    Collection<AbstractAccountOperation> getHistory(BigInteger family_accountId, Date date);

    Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId);
}