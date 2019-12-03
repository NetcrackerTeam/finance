package com.netcracker.services.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;

    @Override
    public void changePassword(BigInteger id, String newPassword) {
        userDao.updateUserPasswordById(id , newPassword);
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public PersonalDebitAccount createPersonalDebitAcc(PersonalDebitAccount personalDebitAccount) {
        return personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }

    @Override
    public boolean isUserActive(User user) {
        return false;
    }

    @Override
    public boolean isUserHasFamilyAccount(User user) {
        return false;
    }
}
