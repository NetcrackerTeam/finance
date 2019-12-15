package com.netcracker.services.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.OperationDao;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class OperationServiceTest {
    @Autowired
    private OperationServiceImpl operationService;
    @Autowired
    private OperationDao operationDao;

    private String dateString = "1990-10-10";
    private LocalDate date = LocalDate.parse(dateString);

    private String dateIncomeString = "2019-12-10";
    private LocalDate dateIncome = LocalDate.parse(dateIncomeString);

    private String dateExpenseString = "2019-11-10";
    private LocalDate dateExpense = LocalDate.parse(dateExpenseString);

    private BigInteger debitIdFamily = BigInteger.valueOf(3);
    private BigInteger debitIdPersonal = BigInteger.valueOf(2);
    private BigInteger currentId = BigInteger.valueOf(97);
    private BigInteger nonexistentId = BigInteger.valueOf(1001);

    private AccountIncome personalIncomeExpected;
    private AccountExpense personalExpenseExpected;
    private AccountIncome familyIncomeExpected;
    private AccountExpense familyExpenseExpected;

    private List<AbstractAccountOperation> expectedList = new ArrayList<>();

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Before
    public void initializeObjects() {
        familyIncomeExpected = new AccountIncome.Builder().accountId(BigInteger.valueOf(19)).accountUserId(debitIdFamily)
                .accountAmount(7000L).accountDate(dateIncome).categoryIncome(CategoryIncome.SALARY).build();

        familyExpenseExpected = new AccountExpense.Builder().accountId(BigInteger.valueOf(17)).accountUserId(debitIdFamily)
                .accountAmount(3000L).accountDate(dateExpense).categoryExpense(CategoryExpense.FOOD).build();

        personalIncomeExpected = new AccountIncome.Builder().accountId(BigInteger.valueOf(18)).accountUserId(debitIdPersonal)
                .accountAmount(5000L).accountDate(dateIncome).categoryIncome(CategoryIncome.SALARY).build();

        personalExpenseExpected = new AccountExpense.Builder().accountId(BigInteger.valueOf(16)).accountUserId(debitIdPersonal)
                .accountAmount(2000L).accountDate(dateExpense).categoryExpense(CategoryExpense.FOOD).build();
    }

    @Test
    public void getAllFamilyOperations() {
        expectedList.add(familyIncomeExpected);
        expectedList.add(familyExpenseExpected);

        List<AbstractAccountOperation> actualList = new ArrayList<>(operationService.getAllFamilyOperations(debitIdFamily, date));
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void getAllFamilyOperationsCheckNull() {
        operationService.getAllFamilyOperations(null, null);
    }

    @Test
    public void getAllFamilyOperationsEmptyList() {
        List<AbstractAccountOperation> actualList = new ArrayList<>(operationService.getAllFamilyOperations(nonexistentId, date));
        AssertUtils.assertOperationsCollections(Collections.emptyList(), actualList);
    }

    @Test
    public void getAllPersonalOperations() {
        expectedList.add(personalIncomeExpected);
        expectedList.add(personalExpenseExpected);

        List<AbstractAccountOperation> actualList = new ArrayList<>(operationService.getAllPersonalOperations(debitIdPersonal, date));
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void getAllPersonalOperationsCheckNull() {
        operationService.getAllPersonalOperations(null, null);
    }

    @Test
    public void getAllPersonalOperationsEmptyList() {
        List<AbstractAccountOperation> actualList = new ArrayList<>(operationService.getAllPersonalOperations(nonexistentId, date));
        AssertUtils.assertOperationsCollections(Collections.emptyList(), actualList);
    }

    @Rollback
    @Test
    public void createFamilyOperationIncome() {
        familyIncomeExpected.setId(currentId);
        operationService.createFamilyOperationIncome(debitIdPersonal, debitIdFamily, familyIncomeExpected.getAmount(),
                familyIncomeExpected.getDate(), familyIncomeExpected.getCategoryIncome());

        expectedList.add(familyIncomeExpected);
        List<AccountIncome> familyIncomeActual = operationDao.getIncomesFamilyAfterDateByAccountId(debitIdFamily, dateIncome);
        List<AbstractAccountOperation> actualList = new ArrayList<>(familyIncomeActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void createFamilyOperationIncomeCheckNull(){
        operationService.createFamilyOperationIncome(null, null, 0, null, null);
    }

    @Rollback
    @Test
    public void createFamilyOperationExpense() {
        familyExpenseExpected.setId(currentId);
        operationService.createFamilyOperationExpense(debitIdPersonal, debitIdFamily, familyExpenseExpected.getAmount(),
                familyExpenseExpected.getDate(), familyExpenseExpected.getCategoryExpense());

        expectedList.add(familyExpenseExpected);
        List<AccountExpense> familyExpenseActual = operationDao.getExpensesFamilyAfterDateByAccountId(debitIdFamily, dateExpense);
        List<AbstractAccountOperation> actualList = new ArrayList<>(familyExpenseActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void createFamilyOperationExpenseCheckNull() {
        operationService.createFamilyOperationExpense(null, null, 0, null, null);
    }

    @Rollback
    @Test
    public void createPersonalOperationIncome() {
        personalIncomeExpected.setId(currentId);
        operationService.createPersonalOperationIncome(debitIdPersonal, personalIncomeExpected.getAmount(),
                personalIncomeExpected.getDate(), personalIncomeExpected.getCategoryIncome());

        expectedList.add(personalIncomeExpected);
        List<AccountIncome> personalIncomeActual = operationDao.getIncomesFamilyAfterDateByAccountId(debitIdPersonal, dateIncome);
        List<AbstractAccountOperation> actualList = new ArrayList<>(personalIncomeActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void createPersonalOperationIncomeCheckNull() {
        operationService.createPersonalOperationIncome(null, 0, null, null);
    }

    @Rollback
    @Test
    public void createPersonalOperationExpense() {
        personalExpenseExpected.setId(currentId);
        operationService.createPersonalOperationExpense(debitIdPersonal, personalExpenseExpected.getAmount(),
                personalExpenseExpected.getDate(), personalExpenseExpected.getCategoryExpense());

        expectedList.add(personalExpenseExpected);
        List<AccountExpense> personalExpenseActual = operationDao.getExpensesPersonalAfterDateByAccountId(debitIdPersonal, dateExpense);
        List<AbstractAccountOperation> actualList = new ArrayList<>(personalExpenseActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void createPersonalOperationExpenseCheckNull() {
        operationService.createPersonalOperationExpense(null, 0, null, null);
    }

}
