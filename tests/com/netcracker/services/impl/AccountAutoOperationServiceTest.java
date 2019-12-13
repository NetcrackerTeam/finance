package com.netcracker.services.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.exception.OperationException;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@Transactional
public class AccountAutoOperationServiceTest {
    @Autowired
    private AccountAutoOperationServiceImpl autoOperationService;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private BigInteger userId = BigInteger.valueOf(74);
    private BigInteger valueZero = BigInteger.valueOf(0);
    private BigInteger currentId = BigInteger.valueOf(97);
    private BigInteger familyDebitId = BigInteger.valueOf(76);
    private BigInteger personalDebitId = BigInteger.valueOf(75);

    private String dayToday = "2019-12-13";
    private int dayOfMonth = 1;
    private List<AbstractAutoOperation> expectedList = new ArrayList<>();

    private AutoOperationIncome autoOperationIncomePersonalExpected;
    private AutoOperationExpense autoOperationExpensePersonalExpected;
    private AutoOperationIncome autoOperationIncomeFamilyExpected;
    private AutoOperationExpense autoOperationExpenseFamilyExpected;

    private String GET_COUNT_OF_AO_OBJECTS = "SELECT COUNT(*) FROM OBJECTS WHERE OBJECT_ID = 95";

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void initializeObjects() throws ParseException {
        autoOperationIncomePersonalExpected = new AutoOperationIncome.Builder().accountId(BigInteger.valueOf(95))
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(13000L).categoryIncome(CategoryIncome.AWARD)
                .accountDate(AssertUtils.stringToDate("2019-12-20")).build();

        autoOperationExpensePersonalExpected = new AutoOperationExpense.Builder().accountId(BigInteger.valueOf(93))
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(17000L).categoryExpense(CategoryExpense.FOOD)
                .accountDate(AssertUtils.stringToDate("2019-12-02")).build();

        autoOperationIncomeFamilyExpected = new AutoOperationIncome.Builder().accountId(BigInteger.valueOf(96))
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(12000L).categoryIncome(CategoryIncome.AWARD)
                .accountDate(AssertUtils.stringToDate("2019-12-15")).build();

        autoOperationExpenseFamilyExpected = new AutoOperationExpense.Builder().accountId(BigInteger.valueOf(94))
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(16000L).categoryExpense(CategoryExpense.FOOD)
                .accountDate(AssertUtils.stringToDate("2019-12-03")).build();
    }

    @Test(expected = OperationException.class)
    public void getFamilyIncomeAutoOperation() {
        autoOperationService.getFamilyIncomeAutoOperation(null);
        autoOperationService.getFamilyIncomeAutoOperation(valueZero);

        AutoOperationIncome autoOperationIncomeFamilyActual = autoOperationService.getFamilyIncomeAutoOperation(BigInteger.valueOf(96));
        AssertUtils.assertAutoOperationIncome(autoOperationIncomeFamilyExpected, autoOperationIncomeFamilyActual);
    }

    @Test(expected = OperationException.class)
    public void getFamilyExpenseAutoOperation() {
        autoOperationService.getFamilyExpenseAutoOperation(null);
        autoOperationService.getFamilyExpenseAutoOperation(valueZero);

        AutoOperationExpense autoOperationExpenseFamilyActual = autoOperationService.getFamilyExpenseAutoOperation(BigInteger.valueOf(94));
        AssertUtils.assertAutoOperationExpense(autoOperationExpenseFamilyExpected, autoOperationExpenseFamilyActual);
    }

    @Test(expected = OperationException.class)
    public void getPersonalIncomeAutoOperation() {
        autoOperationService.getPersonalIncomeAutoOperation(null);
        autoOperationService.getPersonalIncomeAutoOperation(valueZero);

        AutoOperationIncome autoOperationIncomePersonalActual = autoOperationService.getPersonalIncomeAutoOperation(BigInteger.valueOf(95));
        AssertUtils.assertAutoOperationIncome(autoOperationIncomePersonalExpected, autoOperationIncomePersonalActual);
    }

    @Test(expected = OperationException.class)
    public void getPersonalExpenseAutoOperation() {
        autoOperationService.getPersonalExpenseAutoOperation(null);
        autoOperationService.getPersonalExpenseAutoOperation(valueZero);

        AutoOperationExpense autoOperationExpensePersonalActual = autoOperationService.getPersonalExpenseAutoOperation(BigInteger.valueOf(93));
        AssertUtils.assertAutoOperationExpense(autoOperationExpensePersonalExpected, autoOperationExpensePersonalActual);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void deleteAutoOperation() {
        autoOperationService.deleteAutoOperation(null);
        autoOperationService.deleteAutoOperation(valueZero);

        int totalCount = 0;
        autoOperationService.deleteAutoOperation(BigInteger.valueOf(95));
        int countObjects = jdbcTemplate.queryForObject(GET_COUNT_OF_AO_OBJECTS, Integer.class);
        assertEquals(totalCount, countObjects);
    }

    @Test(expected = OperationException.class)
    public void getAllTodayOperationsPersonal() {
        autoOperationService.getAllTodayOperationsPersonal(null, 0);
        autoOperationService.getAllTodayOperationsPersonal(valueZero, 0);

        expectedList.add(autoOperationExpensePersonalExpected);
        expectedList.add(autoOperationIncomePersonalExpected);

        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsPersonal(personalDebitId, dayOfMonth));
        AssertUtils.assertAutoOperationsCollections(expectedList, actualList);
    }

