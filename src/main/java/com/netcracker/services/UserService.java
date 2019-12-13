package com.netcracker.services;

import com.netcracker.models.User;

import java.math.BigInteger;

public interface UserService {
    public boolean isUserActive(BigInteger userId) ;
    public boolean isUserHaveFamilyAccount(BigInteger userId);
    public User getUserById(BigInteger userId);


}
