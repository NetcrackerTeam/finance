package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
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
        User userTemp = userDao.getUserById(userId);
        boolean isActiveUser = UserStatusActive.NO.equals(userTemp.getUserStatusActive());
        if (isActiveUser) {
            logger.debug("User  active by user id " + userId);
            return true;
        } else
            logger.debug("User not active by user id " + userId);
        return false;
    }

    @Override
    public boolean isUserHasFamilyAccount(BigInteger userId) {
        User userTemp = userDao.getUserById(userId);
        boolean isUserHasFamilyAccount =(new BigInteger(String.valueOf(0)).equals(userTemp.getFamilyDebitAccount())) ;
        if (!isUserHasFamilyAccount) {
            logger.debug("User  have family Account with id " + userId);
            return true;
        }
        logger.debug("User didnt have family Account with id " + userId);
        return false;
    }
}
