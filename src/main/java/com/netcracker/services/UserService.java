package com.netcracker.services;

import java.math.BigInteger;

public interface UserService {
    public boolean isUserActive(BigInteger userId);
    public boolean isUserHasFamilyAccount(BigInteger userId);

}
