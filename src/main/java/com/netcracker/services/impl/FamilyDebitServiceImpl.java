package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.dao.impl.FamilyAccountDebitDaoImpl;
import com.netcracker.models.*;
import com.netcracker.services.FamilyDebitService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.*;

public class FamilyDebitServiceImpl implements FamilyDebitService {

    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OperationDao operationDao;

    private static final Logger logger = Logger.getLogger(FamilyAccountDebitDaoImpl.class);

    @Override
    public FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount) {
        return familyAccountDebitDao.createFamilyAccount(familyDebitAccount);
    }

    @Override
    public FamilyDebitAccount getFamilyDebitAccount(BigInteger id) {
        return familyAccountDebitDao.getFamilyAccountById(id);
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger id) {
        familyAccountDebitDao.deleteFamilyAccount(id);
    }

    @Override
    public void addUserToAccount(BigInteger account_id, BigInteger user_id) {
        if(userDao.getUserById(user_id).getUserStatusActive().toString().equals("NO")){
            logger.error("The user is unActive");
        } else {
            for(User participants: familyAccountDebitDao.getParticipantsOfFamilyAccount(account_id)){
                if(participants.getId() == user_id){
                    logger.error("The user is has family account");
                }
            }
            familyAccountDebitDao.addUserToAccountById(account_id, user_id);
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger account_id, BigInteger user_id) {
        familyAccountDebitDao.deleteUserFromAccountById(account_id, user_id);
    }

    @Override
    public Collection<Object> getHistory(BigInteger family_account_id, Date date) {
        Collection<AccountIncome> incomes;
        incomes = operationDao.getIncomesFamilyAfterDateByAccountId(family_account_id, date);
        Collection<AccountExpense> expenses;
        expenses = operationDao.getExpensesFamilyAfterDateByAccountId(family_account_id, date);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(incomes);
        objects.addAll(expenses);
        return objects;
    }
}