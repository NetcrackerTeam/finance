package com.netcracker.services;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface FamilyDebitService {
    FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount);

    FamilyDebitAccount getFamilyDebitAccount(BigInteger debitId);

    void deleteFamilyDebitAccount(BigInteger debitId, BigInteger userId);

    boolean addUserToAccount(BigInteger accountId, BigInteger userId);

    void deleteUserFromAccount(BigInteger accountId, BigInteger userId);

    List<AbstractAccountOperation> getHistory(BigInteger familyAccountId, LocalDate date);

    Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId);

    boolean isUserParticipant(BigInteger userId);
}