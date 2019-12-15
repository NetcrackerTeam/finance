package com.netcracker.dao.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.models.CreditOperation;
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
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@Transactional
public class CreditOperationTest {
    private JdbcTemplate jdbcTemplate;
    private String GET_COUNT_OF_CO_OBJECTS = "SELECT COUNT(*) FROM OBJECTS WHERE OBJECT_ID = 12";

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CreditOperationDao creditOperationDao;

    private CreditOperation creditOperationPersonalExpected;
    private CreditOperation creditOperationFamilyExpected;

    private String dateString = "2019-12-13";
    private LocalDate date = LocalDate.parse(dateString);

    private String dateTodayString = "2019-12-15";
    private LocalDate dateToday = LocalDate.parse(dateTodayString);

    private BigInteger operationIdPersonal = BigInteger.valueOf(12);
    private BigInteger creditAccountPersonalId = BigInteger.valueOf(10);

    private BigInteger userId = BigInteger.valueOf(1);
    private BigInteger operationIdFamily = BigInteger.valueOf(13);
    private BigInteger creditAccountFamilyId = BigInteger.valueOf(11);

    private List<CreditOperation> expectedCollection = new ArrayList<>();

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void initializeObjects() {
        creditOperationPersonalExpected = new CreditOperation(1000.00, date);
        creditOperationPersonalExpected.setCreditOperationId(operationIdPersonal);

        creditOperationFamilyExpected = new CreditOperation(2000.00, date);
        creditOperationFamilyExpected.setCreditOperationId(operationIdFamily);
    }

    @Test
    public void getCreditOperationPersonal() {
        CreditOperation creditOperationActual = creditOperationDao.getCreditOperationPersonal(operationIdPersonal);
        AssertUtils.assertCreditOperation(creditOperationPersonalExpected, creditOperationActual);
    }

    @Test
    public void getCreditOperationFamily() {
        CreditOperation creditOperationActual = creditOperationDao.getCreditOperationFamily(operationIdFamily);
        AssertUtils.assertCreditOperation(creditOperationFamilyExpected, creditOperationActual);
    }

    @Rollback
    @Test
    public void createFamilyCreditOperation() {
        creditOperationFamilyExpected.setDate(dateToday);
        CreditOperation creditOperationActual = creditOperationDao.createFamilyCreditOperation(creditOperationFamilyExpected.getAmount(),
                creditOperationFamilyExpected.getDate(), creditAccountFamilyId, userId);
        creditOperationFamilyExpected.setCreditOperationId(creditOperationActual.getCreditOperationId());
        AssertUtils.assertCreditOperation(creditOperationFamilyExpected, creditOperationActual);
    }

    @Rollback
    @Test
    public void createPersonalCreditOperation() {
        creditOperationPersonalExpected.setDate(dateToday);
        CreditOperation creditOperationActual = creditOperationDao.createPersonalCreditOperation(creditOperationPersonalExpected.getAmount(),
                creditOperationPersonalExpected.getDate(), creditAccountPersonalId);
        creditOperationPersonalExpected.setCreditOperationId(creditOperationActual.getCreditOperationId());
        AssertUtils.assertCreditOperation(creditOperationPersonalExpected, creditOperationActual);
    }

    @Rollback
    @Test
    public void getAllCreditOperationsByCreditFamilyId() {
        List<CreditOperation> actualCollection = creditOperationDao.getAllCreditOperationsByCreditFamilyId(creditAccountFamilyId);
        expectedCollection.add(creditOperationFamilyExpected);

        AssertUtils.assertCreditOperationsCollections(expectedCollection, actualCollection);
    }

    @Rollback
    @Test
    public void getAllCreditOperationsByCreditPersonalId() {
        List<CreditOperation> actualCollection = creditOperationDao.getAllCreditOperationsByCreditPersonalId(creditAccountPersonalId);
        expectedCollection.add(creditOperationPersonalExpected);

        AssertUtils.assertCreditOperationsCollections(expectedCollection, actualCollection);
    }

    @Rollback
    @Test
    public void deleteCreditOperation() {
        int totalCount = 0;
        creditOperationDao.deleteCreditOperation(operationIdPersonal);
        int countObjects = jdbcTemplate.queryForObject(GET_COUNT_OF_CO_OBJECTS, Integer.class);
        assertEquals(totalCount, countObjects);
    }
}
