package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.FamilyDebitService;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    FamilyDebitService familyDebitService;

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
        if (userTemp.getFamilyDebitAccount() != null || familyDebitService.isUserParticipant(userId)) {
            logger.debug("User have family Account with id " + userId);
            return true;
        }
        logger.debug("User don't have family Account with id " + userId);
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
        List<? extends AbstractCreditAccount> allPersonalCredits = creditAccountDao.getAllPersonalCreditsByAccountId(user.getPersonalDebitAccount());

        if (isUserHaveFamilyAccount(user.getId())) {
            List<? extends AbstractCreditAccount> allFamilyCredits = creditAccountDao.getAllFamilyCreditsByAccountId(user.getFamilyDebitAccount());
            if (isNotCreditDebts(allPersonalCredits) && isNotCreditDebts(allFamilyCredits)) return true;
        }

        return isNotCreditDebts(allPersonalCredits);
    }

    private boolean isNotCreditDebts(List<? extends AbstractCreditAccount> allCredits) {
        if (allCredits.isEmpty()) return true;
        for (AbstractCreditAccount creditAccount : allCredits) {
            if (creditAccount.getDebt().getAmountDebt() > 0) return false;
            if (CreditStatusPaid.NO.equals(creditAccount.isPaid())) return false;
        }
        return true;
    }

    @Override
    public boolean validatePasswordResetToken(BigInteger id, String token) {
        User user =
                userDao.findUserByToken(token);
        if (user == null) {
            return false;
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return true;
    }
}
