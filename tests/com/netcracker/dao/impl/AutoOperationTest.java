package com.netcracker.dao.impl;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.impl.mapper.CurrentSequenceMapper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class AutoOperationTest {
    protected JdbcTemplate jdbcTemplate;
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
        AutoOperationIncome autoOperationIncome = autoOperationDao.getFamilyIncomeAutoOperation(9004);
        assertEquals(BigInteger.valueOf(9004), autoOperationIncome.getId());
        assertEquals("operationId=9004 userId=1 dayOfMonth=4 amount=3500 categoryId=34 date=2019-11-28",
                "operationId=" + autoOperationIncome.getId() + " userId=" + autoOperationIncome.getUserId() +
                        " dayOfMonth=" + autoOperationIncome.getDayOfMonth() + " amount=" + autoOperationIncome.getAmount() +
                        " categoryId=" + autoOperationIncome.getCategoryIncome().getId() + " date=" + autoOperationIncome.getDate());
    }

    @Test
    public void getFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpense = autoOperationDao.getFamilyExpenseAutoOperation(9003);
        assertEquals(BigInteger.valueOf(9003), autoOperationExpense.getId());
        assertEquals("operationId=9003 userId=1 dayOfMonth=3 amount=3000 categoryId=16 date=2019-11-29",
                "operationId=" + autoOperationExpense.getId() + " userId=" + autoOperationExpense.getUserId() +
                        " dayOfMonth=" + autoOperationExpense.getDayOfMonth() + " amount=" + autoOperationExpense.getAmount() +
                        " categoryId=" + autoOperationExpense.getCategoryExpense().getId() + " date=" + autoOperationExpense.getDate());
    }

    @Test
    public void getPersonalIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncome = autoOperationDao.getPersonalIncomeAutoOperation(9002);
        assertEquals(BigInteger.valueOf(9002), autoOperationIncome.getId());
        assertEquals("operationId=9002 userId=1 dayOfMonth=2 amount=2500 categoryId=33 date=2019-11-29",
                "operationId=" + autoOperationIncome.getId() + " userId=" + autoOperationIncome.getUserId() +
                        " dayOfMonth=" + autoOperationIncome.getDayOfMonth() + " amount=" + autoOperationIncome.getAmount() +
                        " categoryId=" + autoOperationIncome.getCategoryIncome().getId() + " date=" + autoOperationIncome.getDate());
    }

    @Test
    public void getPersonalExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpense = autoOperationDao.getPersonalExpenseAutoOperation(9001);
        assertEquals(BigInteger.valueOf(9001), autoOperationExpense.getId());
        assertEquals("operationId=9003 userId=1 dayOfMonth=1 amount=2000 categoryId=15 date=2019-11-29",
                "operationId=" + autoOperationExpense.getId() + " userId=" + autoOperationExpense.getUserId() +
                        " dayOfMonth=" + autoOperationExpense.getDayOfMonth() + " amount=" + autoOperationExpense.getAmount() +
                        " categoryId=" + autoOperationExpense.getCategoryExpense().getId() + " date=" + autoOperationExpense.getDate());
    }

    @Test
    public void createFamilyIncomeAutoOperation() throws ParseException {
        AutoOperationIncome expAutoOperation = new AutoOperationIncome.Builder().accountId(BigInteger.valueOf(getCurrentSequenceId()))
                .accountUserId(BigInteger.valueOf(1)).categoryIncome(CategoryIncome.PERSENTS).accountAmount(Long.valueOf("150.50"))
                .dayOfMonth(2).accountDate(stringToDate("2019-11-29")).build();
        AutoOperationIncome actualAutoOperation = autoOperationDao.createFamilyIncomeAutoOperation(expAutoOperation);
        AssertUtils.assertAutoOperationIncome(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void createFamilyExpenseAutoOperation() throws ParseException {
        AutoOperationExpense expAutoOperation = new AutoOperationExpense.Builder().accountId(BigInteger.valueOf(getCurrentSequenceId()))
                .accountUserId(BigInteger.valueOf(1)).categoryExpense(CategoryExpense.CHILDREN).accountAmount(Long.valueOf("228"))
                .dayOfMonth(30).accountDate(stringToDate("2019-11-29")).build();
        AutoOperationExpense actualAutoOperation = autoOperationDao.createFamilyExpenseAutoOperation(expAutoOperation);
        AssertUtils.assertAutoOperationExpense(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void createPersonalIncomeAutoOperation() throws ParseException {
        AutoOperationIncome expAutoOperation = new AutoOperationIncome.Builder().accountId(BigInteger.valueOf(getCurrentSequenceId()))
                .accountUserId(BigInteger.valueOf(2)).categoryIncome(CategoryIncome.SALARY).accountAmount(Long.valueOf("1000"))
                .dayOfMonth(5).accountDate(stringToDate("2019-11-29")).build();
        AutoOperationIncome actualAutoOperation = autoOperationDao.createPersonalIncomeAutoOperation(expAutoOperation);
        AssertUtils.assertAutoOperationIncome(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void createPersonalExpenseAutoOperation() throws ParseException {
        AutoOperationExpense expAutoOperation = new AutoOperationExpense.Builder().accountId(BigInteger.valueOf(getCurrentSequenceId()))
                .accountUserId(BigInteger.valueOf(2)).categoryExpense(CategoryExpense.CHILDREN).accountAmount(Long.valueOf("550.75"))
                .dayOfMonth(28).accountDate(stringToDate("2019-11-29")).build();
        AutoOperationExpense actualAutoOperation = autoOperationDao.createPersonalExpenseAutoOperation(expAutoOperation);
        AssertUtils.assertAutoOperationExpense(expAutoOperation, actualAutoOperation);
    }

    @Test
    public void getAllTodayOperations() throws ParseException {
        AutoOperationIncome expAutoOperation = new AutoOperationIncome.Builder().accountId(BigInteger.valueOf(getCurrentSequenceId()))
                .accountUserId(BigInteger.valueOf(1)).categoryIncome(CategoryIncome.PERSENTS).accountAmount(Long.valueOf("3500"))
                .dayOfMonth(4).accountDate(stringToDate("2019-11-29")).build();
        Collection<AbstractAutoOperation> expCollection = new ArrayList<>();
        expCollection.add(expAutoOperation);
        Collection<AbstractAutoOperation> actualCollection = autoOperationDao.getAllTodayOperations(1);
        assertEquals(expCollection, actualCollection);
    }

    private Date stringToDate(String stringToParse) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(stringToParse);
    }

    private Integer getCurrentSequenceId() {
        String GET_CURRENT_SEQUENCE_ID = "select last_number from user_sequences where sequence_name = 'OBJECTS_ID_S'";
        int newId = jdbcTemplate.queryForObject(GET_CURRENT_SEQUENCE_ID, new CurrentSequenceMapper());
        return newId++;
    }
}
