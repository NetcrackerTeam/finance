package com.netcracker.services.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.OperationDao;
import com.netcracker.exception.OperationException;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private AccountIncome personalIncomeExpected;
    private AccountExpense personalExpenseExpected;
    private AccountIncome familyIncomeExpected;
    private AccountExpense familyExpenseExpected;

    private List<AbstractAccountOperation> expectedList = new ArrayList<>();

    public OperationServiceTest() throws ParseException {
    }

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

    @Test(expected = OperationException.class)
    public void getAllFamilyOperations() {
        operationService.getAllFamilyOperations(null, null);
        operationService.getAllFamilyOperations(BigInteger.valueOf(0), null);

        expectedList.add(familyIncomeExpected);
        expectedList.add(familyExpenseExpected);

        List<AbstractAccountOperation> actualList = new ArrayList<>(operationService.getAllFamilyOperations(debitIdFamily, date));
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Test(expected = OperationException.class)
    public void getAllPersonalOperations() {
        operationService.getAllPersonalOperations(null, null);
        operationService.getAllPersonalOperations(BigInteger.valueOf(0), null);

        expectedList.add(personalIncomeExpected);
        expectedList.add(personalExpenseExpected);

        List<AbstractAccountOperation> actualList = new ArrayList<>(operationService.getAllPersonalOperations(debitIdPersonal, date));
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createFamilyOperationIncome() {
        operationService.createFamilyOperationIncome(null, null, 0, null, null);
        operationService.createFamilyOperationIncome(BigInteger.valueOf(0), BigInteger.valueOf(0), 0, null,
                CategoryIncome.DEFAULT);

        familyIncomeExpected.setId(currentId);
        operationService.createFamilyOperationIncome(debitIdPersonal, debitIdFamily, familyIncomeExpected.getAmount(),
                familyIncomeExpected.getDate(), familyIncomeExpected.getCategoryIncome());

        expectedList.add(familyIncomeExpected);
        List<AccountIncome> familyIncomeActual = (ArrayList<AccountIncome>) operationDao.getIncomesFamilyAfterDateByAccountId(debitIdFamily, dateIncome);
        List<AbstractAccountOperation> actualList = new ArrayList<>(familyIncomeActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createFamilyOperationExpense() {
        operationService.createFamilyOperationExpense(null, null, 0, null, null);
        operationService.createFamilyOperationExpense(BigInteger.valueOf(0), BigInteger.valueOf(0), 0, null,
                CategoryExpense.DEFAULT);

        familyExpenseExpected.setId(currentId);
        operationService.createFamilyOperationExpense(debitIdPersonal, debitIdFamily, familyExpenseExpected.getAmount(),
                familyExpenseExpected.getDate(), familyExpenseExpected.getCategoryExpense());

        expectedList.add(familyExpenseExpected);
        List<AccountExpense> familyExpenseActual = (ArrayList<AccountExpense>) operationDao.getExpensesFamilyAfterDateByAccountId(debitIdFamily, dateExpense);
        List<AbstractAccountOperation> actualList = new ArrayList<>(familyExpenseActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createPersonalOperationIncome() {
        operationService.createPersonalOperationIncome(null, 0, null, null);
        operationService.createPersonalOperationIncome(BigInteger.valueOf(0), 0, null, CategoryIncome.DEFAULT);

        personalIncomeExpected.setId(currentId);
        operationService.createPersonalOperationIncome(debitIdPersonal, personalIncomeExpected.getAmount(),
                personalIncomeExpected.getDate(), personalIncomeExpected.getCategoryIncome());

        expectedList.add(personalIncomeExpected);
        List<AccountIncome> personalIncomeActual = (ArrayList<AccountIncome>) operationDao.getIncomesFamilyAfterDateByAccountId(debitIdPersonal, dateIncome);
        List<AbstractAccountOperation> actualList = new ArrayList<>(personalIncomeActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createPersonalOperationExpense() {
        operationService.createPersonalOperationExpense(null, 0, null, null);
        operationService.createPersonalOperationExpense(BigInteger.valueOf(0), 0, null, CategoryExpense.DEFAULT);

        personalExpenseExpected.setId(currentId);
        operationService.createPersonalOperationExpense(debitIdPersonal, personalExpenseExpected.getAmount(),
                personalExpenseExpected.getDate(), personalExpenseExpected.getCategoryExpense());

        expectedList.add(personalExpenseExpected);
        List<AccountExpense> personalExpenseActual = (ArrayList<AccountExpense>) operationDao.getExpensesPersonalAfterDateByAccountId(debitIdPersonal, dateExpense);
        List<AbstractAccountOperation> actualList = new ArrayList<>(personalExpenseActual);
        AssertUtils.assertOperationsCollections(expectedList, actualList);
    }

}
