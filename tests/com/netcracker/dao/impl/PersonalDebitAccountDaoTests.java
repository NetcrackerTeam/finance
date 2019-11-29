package com.netcracker.dao.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.enums.PersonalAccountStatusActive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Locale;

@TestExecutionListeners( {DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Config.xml"})
public class PersonalDebitAccountDaoTests {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate template;
    private PersonalDebitAccount personalDebitAccount;


    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        template = new JdbcTemplate(dataSource);
    }

    @Test
    public void getPersonalAccountById() {
        PersonalDebitAccount personalDebitAccount = personalDebitAccountDao.getPersonalAccountById(BigInteger.valueOf(1));
    }
    @Test
    public void createPersonalAccount(){
        personalDebitAccount = new PersonalDebitAccount.Builder()
                .debitId(BigInteger.valueOf(101))
                .debitObjectName("test")
                .debitAmount(Long.valueOf(1234))
                .debitPersonalAccountStatus(PersonalAccountStatusActive.getStatusByKey(BigInteger.valueOf(43)))
                .build();
        personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }
}