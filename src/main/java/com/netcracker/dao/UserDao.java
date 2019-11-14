package com.netcracker.dao;

import com.netcracker.models.User;
import java.math.BigInteger;
import java.util.List;

public interface UserDao {
    public void createUser();

    public User getUserById(BigInteger id);

    public User getUserByLogin(String login);

    public void updateUserPasswordById(BigInteger id, BigInteger newPassword);

    public User getAllUsersByFamilyAccountId(List<BigInteger> id);

    public User getUserByFamilyAccountId(BigInteger id);
}
