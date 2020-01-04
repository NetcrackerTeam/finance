package com.netcracker.services;

import com.netcracker.models.User;

import java.math.BigInteger;

public interface UserService {
    boolean isUserActive(BigInteger userId);

    boolean isUserHaveFamilyAccount(BigInteger userId);

    User getUserById(BigInteger userId);

    boolean confirmUserPassword(String password, String confirmPassword);

    String encodePassword(String password);
}
