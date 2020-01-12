package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private
    PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private CreditAccountDao creditAccountDao;

    private final double ZERO = 0;

    private static final org.apache.log4j.Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public boolean isUserActive(BigInteger userId) {
        User userTemp = getUserById(userId);
        boolean isActiveUser = UserStatusActive.YES.equals(userTemp.getUserStatusActive());
        if (isActiveUser) {
            logger.debug("User  active by user id " + userId);
            return true;
        } else
            logger.debug("User not active by user id " + userId);
        return false;
    }

    @Override
    public boolean isUserHaveFamilyAccount(BigInteger userId) {
        User userTemp = getUserById(userId);
        boolean isUserHasFamilyAccount = BigInteger.ZERO.equals(userTemp.getFamilyDebitAccount());
        if (isUserHasFamilyAccount) {
            logger.debug("User  have family Account with id " + userId);
            return true;
        }
        logger.debug("User didnt have family Account with id " + userId);
        return false;
    }

    @Override
    public User getUserById(BigInteger userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public boolean confirmUserPassword(String password, String confirmPassword) {
        boolean notNull = (password != null && confirmPassword != null);
        boolean equalPassword = (password.equals(confirmPassword));
        if (!notNull) {
            logger.debug("password null" + password + " " + " " + confirmPassword);
        } else if (equalPassword) {
            return true;
        }
        return false;
    }

    @Override
    public String encodePassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (password == null) {
            logger.debug("password null" + password);
        } else {
            return bCryptPasswordEncoder.encode(password);
        }
        return null;
    }

    @Override
    public boolean deactivateUser(User user) {
        PersonalDebitAccount personalDebitAccount = personalDebitAccountDao.
                getPersonalAccountById(user.getPersonalDebitAccount());

        if (isUserHaveFamilyAccount(user.getId())) {
            FamilyDebitAccount familyDebitAccount = familyAccountDebitDao.getFamilyAccountById(user.getFamilyDebitAccount());
            boolean checkDebitPersonalAccount = (ZERO == personalDebitAccount.getAmount());
            if (familyDebitAccount != null) {
                boolean checkDebitFamilyAndPersonalAccount = (ZERO == personalDebitAccount.getAmount()) && (ZERO == familyDebitAccount.getAmount());
                if (checkDebitFamilyAndPersonalAccount && allFamilyCreditNull(user) && allPersonalCreditNull(user)) {
                    return true;
                }
            } else if (checkDebitPersonalAccount && allFamilyCreditNull(user) && allPersonalCreditNull(user)) {
                return true;
            }
        }

        return true;
    }


    public boolean allPersonalCreditNull(User user) {
        List<PersonalCreditAccount> allPersonalCredit = creditAccountDao.getAllPersonalCreditsByAccountId(user.getId());
        for (PersonalCreditAccount personalCredit : allPersonalCredit) {
            if (personalCredit == null)
                logger.debug("personalCredit null" + personalCredit.getCreditId());
            boolean checkCredit = (ZERO == personalCredit.getAmount());
            if (!checkCredit) {
                return false;
            }
        }
        return true;
    }

    public boolean allFamilyCreditNull(User user) {
        List<FamilyCreditAccount> allFamilyCredit = creditAccountDao.getAllFamilyCreditsByAccountId(user.getId());
        for (FamilyCreditAccount familyCredit : allFamilyCredit) {
            if (familyCredit == null)
                logger.debug("familyCredit null" + familyCredit.getCreditId());
            boolean checkCredit = (ZERO == familyCredit.getAmount());
            if (!checkCredit) {
                return false;
            }
        }
        return true;
    }

}
