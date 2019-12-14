package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.exception.PersonalDebitAccountException;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.PersonalDebitAccount;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class PersonalDebitAccountServiceTest {

    @Autowired
    private PersonalDebitServiceImpl personalDebitService;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate template;
    private static final Logger logger = Logger.getLogger(PersonalDebitAccountServiceTest.class);
    private static final BigInteger ID = BigInteger.valueOf(25);
    private static final BigInteger ERROR_ID = BigInteger.valueOf(5051);

    private static final String ERROR_MESSAGE_PERSONAL = "the personal debit account doesn`t exist";
    private final static String NULL_MESSAGE = "Null object was found";

    @Test
    public void getPersonalAcc() {
        PersonalDebitAccount personalDebitAccount = personalDebitService.getPersonalDebitAccount(ID);
        assertNotNull(personalDebitAccount);
    }

    @Test
    public void getPesonalAccountWithNull() {
        try {
            PersonalDebitAccount personalDebitAccount = personalDebitService.getPersonalDebitAccount(null);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), NULL_MESSAGE);
        }
    }

    @Test
    public void getPersonalAccountIncorrectId() {
        try {
            PersonalDebitAccount personalDebitAccount = personalDebitService.getPersonalDebitAccount(ERROR_ID);
        } catch (PersonalDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_PERSONAL);
        }
    }


    @Test
    public void deletePersonalDebitAccountWithNull() {
        try {
            personalDebitService.deletePersonalDebitAccount(null, null);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), NULL_MESSAGE);
        }
    }

    @Test
    public void getHistoryWithNull() {
        try {
            personalDebitService.getHistory(null, null);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), NULL_MESSAGE);
        }
    }

    @Test
    public void getHistoryWithDateNull() {
        try {
            personalDebitService.getHistory(ID, null);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), NULL_MESSAGE);
        }
    }

    @Test
    public void getHistory() {
        Collection<AbstractAccountOperation> operations = personalDebitService.getHistory(ID, LocalDate.of(2019, 01, 11));
        assertNotNull(operations);
    }
}