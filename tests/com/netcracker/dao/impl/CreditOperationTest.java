package com.netcracker.dao.impl;

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
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@Transactional
public class CreditOperationTest {
    private JdbcTemplate jdbcTemplate;
    private String dateToday = "2019-12-01";
    private String GET_COUNT_OF_CO_OBJECTS = "SELECT COUNT(*) FROM OBJECTS WHERE OBJECT_ID = 8010";

    @Autowired
    private DataSource dataSource;
    @Autowired
    private CreditOperationDao creditOperationDao;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void getCreditOperationPersonal() {
        CreditOperation creditOperation = creditOperationDao.getCreditOperationPersonal(new BigInteger("8001"));
        assertEquals(BigInteger.valueOf(8001), creditOperation.getCreditOperationId());
        assertEquals("CreditOperation{creditOperationId=8001, amount=2000, date=" + dateToday + "}",
                "CreditOperation{creditOperationId=" + creditOperation.getCreditOperationId() + ", amount="
        + creditOperation.getAmount() + ", date=" + creditOperation.getDate() + "}");
    }

    @Test
    public void getCreditOperationFamily() {
        CreditOperation creditOperation = creditOperationDao.getCreditOperationFamily(new BigInteger("8002"));
        assertEquals(BigInteger.valueOf(8002), creditOperation.getCreditOperationId());
        assertEquals("CreditOperation{creditOperationId=8002, amount=2500, date=" + dateToday + "}",
                "CreditOperation{creditOperationId=" + creditOperation.getCreditOperationId() + ", amount="
                        + creditOperation.getAmount() + ", date=" + creditOperation.getDate() + "}");
    }

    @Rollback
    @Test
    public void createFamilyCreditOperation() throws ParseException {
        CreditOperation expectedCreditOperation = new CreditOperation(Long.valueOf("6666"),
                AssertUtils.stringToDate("2000-01-01"));
        expectedCreditOperation.setCreditOperationId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)));
        creditOperationDao.createFamilyCreditOperation(Long.valueOf("6666"), AssertUtils.stringToDate("2000-01-01"),
                new BigInteger("84"), new BigInteger("74"));
        CreditOperation actualCreditOperation = creditOperationDao.getCreditOperationFamily(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)));
        AssertUtils.assertCreditOperation(expectedCreditOperation, actualCreditOperation);
    }

    @Rollback
    @Test
    public void createPersonalCreditOperation() throws ParseException {
        CreditOperation expectedCreditOperation = new CreditOperation(Long.valueOf("1488"),
                AssertUtils.stringToDate("2003-03-03"));
        expectedCreditOperation.setCreditOperationId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)));
        creditOperationDao.createPersonalCreditOperation(Long.valueOf("1488"), AssertUtils.stringToDate("2003-03-03"),
                new BigInteger("60"));
        CreditOperation actualCreditOperation = creditOperationDao.getCreditOperationPersonal(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(jdbcTemplate)));
        AssertUtils.assertCreditOperation(expectedCreditOperation, actualCreditOperation);
    }

    @Test
    public void getAllCreditOperationsByCreditFamilyId() throws ParseException {
        List<CreditOperation> actualCollection = (ArrayList) creditOperationDao.getAllCreditOperationsByCreditFamilyId(new BigInteger("11"));
        List<CreditOperation> expectedCollection = new ArrayList<>();
        CreditOperation expectedOperation1 = new CreditOperation(Long.valueOf("2500"),
                AssertUtils.stringToDate(dateToday));
        expectedOperation1.setCreditOperationId(new BigInteger("8002"));
        CreditOperation expectedOperation2 = new CreditOperation(Long.valueOf("2000"),
                AssertUtils.stringToDate(dateToday));
        expectedOperation2.setCreditOperationId(new BigInteger("13"));
        expectedCollection.add(expectedOperation1);
        expectedCollection.add(expectedOperation2);

        AssertUtils.assertCreditOperationsCollections(expectedCollection, actualCollection);
    }

    @Test
    public void getAllCreditOperationsByCreditPersonalId() throws ParseException {
        List<CreditOperation> actualCollection = (ArrayList) creditOperationDao.getAllCreditOperationsByCreditPersonalId(new BigInteger("10"));
        List<CreditOperation> expectedCollection = new ArrayList<>();
        CreditOperation expectedOperation1 = new CreditOperation(Long.valueOf("2000"),
                AssertUtils.stringToDate(dateToday));
        expectedOperation1.setCreditOperationId(new BigInteger("8001"));
        CreditOperation expectedOperation2 = new CreditOperation(Long.valueOf("1000"),
                AssertUtils.stringToDate("2001-01-01"));
        expectedOperation2.setCreditOperationId(new BigInteger("12"));
        expectedCollection.add(expectedOperation1);
        expectedCollection.add(expectedOperation2);

        AssertUtils.assertCreditOperationsCollections(expectedCollection, actualCollection);
    }

    @Rollback
    @Test
    public void deleteCreditOperation() {
        int totalCount = 0;
        creditOperationDao.deleteCreditOperation(new BigInteger("8010"));
        int countObjects = jdbcTemplate.queryForObject(GET_COUNT_OF_CO_OBJECTS, Integer.class);
        assertEquals(totalCount, countObjects);
    }
}
