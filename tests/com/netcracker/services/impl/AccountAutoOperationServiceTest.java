package com.netcracker.services.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.exception.ErrorsMap;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    private BigInteger currentId;
    private BigInteger familyDebitId = BigInteger.valueOf(76);
    private BigInteger personalDebitId = BigInteger.valueOf(75);

    private String dateTodayString = "2019-12-27";
    private LocalDate dateToday = LocalDate.parse(dateTodayString);
    private int dayOfMonth = 1;
    private List<AbstractAutoOperation> expectedList = new ArrayList<>();

    private AutoOperationIncome autoOperationIncomePersonalExpected;
    private AutoOperationExpense autoOperationExpensePersonalExpected;
    private AutoOperationIncome autoOperationIncomeFamilyExpected;
    private AutoOperationExpense autoOperationExpenseFamilyExpected;
    private AutoOperationExpense autoOperationExpenseExpected;
    private AutoOperationIncome autoOperationIncomeExpected;

    private BigInteger familyIncomeObjectIdAO = BigInteger.valueOf(96);
    private BigInteger familyExpenseObjectIdAO = BigInteger.valueOf(94);
    private BigInteger personalIncomeObjectIdAO = BigInteger.valueOf(95);
    private BigInteger personalExpenseObjectIdAO = BigInteger.valueOf(93);

    private String GET_COUNT_OF_AO_OBJECTS = "SELECT COUNT(*) FROM OBJECTS WHERE OBJECT_ID = 96";

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void initializeObjects() {
        autoOperationIncomePersonalExpected = new AutoOperationIncome.Builder().accountId(personalIncomeObjectIdAO)
                .dayOfMonth(dayOfMonth).accountAmount(13000.00).categoryIncome(CategoryIncome.AWARD).accountDebitId(personalDebitId)
                .accountDate(LocalDate.parse("2019-12-20")).build();

        autoOperationExpensePersonalExpected = new AutoOperationExpense.Builder().accountId(personalExpenseObjectIdAO)
                .dayOfMonth(dayOfMonth).accountAmount(17000.00).categoryExpense(CategoryExpense.FOOD).accountDebitId(personalDebitId)
                .accountDate(LocalDate.parse("2019-12-02")).build();

        autoOperationIncomeFamilyExpected = new AutoOperationIncome.Builder().accountId(familyIncomeObjectIdAO)
                .dayOfMonth(dayOfMonth).accountAmount(12000.00).categoryIncome(CategoryIncome.AWARD).accountDebitId(familyDebitId)
                .accountDate(LocalDate.parse("2019-12-15")).build();

        autoOperationExpenseFamilyExpected = new AutoOperationExpense.Builder().accountId(familyExpenseObjectIdAO)
                .dayOfMonth(dayOfMonth).accountAmount(16000.00).categoryExpense(CategoryExpense.FOOD).accountDebitId(familyDebitId)
                .accountDate(LocalDate.parse("2019-12-03")).build();

        autoOperationExpenseExpected = new AutoOperationExpense.Builder().accountId(BigInteger.valueOf(9001))
                .dayOfMonth(2).accountAmount(2000.00).categoryExpense(CategoryExpense.FOOD).accountDebitId(BigInteger.valueOf(2))
                .accountDate(LocalDate.parse("2019-12-16")).build();

        autoOperationIncomeExpected = new AutoOperationIncome.Builder().accountId(BigInteger.valueOf(9004))
                .dayOfMonth(2).accountAmount(3500.00).categoryIncome(CategoryIncome.PRESENTS).accountDebitId(BigInteger.valueOf(3))
                .accountDate(LocalDate.parse("2019-12-16")).build();
    }

    @Test(expected = RuntimeException.class)
    public void getFamilyIncomeAutoOperationCheckNull(){
        autoOperationService.getFamilyIncomeAutoOperation(null);
    }

    @Test
    public void getFamilyIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomeFamilyActual = autoOperationService.getFamilyIncomeAutoOperation(familyIncomeObjectIdAO);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomeFamilyExpected, autoOperationIncomeFamilyActual);
    }

    @Test(expected = RuntimeException.class)
    public void getFamilyExpenseAutoOperationCheckNull(){
        autoOperationService.getFamilyExpenseAutoOperation(null);
    }

    @Test
    public void getFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpenseFamilyActual = autoOperationService.getFamilyExpenseAutoOperation(familyExpenseObjectIdAO);
        AssertUtils.assertAutoOperationExpense(autoOperationExpenseFamilyExpected, autoOperationExpenseFamilyActual);
    }

    @Test(expected = RuntimeException.class)
    public void getPersonalIncomeAutoOperationCheckNull(){
        autoOperationService.getPersonalIncomeAutoOperation(null);
    }

    @Test
    public void getPersonalIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomePersonalActual = autoOperationService.getPersonalIncomeAutoOperation(personalIncomeObjectIdAO);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomePersonalExpected, autoOperationIncomePersonalActual);
    }

    @Test(expected = RuntimeException.class)
    public void getPersonalExpenseAutoOperationCheckNull(){
        autoOperationService.getPersonalExpenseAutoOperation(null);
    }

    @Test
    public void getPersonalExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpensePersonalActual = autoOperationService.getPersonalExpenseAutoOperation(personalExpenseObjectIdAO);
        AssertUtils.assertAutoOperationExpense(autoOperationExpensePersonalExpected, autoOperationExpensePersonalActual);
    }

    @Test(expected = RuntimeException.class)
    public void deleteAutoOperationCheckNull() {
        autoOperationService.deleteAutoOperation(null);
    }

    @Rollback
    @Test
    public void deleteAutoOperation() {
        int totalCount = 0;
        autoOperationService.deleteAutoOperation(familyIncomeObjectIdAO);
        int countObjects = jdbcTemplate.queryForObject(GET_COUNT_OF_AO_OBJECTS, Integer.class);
        assertEquals(totalCount, countObjects);
    }

    @Test(expected = RuntimeException.class)
    public void createFamilyIncomeAutoOperationCheckNull() {
        autoOperationService.createFamilyIncomeAutoOperation(autoOperationIncomeFamilyExpected, null, null);
    }

    /*@Rollback
    @Test
    public void createFamilyIncomeAutoOperation() {
       /AutoOperationIncome autoOperationIncomeFamilyActual = autoOperationService.createFamilyIncomeAutoOperation(autoOperationIncomeFamilyExpected, userId, familyDebitId);
        currentId = autoOperationIncomeFamilyActual.getId();
        setDateAndId(autoOperationIncomeFamilyExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomeFamilyExpected, autoOperationIncomeFamilyActual);
    }*/

    @Test(expected = RuntimeException.class)
    public void createFamilyExpenseAutoOperationCheckNull() {
        autoOperationService.createFamilyExpenseAutoOperation(autoOperationExpenseFamilyExpected, null, null);
    }

    /*@Rollback
    @Test
    public void createFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpenseFamilyActual = autoOperationService.createFamilyExpenseAutoOperation(autoOperationExpenseFamilyExpected, userId, familyDebitId);
        currentId = autoOperationExpenseFamilyActual.getId();
        setDateAndId(autoOperationExpenseFamilyExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationExpense(autoOperationExpenseFamilyExpected, autoOperationExpenseFamilyActual);
    }*/

    private void setDateAndId(AbstractAutoOperation autoOperation, BigInteger newId, LocalDate newDate) {
        autoOperation.setId(newId);
        autoOperation.setDate(newDate);
    }

    @Test(expected = RuntimeException.class)
    public void createPersonalIncomeAutoOperationCheckNull() {
        autoOperationService.createPersonalIncomeAutoOperation(autoOperationIncomePersonalExpected, null);
    }

    /*@Rollback
    @Test
    public void createPersonalIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomePersonalActual = autoOperationService.createPersonalIncomeAutoOperation(autoOperationIncomePersonalExpected, personalDebitId);
        currentId = autoOperationIncomePersonalActual.getId();
        setDateAndId(autoOperationIncomePersonalExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomePersonalExpected, autoOperationIncomePersonalActual);
    }*/

    @Test(expected = RuntimeException.class)
    public void createPersonalExpenseAutoOperationCheckNull() {
        autoOperationService.createPersonalExpenseAutoOperation(autoOperationExpensePersonalExpected, null);
    }

    /*@Rollback
    @Test
    public void createPersonalExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpensePersonalActual = autoOperationService.createPersonalExpenseAutoOperation(autoOperationExpensePersonalExpected, personalDebitId);
        currentId = autoOperationExpensePersonalActual.getId();
        setDateAndId(autoOperationExpensePersonalExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationExpense(autoOperationExpensePersonalExpected, autoOperationExpensePersonalActual);
    }*/

    @Test
    public void getAllTodayOperationsPersonal() {
        expectedList.add(autoOperationExpenseExpected);

        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsPersonalExpense(2));
        AssertUtils.assertAutoOperationsCollections(expectedList, actualList);
    }

    @Test
    public void getAllTodayOperationsPersonalEmptyList() {
        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsPersonalIncome(4));
        AssertUtils.assertAutoOperationsCollections(Collections.emptyList(), actualList);
    }

    @Test(expected = OperationException.class)
    public void getAllTodayOperationsPersonalCheckNull() {
        autoOperationService.getAllTodayOperationsPersonalIncome(0);
    }

    @Test
    public void getAllTodayOperationsFamily() {
        expectedList.add(autoOperationIncomeExpected);

        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsFamilyIncome(2));
        AssertUtils.assertAutoOperationsCollections(expectedList, actualList);
    }

    @Test
    public void getAllTodayOperationsFamilyEmptyList() {
        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsFamilyIncome(4));
        AssertUtils.assertAutoOperationsCollections(Collections.emptyList(), actualList);
    }

    @Test(expected = OperationException.class)
    public void getAllTodayOperationsFamilyCheckNull() {
        autoOperationService.getAllTodayOperationsFamilyIncome(0);
    }

}
