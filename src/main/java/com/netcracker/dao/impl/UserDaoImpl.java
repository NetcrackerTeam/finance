package com.netcracker.dao.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.models.User;

import java.math.BigInteger;
import java.util.List;

public class UserDaoImpl implements UserDao {

    public void createUser() {

    }

    public User getUserById(BigInteger id) {
        return null;
    }

    public User getUserByLogin(String login) {
        return null;
    }

    public void updateUserPasswordById(BigInteger id, BigInteger newPassword) {

    }

    public User getAllUsersByFamilyAccountId(List<BigInteger> id) {
        return null;
    }

    public User getUserByFamilyAccountId(BigInteger id) {
        return null;
    }
}
