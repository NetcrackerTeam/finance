package com.netcracker.dao.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.impl.mapper.CurrentSequenceMapper;
import com.netcracker.models.CreditOperation;
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
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class CreditOperationTest {
    protected JdbcTemplate jdbcTemplate;
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
        CreditOperation creditOperation = creditOperationDao.getCreditOperationPersonal(8001);
        assertEquals(BigInteger.valueOf(8001), creditOperation.getCreditOperationId());
        assertEquals("CreditOperation{creditOperationId=8001, amount=2000, date=2019-11-28}",
                "CreditOperation{creditOperationId=" + creditOperation.getCreditOperationId() + ", amount="
        + creditOperation.getAmount() + ", date=" + creditOperation.getDate() + "}");
    }

    @Test
    public void getCreditOperationFamily() {
        CreditOperation creditOperation = creditOperationDao.getCreditOperationFamily(105);
        assertEquals(BigInteger.valueOf(105), creditOperation.getCreditOperationId());
        assertEquals("CreditOperation{creditOperationId=105, amount=6666, date=2000-01-01}",
                "CreditOperation{creditOperationId=" + creditOperation.getCreditOperationId() + ", amount="
                        + creditOperation.getAmount() + ", date=" + creditOperation.getDate() + "}");
    }

    @Test
    public void createFamilyCreditOperation() throws ParseException {
        CreditOperation expectedCreditOperation = new CreditOperation(Long.valueOf("6666"),
                stringToDate("2000-01-01"));
        expectedCreditOperation.setCreditOperationId(BigInteger.valueOf(getCurrentSequenceId()));
        CreditOperation actualCreditOperation = creditOperationDao.createFamilyCreditOperation(expectedCreditOperation,
                84, 74);
        AssertUtils.assertCreditOperation(expectedCreditOperation, actualCreditOperation);
    }

    @Test
    public void createPersonalCreditOperation() throws ParseException {
        CreditOperation expectedCreditOperation = new CreditOperation(Long.valueOf("1488"),
                stringToDate("2003-03-03"));
        expectedCreditOperation.setCreditOperationId(BigInteger.valueOf(getCurrentSequenceId()));
        CreditOperation actualCreditOperation = creditOperationDao.createPersonalCreditOperation(expectedCreditOperation,
                60);
        AssertUtils.assertCreditOperation(expectedCreditOperation, actualCreditOperation);
    }

    @Test
    public void getAllCreditOperationsByCreditFamilyId() throws ParseException {
        Collection<CreditOperation> actualCollection = creditOperationDao.
                getAllCreditOperationsByCreditFamilyId(84);
        CreditOperation creditOperation_1 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_1.setCreditOperationId(BigInteger.valueOf(117));
        CreditOperation creditOperation_2 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_2.setCreditOperationId(BigInteger.valueOf(115));
        CreditOperation creditOperation_3 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_3.setCreditOperationId(BigInteger.valueOf(113));
        CreditOperation creditOperation_4 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_4.setCreditOperationId(BigInteger.valueOf(111));
        CreditOperation creditOperation_5 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_5.setCreditOperationId(BigInteger.valueOf(109));
        CreditOperation creditOperation_6 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_6.setCreditOperationId(BigInteger.valueOf(107));
        CreditOperation creditOperation_7 = new CreditOperation(Long.valueOf("6666"), stringToDate("2000-01-01"));
        creditOperation_7.setCreditOperationId(BigInteger.valueOf(105));
        Collection<CreditOperation> expectedCollection = new ArrayList<>();
        expectedCollection.add(creditOperation_1);
        expectedCollection.add(creditOperation_2);
        expectedCollection.add(creditOperation_3);
        expectedCollection.add(creditOperation_4);
        expectedCollection.add(creditOperation_5);
        expectedCollection.add(creditOperation_6);
        expectedCollection.add(creditOperation_7);
        assertEquals(expectedCollection, actualCollection);
    }

    @Test
    public void getAllCreditOperationsByCreditPersonalId() throws ParseException {
        Collection<CreditOperation> actualCollection = creditOperationDao.
                getAllCreditOperationsByCreditPersonalId(10);
        CreditOperation creditOperationFirst = new CreditOperation(Long.valueOf("2000"), stringToDate("2019-11-28"));
        creditOperationFirst.setCreditOperationId(BigInteger.valueOf(97));
        CreditOperation creditOperationSecond = new CreditOperation(Long.valueOf("2000"), stringToDate("2019-11-28"));
        creditOperationSecond.setCreditOperationId(BigInteger.valueOf(8001));
        Collection<CreditOperation> expectedCollection = new ArrayList<>();
        expectedCollection.add(creditOperationFirst);
        expectedCollection.add(creditOperationSecond);
        assertEquals(expectedCollection, actualCollection);
    }


    private Date stringToDate(String stringToParse) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(stringToParse);
    }

    protected Integer getCurrentSequenceId() {
        String GET_CURRENT_SEQUENCE_ID = "select last_number from user_sequences where sequence_name = 'OBJECTS_ID_S'";
        int newId = jdbcTemplate.queryForObject(GET_CURRENT_SEQUENCE_ID, new CurrentSequenceMapper());
        return newId++;
    }
}
