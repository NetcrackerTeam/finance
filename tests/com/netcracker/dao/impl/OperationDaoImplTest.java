package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.OperationDao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OperationDaoImplTest {

    @Autowired
    private OperationDao operationDao;

    @Rollback
    @Test
    public void addIncomePersonalByAccId() {
    }
    @Rollback
    @Test
    public void addExpensePersonaByAccId() {
    }
    @Rollback
    @Test
    public void addIncomeFamilyByAccId() {
    }
    @Rollback
    @Test
    public void addExpenseFamilyByAccId() {
    }

    @Test
    public void getIncomesPersonalAfterDateByAccountId() {
        List<AccountIncome> list = (List<AccountIncome>) operationDao.getIncomesPersonalAfterDateByAccountId(BigInteger.valueOf(2),  Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getExpensesPersonalAfterDateByAccountId() {
    }

    @Test
    public void getIncomesFamilyAfterDateByAccountId() throws ParseException {
        List<AccountIncome> list = (List<AccountIncome>) operationDao.getIncomesFamilyAfterDateByAccountId(BigInteger.valueOf(3),  Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getExpensesFamilyAfterDateByAccountId() {
        List<AccountExpense> list = (List<AccountExpense>) operationDao.getExpensesFamilyAfterDateByAccountId(BigInteger.valueOf(3),  Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }
}
