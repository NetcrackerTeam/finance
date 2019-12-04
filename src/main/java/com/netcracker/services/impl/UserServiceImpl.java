package com.netcracker.services.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    private static final org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public void changePassword(BigInteger id, String newPassword) {
        logger.info("change password, user id" + id + " new password " + newPassword);
        userDao.updateUserPasswordById(id, newPassword);
    }

    @Override
    public User createUser(User user) {
        logger.info(
                "create user in UserServiceImpl  " + user);
        return userDao.createUser(user);
    }

    @Override
    public PersonalDebitAccount createPersonalDebitAcc(PersonalDebitAccount personalDebitAccount) {
        logger.info("Create user personal  debit account " + personalDebitAccount);
        return personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }

    @Override
    public boolean isUserActive(BigInteger user_id) {
        if (userDao.getUserById(user_id).getUserStatusActive().toString().equals("NO")) {
            logger.error("User not active by user id "+ user_id);
            return false;
        } else
            return true;
    }

    @Override
    public boolean isUserHasFamilyAccount(BigInteger user_id) {
        if (userDao.getUserById(user_id).getFamilyDebitAccount() == BigInteger.valueOf(0)){
            logger.error("User didnt have family Account "+ user_id);
            return false;
        }
        return true;
    }
}
