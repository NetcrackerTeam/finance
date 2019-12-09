package com.netcracker.services.impl;

import com.netcracker.dao.OperationDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationDao operationDao;

    @Override
    public List<AbstractAccountOperation> getAllFamilyOperations(List<FamilyDebitAccount> accounts) {
        return null;
    }

    @Override
    public List<AbstractAccountOperation> getAllPersonalOperations(List<PersonalDebitAccount> accounts) {
        return null;
    }

    @Override
    public AccountIncome getFamilyOperationIncome(BigInteger operationId) {
        return null;
    }

    @Override
    public AccountExpense getFamilyOperationExpense(BigInteger operationId) {
        return null;
    }

    @Override
    public AccountIncome getPersonalOperationIncome(BigInteger operationId) {
        return null;
    }

    @Override
    public AccountExpense getPersonalOperationExpense(BigInteger operationId) {
        return null;
    }

    @Override
    public void createFamilyOperationIncome(BigInteger idUser, BigInteger idFamily, BigDecimal income, Date date, CategoryIncome categoryIncome) {

    }

    @Override
    public void createFamilyOperationExpense(BigInteger idUser, BigInteger idFamily, BigDecimal expense, Date date, CategoryExpense categoryExpense) {

    }

    @Override
    public void createPersonalOperationIncome(BigInteger id, BigDecimal income, Date date, CategoryIncome categoryIncome) {

    }

    @Override
    public void createPersonalOperationExpense(BigInteger id, BigDecimal expense, Date date, CategoryExpense categoryExpense) {

    }

}
