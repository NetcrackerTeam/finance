package com.netcracker.dao.impl;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class AutoOperationTest {
    protected JdbcTemplate jdbcTemplate;
    private String dateToday = "2019-12-01";
    private String GET_COUNT_OF_AO_OBJECTS = "SELECT COUNT(*) FROM OBJECTS WHERE OBJECT_ID = 9111";
    private String GET_COUNT_OF_AO_ATTRIBUTES = "SELECT COUNT(*) FROM ATTRIBUTES WHERE OBJECT_ID = 9111";
    private String GET_COUNT_OF_AO_OBJREFERENCE = "SELECT COUNT(*) FROM OBJREFERENCE WHERE OBJECT_ID = 9111";

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AutoOperationDao autoOperationDao;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void getFamilyIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncome = autoOperationDao.getFamilyIncomeAutoOperation(new BigInteger("9004"));
        assertEquals(BigInteger.valueOf(9004), autoOperationIncome.getId());
        assertEquals("operationId=9004 userId=1 dayOfMonth=4 amount=3500 category=PRESENTS date=" + dateToday,
                "operationId=" + autoOperationIncome.getId() + " userId=" + autoOperationIncome.getUserId() +
                        " dayOfMonth=" + autoOperationIncome.getDayOfMonth() + " amount=" + autoOperationIncome.getAmount() +
                        " category=" + autoOperationIncome.getCategoryIncome() + " date=" + autoOperationIncome.getDate());
    }

    @Test
    public void getFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpense = autoOperationDao.getFamilyExpenseAutoOperation(new BigInteger("9003"));
        assertEquals(BigInteger.valueOf(9003), autoOperationExpense.getId());
        assertEquals("operationId=9003 userId=1 dayOfMonth=3 amount=3000 category=RESIDENTIAL date=" + dateToday,
                "operationId=" + autoOperationExpense.getId() + " userId=" + autoOperationExpense.getUserId() +
                        " dayOfMonth=" + autoOperationExpense.getDayOfMonth() + " amount=" + autoOperationExpense.getAmount() +
                        " category=" + autoOperationExpense.getCategoryExpense() + " date=" + autoOperationExpense.getDate());
    }

    @Test
    public void getPersonalIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncome = autoOperationDao.getPersonalIncomeAutoOperation(new BigInteger("9002"));
        assertEquals(BigInteger.valueOf(9002), autoOperationIncome.getId());
        assertEquals("operationId=9002 userId=1 dayOfMonth=2 amount=2500 category=SALARY date=" + dateToday,
                "operationId=" + autoOperationIncome.getId() + " userId=" + autoOperationIncome.getUserId() +
                        " dayOfMonth=" + autoOperationIncome.getDayOfMonth() + " amount=" + autoOperationIncome.getAmount() +
                        " category=" + autoOperationIncome.getCategoryIncome() + " date=" + autoOperationIncome.getDate());
    }

    @Test
    public void getPersonalExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpense = autoOperationDao.getPersonalExpenseAutoOperation(new BigInteger("9001"));
        assertEquals(BigInteger.valueOf(9001), autoOperationExpense.getId());
        assertEquals("operationId=9001 userId=1 dayOfMonth=1 amount=2000 category=FOOD date=" + dateToday,
                "operationId=" + autoOperationExpense.getId() + " userId=" + autoOperationExpense.getUserId() +
                        " dayOfMonth=" + autoOperationExpense.getDayOfMonth() + " amount=" + autoOperationExpense.getAmount() +
                        " category=" + autoOperationExpense.getCategoryExpense() + " date=" + autoOperationExpense.getDate());
    }

    @Test
    public void createFamilyIncomeAutoOperation() throws ParseException {
        AutoOperationIncome expAutoOperation = new AutoOperationIncome.Builder()
                .accountId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)))
                .accountUserId(BigInteger.valueOf(1)).categoryIncome(CategoryIncome.PRESENTS).accountAmount(Long.valueOf("150"))
                .dayOfMonth(2).accountDate(AssertUtils.stringToDate(dateToday)).build();
        AutoOperationIncome actualAutoOperation = autoOperationDao.createFamilyIncomeAutoOperation(expAutoOperation,
                new BigInteger("3"));
        AssertUtils.assertAutoOperationIncome(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void createFamilyExpenseAutoOperation() throws ParseException {
        AutoOperationExpense expAutoOperation = new AutoOperationExpense.Builder()
                .accountId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)))
                .accountUserId(BigInteger.valueOf(1)).categoryExpense(CategoryExpense.CHILDREN).accountAmount(Long.valueOf("228"))
                .dayOfMonth(30).accountDate(AssertUtils.stringToDate(dateToday)).build();
        AutoOperationExpense actualAutoOperation = autoOperationDao.createFamilyExpenseAutoOperation(expAutoOperation,
                new BigInteger("3"));
        AssertUtils.assertAutoOperationExpense(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void createPersonalIncomeAutoOperation() throws ParseException {
        AutoOperationIncome expAutoOperation = new AutoOperationIncome.Builder()
                .accountId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)))
                .accountUserId(BigInteger.valueOf(2)).categoryIncome(CategoryIncome.SALARY).accountAmount(Long.valueOf("1000"))
                .dayOfMonth(5).accountDate(AssertUtils.stringToDate(dateToday)).build();
        AutoOperationIncome actualAutoOperation = autoOperationDao.createPersonalIncomeAutoOperation(expAutoOperation);
        AssertUtils.assertAutoOperationIncome(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void createPersonalExpenseAutoOperation() throws ParseException {
        AutoOperationExpense expAutoOperation = new AutoOperationExpense.Builder()
                .accountId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)))
                .accountUserId(BigInteger.valueOf(2)).categoryExpense(CategoryExpense.CHILDREN).accountAmount(Long.valueOf("55075"))
                .dayOfMonth(28).accountDate(AssertUtils.stringToDate(dateToday)).build();
        AutoOperationExpense actualAutoOperation = autoOperationDao.createPersonalExpenseAutoOperation(expAutoOperation);
        AssertUtils.assertAutoOperationExpense(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void getAllTodayOperations() throws ParseException {
        AutoOperationExpense expAutoOperationExpense = new AutoOperationExpense.Builder()
                .accountId(new BigInteger("70")).accountUserId(new BigInteger("52"))
                .categoryExpense(CategoryExpense.CLOTHES).accountAmount(Long.valueOf("16000"))
                .dayOfMonth(1).accountDate(AssertUtils.stringToDate(dateToday)).build();
        List<AbstractAutoOperation> expCollectionExpense = new ArrayList<>();
        expCollectionExpense.add(expAutoOperationExpense);

        AutoOperationIncome expAutoOperationIncome = new AutoOperationIncome.Builder()
                .accountId(new BigInteger("9010")).accountUserId(new BigInteger("52"))
                .categoryIncome(CategoryIncome.OTHER).accountAmount(Long.valueOf("2500"))
                .dayOfMonth(7).accountDate(AssertUtils.stringToDate(dateToday)).build();
        List<AbstractAutoOperation> expCollectionIncome = new ArrayList<>();
        expCollectionIncome.add(expAutoOperationIncome);

        List<AbstractAutoOperation> expectedCollection = new ArrayList<>(expCollectionExpense);
        expectedCollection.addAll(expCollectionIncome);

        List<AbstractAutoOperation> actualCollection = (ArrayList) autoOperationDao.getAllTodayOperations(new BigInteger("52"));

        AssertUtils.assertAutoOperationsCollections(expectedCollection, actualCollection);
    }

    @Test
    public void deleteAutoOperation() {
        int totalCount = 0;
        autoOperationDao.deleteAutoOperation(new BigInteger("9111"));
        int countObjects = jdbcTemplate.queryForObject(GET_COUNT_OF_AO_OBJECTS, Integer.class);
        int countAttributes = jdbcTemplate.queryForObject(GET_COUNT_OF_AO_ATTRIBUTES, Integer.class);
        int countObjreference = jdbcTemplate.queryForObject(GET_COUNT_OF_AO_OBJREFERENCE, Integer.class);
        assertEquals(totalCount, countObjects);
        assertEquals(totalCount, countAttributes);
        assertEquals(totalCount, countObjreference);
    }
}
