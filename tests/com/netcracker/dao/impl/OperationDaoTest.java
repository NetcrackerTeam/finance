package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.OperationDao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
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
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
            .accountDate(LocalDate.of(1995, 10, 20))
            .categoryIncome(CategoryIncome.getNameByKey(BigInteger.valueOf(14)))
            .accountAmount(1357L)
            .build();

        testExpense = new AccountExpense.Builder()
                .accountDate(LocalDate.of(2001, 12, 9))
                .categoryExpense(CategoryExpense.getNameByKey(BigInteger.valueOf(5)))
                .accountAmount(14685L)
                .build();
    }


    @Rollback
    @Test
    public void addIncomePersonalByAccId() {
        operationDao.createIncomePersonalByAccId(BigInteger.valueOf(2), testIncome.getAmount(), testIncome.getDate(), testIncome.getCategoryIncome());
        int expected = 2;
        int actual = operationDao.getIncomesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                LocalDate.of(1990,10,10)).size();
        assertEquals(expected, actual);
    }
    @Rollback
    @Test
    public void addExpensePersonaByAccId() {
        operationDao.createExpensePersonaByAccId(BigInteger.valueOf(2), testExpense.getAmount(),
                testExpense.getDate(), testExpense.getCategoryExpense());
        int expected = 2;
        int actual = operationDao.getExpensesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                LocalDate.of(1990,10,10)).size();
        assertEquals(expected, actual);
    }
    @Rollback
    @Test
    public void addIncomeFamilyByAccId() {
        operationDao.createIncomeFamilyByAccId(BigInteger.valueOf(3),BigInteger.valueOf(2), testIncome.getAmount(),
                testIncome.getDate(), testIncome.getCategoryIncome());
        int expected = 2;
        int actual = operationDao.getIncomesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                LocalDate.of(1990,10,10)).size();
        assertEquals(expected, actual);
    }
    @Rollback
    @Test
    public void addExpenseFamilyByAccId() {
        operationDao.createExpenseFamilyByAccId(BigInteger.valueOf(2),BigInteger.valueOf(3), testExpense.getAmount(),
                testExpense.getDate(), testExpense.getCategoryExpense());
        int expected = 2;
        int actual = operationDao.getExpensesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                LocalDate.of(1990,10,10)).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getIncomesPersonalAfterDateByAccountId() {
        List<AccountIncome> list = operationDao.getIncomesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                LocalDate.of(1990, 11, 30));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getExpensesPersonalAfterDateByAccountId() {
        List<AccountExpense> list = operationDao.getExpensesPersonalAfterDateByAccountId(BigInteger.valueOf(2),
                LocalDate.of(1990, 11, 30));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getIncomesFamilyAfterDateByAccountId() {
        List<AccountIncome> list = operationDao.getIncomesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                LocalDate.of(1990, 11, 30));
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getExpensesFamilyAfterDateByAccountId() {
        List<AccountExpense> list = operationDao.getExpensesFamilyAfterDateByAccountId(BigInteger.valueOf(3),
                LocalDate.of(1990, 11, 30));
        Assert.assertEquals(1, list.size());
    }
}
