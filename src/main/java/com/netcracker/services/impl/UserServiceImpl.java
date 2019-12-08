package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class UserServiceImpl implements UserService {
    private final BigInteger CONSTANTZERO = BigInteger.valueOf(0);
    @Autowired
    private UserDao userDao;
    private static final org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public boolean isUserActive(BigInteger userId) {
        if (userDao.getUserById(userId).equals(UserStatusActive.NO)) {
            logger.debug("User not active by user id " + userId);
            return false;
        } else
            return true;
    }

    @Override
    public boolean isUserHasFamilyAccount(BigInteger userId) {
        if (userDao.getUserById(userId).getFamilyDebitAccount().equals(CONSTANTZERO)) {
            logger.debug("User didnt have family Account " + userId);
            return false;
        }
        return true;
    }
}
