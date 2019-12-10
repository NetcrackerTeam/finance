package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private static final org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public boolean isUserActive(BigInteger userId) {
        boolean isActiveUser = userDao.getUserById(userId).getUserStatusActive().equals(UserStatusActive.NO);
        if (!isActiveUser) {
            logger.debug("User not active by user id " + userId);
            return false;
        } else
            logger.debug("User  active by user id " + userId);
            return true;
    }

    @Override
    public boolean isUserHasFamilyAccount(BigInteger userId) {
        boolean isUserHasFamilyAccount = userDao.getUserById(userId).getFamilyDebitAccount().equals(new BigInteger(String.valueOf(0)));
        if (!isUserHasFamilyAccount) {
            logger.debug("User didnt have family Account with id " + userId);
            return false;
        }
        logger.debug("User  have family Account with id " + userId);
        return true;
    }
}
