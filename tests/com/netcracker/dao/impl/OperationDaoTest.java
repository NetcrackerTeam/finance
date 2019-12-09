package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.OperationDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.CreditStatusPaid;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.*;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OperationDaoTest {

    private AccountIncome testIncome;
    private AccountExpense testExpense;
    @Autowired
    private OperationDao operationDao;

    @Before
    public void initializeObjects() {
        testIncome = new AccountIncome.Builder()
            .accountDate(Date.valueOf(LocalDate.of(1995, 10, 20)))
            .categoryIncome(CategoryIncome.getNameByKey(BigInteger.valueOf(14)))
            .accountAmount(1357L)
            .build();

        testExpense = new AccountExpense.Builder()
                .accountDate(Date.valueOf(LocalDate.of(2001, 12, 9)))
                .categoryExpense(CategoryExpense.getNameByKey(BigInteger.valueOf(5)))
                .accountAmount(14685L)
                .build();
    }


    @Rollback
    @Test
    public void addIncomePersonalByAccId() {
        operationDao.createIncomePersonalByAccId(BigInteger.valueOf(2), BigDecimal.valueOf(testIncome.getAmount()), testIncome.getDate(), testIncome.getCategoryIncome());
        int expected = 2;
        int actual = operationDao.getIncomesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                Date.valueOf(LocalDate.of(1990,10,10))).size();
        assertEquals(expected, actual);
    }
    @Rollback
    @Test
    public void addExpensePersonaByAccId() {
        operationDao.createExpensePersonaByAccId(BigInteger.valueOf(2), BigDecimal.valueOf(testExpense.getAmount()),
                testExpense.getDate(), testExpense.getCategoryExpense());
        int expected = 2;
        int actual = operationDao.getExpensesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                Date.valueOf(LocalDate.of(1990,10,10))).size();
        assertEquals(expected, actual);
    }
    @Rollback
    @Test
    public void addIncomeFamilyByAccId() {
        operationDao.createIncomeFamilyByAccId(BigInteger.valueOf(3),BigInteger.valueOf(2), BigDecimal.valueOf(testIncome.getAmount()),
                testIncome.getDate(), testIncome.getCategoryIncome());
        int expected = 2;
        int actual = operationDao.getIncomesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                Date.valueOf(LocalDate.of(1990,10,10))).size();
        assertEquals(expected, actual);
    }
    @Rollback
    @Test
    public void addExpenseFamilyByAccId() {
        operationDao.createExpenseFamilyByAccId(BigInteger.valueOf(2),BigInteger.valueOf(3), BigDecimal.valueOf(testExpense.getAmount()),
                testExpense.getDate(), testExpense.getCategoryExpense());
        int expected = 2;
        int actual = operationDao.getExpensesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                Date.valueOf(LocalDate.of(1990,10,10))).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getIncomesPersonalAfterDateByAccountId() {
        List<AccountIncome> list = (List<AccountIncome>) operationDao.getIncomesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getExpensesPersonalAfterDateByAccountId() {
        List<AccountExpense> list = (List<AccountExpense>) operationDao.getExpensesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getIncomesFamilyAfterDateByAccountId() {
        List<AccountIncome> list = (List<AccountIncome>) operationDao.getIncomesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getExpensesFamilyAfterDateByAccountId() {
        List<AccountExpense> list = (List<AccountExpense>) operationDao.getExpensesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                Date.valueOf(LocalDate.of(1990, 11, 30)));
        Assert.assertEquals(1, list.size());
    }
}