    @Test(expected = OperationException.class)
    public void getAllTodayOperationsFamily() {
        autoOperationService.getAllTodayOperationsFamily(null, 0);
        autoOperationService.getAllTodayOperationsFamily(valueZero, 0);

        expectedList.add(autoOperationExpenseFamilyExpected);
        expectedList.add(autoOperationIncomeFamilyExpected);

        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsFamily(familyDebitId, dayOfMonth));
        AssertUtils.assertAutoOperationsCollections(expectedList, actualList);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createFamilyIncomeAutoOperation() throws ParseException {
        autoOperationService.createFamilyIncomeAutoOperation(null, null, 0,
                0, null);
        autoOperationService.createFamilyIncomeAutoOperation(valueZero, valueZero, 0,
                0, CategoryIncome.DEFAULT);

        autoOperationIncomeFamilyExpected.setId(currentId);
        autoOperationIncomeFamilyExpected.setDate(AssertUtils.stringToDate(dayToday));

        AutoOperationIncome autoOperationIncomeFamilyActual = autoOperationService.createFamilyIncomeAutoOperation(familyDebitId,
                autoOperationIncomeFamilyExpected.getUserId(), autoOperationIncomeFamilyExpected.getDayOfMonth(),
                autoOperationIncomeFamilyExpected.getAmount(), autoOperationIncomeFamilyExpected.getCategoryIncome());
        AssertUtils.assertAutoOperationIncome(autoOperationIncomeFamilyExpected, autoOperationIncomeFamilyActual);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createPersonalIncomeAutoOperation() throws ParseException {
        autoOperationService.createPersonalIncomeAutoOperation(null, null, 0,
                0, null);
        autoOperationService.createPersonalIncomeAutoOperation(valueZero, valueZero, 0,
                0, CategoryIncome.DEFAULT);

        autoOperationIncomePersonalExpected.setId(currentId);
        autoOperationIncomePersonalExpected.setDate(AssertUtils.stringToDate(dayToday));

        AutoOperationIncome autoOperationIncomePersonalActual = autoOperationService.createPersonalIncomeAutoOperation(personalDebitId,
                autoOperationIncomePersonalExpected.getUserId(), autoOperationIncomePersonalExpected.getDayOfMonth(),
                autoOperationIncomePersonalExpected.getAmount(), autoOperationIncomePersonalExpected.getCategoryIncome());
        AssertUtils.assertAutoOperationIncome(autoOperationIncomePersonalExpected, autoOperationIncomePersonalActual);

    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createFamilyExpenseAutoOperation() throws ParseException {
        autoOperationService.createFamilyExpenseAutoOperation(null, null, 0,
                0, null);
        autoOperationService.createFamilyExpenseAutoOperation(valueZero, valueZero, 0,
                0, CategoryExpense.DEFAULT);

        autoOperationExpenseFamilyExpected.setId(currentId);
        autoOperationExpenseFamilyExpected.setDate(AssertUtils.stringToDate(dayToday));

        AutoOperationExpense autoOperationExpenseFamilyActual = autoOperationService.createFamilyExpenseAutoOperation(familyDebitId,
                autoOperationExpenseFamilyExpected.getUserId(), autoOperationExpenseFamilyExpected.getDayOfMonth(),
                autoOperationExpenseFamilyExpected.getAmount(), autoOperationExpenseFamilyExpected.getCategoryExpense());
        AssertUtils.assertAutoOperationExpense(autoOperationExpenseFamilyExpected, autoOperationExpenseFamilyActual);
    }

    @Rollback
    @Test(expected = OperationException.class)
    public void createPersonalExpenseAutoOperation() throws ParseException {
        autoOperationService.createPersonalExpenseAutoOperation(null, null, 0,
                0, null);
        autoOperationService.createPersonalExpenseAutoOperation(valueZero, valueZero, 0,
                0, CategoryExpense.DEFAULT);

        autoOperationExpensePersonalExpected.setId(currentId);
        autoOperationExpensePersonalExpected.setDate(AssertUtils.stringToDate(dayToday));

        AutoOperationExpense autoOperationExpensePersonalActual = autoOperationService.createPersonalExpenseAutoOperation(personalDebitId,
                autoOperationExpensePersonalExpected.getUserId(), autoOperationExpensePersonalExpected.getDayOfMonth(),
                autoOperationExpensePersonalExpected.getAmount(), autoOperationExpensePersonalExpected.getCategoryExpense());
        AssertUtils.assertAutoOperationExpense(autoOperationExpensePersonalExpected, autoOperationExpensePersonalActual);
    }

}
