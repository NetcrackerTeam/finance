package com.netcracker.services.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.AutoOperationDao;
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
import java.util.Date;
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
    private AutoOperationDao autoOperationDao;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private BigInteger userId = BigInteger.valueOf(74);
    private BigInteger currentId;
    private BigInteger familyDebitId = BigInteger.valueOf(76);
    private BigInteger personalDebitId = BigInteger.valueOf(75);

    private String dateTodayString = "2019-12-14";
    private Date dateToday = AssertUtils.stringToDate(dateTodayString);
    private int dayOfMonth = 1;
    private List<AbstractAutoOperation> expectedList = new ArrayList<>();

    private AutoOperationIncome autoOperationIncomePersonalExpected;
    private AutoOperationExpense autoOperationExpensePersonalExpected;
    private AutoOperationIncome autoOperationIncomeFamilyExpected;
    private AutoOperationExpense autoOperationExpenseFamilyExpected;

    private BigInteger familyIncomeObjectIdAO = BigInteger.valueOf(96);
    private BigInteger familyExpenseObjectIdAO = BigInteger.valueOf(94);
    private BigInteger personalIncomeObjectIdAO = BigInteger.valueOf(95);
    private BigInteger personalExpenseObjectIdAO = BigInteger.valueOf(93);

    private String GET_COUNT_OF_AO_OBJECTS = "SELECT COUNT(*) FROM OBJECTS WHERE OBJECT_ID = 96";

    public AccountAutoOperationServiceTest() throws ParseException {

    }

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void initializeObjects() throws ParseException {
        autoOperationIncomePersonalExpected = new AutoOperationIncome.Builder().accountId(personalIncomeObjectIdAO)
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(13000L).categoryIncome(CategoryIncome.AWARD)
                .accountDate(AssertUtils.stringToDate("2019-12-20")).build();

        autoOperationExpensePersonalExpected = new AutoOperationExpense.Builder().accountId(personalExpenseObjectIdAO)
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(17000L).categoryExpense(CategoryExpense.FOOD)
                .accountDate(AssertUtils.stringToDate("2019-12-02")).build();

        autoOperationIncomeFamilyExpected = new AutoOperationIncome.Builder().accountId(familyIncomeObjectIdAO)
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(12000L).categoryIncome(CategoryIncome.AWARD)
                .accountDate(AssertUtils.stringToDate("2019-12-15")).build();

        autoOperationExpenseFamilyExpected = new AutoOperationExpense.Builder().accountId(familyExpenseObjectIdAO)
                .accountUserId(userId).dayOfMonth(dayOfMonth).accountAmount(16000L).categoryExpense(CategoryExpense.FOOD)
                .accountDate(AssertUtils.stringToDate("2019-12-03")).build();
    }

    @Test
    public void getFamilyIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomeFamilyActual = autoOperationService.getFamilyIncomeAutoOperation(familyIncomeObjectIdAO);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomeFamilyExpected, autoOperationIncomeFamilyActual);
    }

    @Test
    public void getFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpenseFamilyActual = autoOperationService.getFamilyExpenseAutoOperation(familyExpenseObjectIdAO);
        AssertUtils.assertAutoOperationExpense(autoOperationExpenseFamilyExpected, autoOperationExpenseFamilyActual);
    }

    @Test
    public void getPersonalIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomePersonalActual = autoOperationService.getPersonalIncomeAutoOperation(personalIncomeObjectIdAO);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomePersonalExpected, autoOperationIncomePersonalActual);
    }

    @Test
    public void getPersonalExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpensePersonalActual = autoOperationService.getPersonalExpenseAutoOperation(personalExpenseObjectIdAO);
        AssertUtils.assertAutoOperationExpense(autoOperationExpensePersonalExpected, autoOperationExpensePersonalActual);
    }

    @Rollback
    @Test
    public void deleteAutoOperation() {
        int totalCount = 0;
        autoOperationService.deleteAutoOperation(familyIncomeObjectIdAO);
        int countObjects = jdbcTemplate.queryForObject(GET_COUNT_OF_AO_OBJECTS, Integer.class);
        assertEquals(totalCount, countObjects);
    }

    @Rollback
    @Test
    public void createFamilyIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomeFamilyActual = autoOperationService.createFamilyIncomeAutoOperation(autoOperationIncomeFamilyExpected, userId, familyDebitId);
        currentId = autoOperationIncomeFamilyActual.getId();
        setDateAndId(autoOperationIncomeFamilyExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomeFamilyExpected, autoOperationIncomeFamilyActual);
    }

    @Rollback
    @Test
    public void createFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpenseFamilyActual = autoOperationService.createFamilyExpenseAutoOperation(autoOperationExpenseFamilyExpected, userId, familyDebitId);
        currentId = autoOperationExpenseFamilyActual.getId();
        setDateAndId(autoOperationExpenseFamilyExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationExpense(autoOperationExpenseFamilyExpected, autoOperationExpenseFamilyActual);
    }

    private void setDateAndId(AbstractAutoOperation autoOperation, BigInteger newId, Date newDate) {
        autoOperation.setId(newId);
        autoOperation.setDate(newDate);
    }

    @Rollback
    @Test
    public void createPersonalIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncomePersonalActual = autoOperationService.createPersonalIncomeAutoOperation(autoOperationIncomePersonalExpected, personalDebitId);
        currentId = autoOperationIncomePersonalActual.getId();
        setDateAndId(autoOperationIncomePersonalExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationIncome(autoOperationIncomePersonalExpected, autoOperationIncomePersonalActual);
    }

    @Rollback
    @Test
    public void createPersonalExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpensePersonalActual = autoOperationService.createPersonalExpenseAutoOperation(autoOperationExpensePersonalExpected, personalDebitId);
        currentId = autoOperationExpensePersonalActual.getId();
        setDateAndId(autoOperationExpensePersonalExpected, currentId, dateToday);
        AssertUtils.assertAutoOperationExpense(autoOperationExpensePersonalExpected, autoOperationExpensePersonalActual);
    }

    @Test
    public void getAllTodayOperationsPersonal() {
        expectedList.add(autoOperationExpensePersonalExpected);
        expectedList.add(autoOperationIncomePersonalExpected);

        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsPersonal(personalDebitId, dayOfMonth));
        AssertUtils.assertAutoOperationsCollections(expectedList, actualList);
    }

    @Test
    public void getAllTodayOperationsFamily() {
        expectedList.add(autoOperationExpenseFamilyExpected);
        expectedList.add(autoOperationIncomeFamilyExpected);

        List<AbstractAutoOperation> actualList = new ArrayList<>(autoOperationService.getAllTodayOperationsFamily(familyDebitId, dayOfMonth));
        AssertUtils.assertAutoOperationsCollections(expectedList, actualList);
    }

}
