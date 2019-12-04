package com.netcracker.services;

import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;

import java.math.BigInteger;

public interface UserService {
    public void changePassword(BigInteger id,  String newPassword);
    public User createUser(User user);
    public PersonalDebitAccount createPersonalDebitAcc(PersonalDebitAccount personalDebitAccount);
    public boolean isUserActive(BigInteger user_id);
    public boolean isUserHasFamilyAccount(BigInteger user_id);

}
