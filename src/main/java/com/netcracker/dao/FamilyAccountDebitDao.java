package com.netcracker.dao;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;

import java.math.BigInteger;

public interface FamilyAccountDebitDao {
    public FamilyDebitAccount getFamilyAccountById(BigInteger id);

    public void createFamilyAccount();

    public void deleteFamilyAccount(BigInteger id);

    public void addUserToAccountById(User id);

    public void deleteUserFromAccountById(User id);

}
