package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.PersonalAccountStatusActive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class PersonalDebitAccountDaoTests {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate template;
    private PersonalDebitAccount personalDebitAccount;
    private static final String  CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (127, 1, 'user_Tests') "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(5, 127, 'UserTests') "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(3, 127, 'mail@gmail.com') "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(4, 127, 'password') "
            +
            "SELECT * " +
                    "FROM Dual";

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        template = new JdbcTemplate(dataSource);
    }

    @Test
    public void getPersonalAccountById() {
        BigInteger id = BigInteger.valueOf(25);
        PersonalDebitAccount personalDebitAccount =
                personalDebitAccountDao.getPersonalAccountById(id);
        BigInteger ex_id = id;
        String name = "PER_DEB_ACC2";
        Long amount = 10000L;
        assertEquals(ex_id,personalDebitAccount.getId());
        assertEquals(name, personalDebitAccount.getObjectName());
        assertEquals(amount, personalDebitAccount.getAmount());

        assertEquals(BigInteger.valueOf(24), personalDebitAccount.getOwner().getId());
        assertEquals("Dimas", personalDebitAccount.getOwner().getName());
        assertEquals("mailDimas@gmail.com", personalDebitAccount.getOwner().geteMail());
        assertEquals("passwordDima", personalDebitAccount.getOwner().getPassword());
    }
    @Test
    public void createPersonalAccount(){
        User owner = new User.Builder()
                .user_id(BigInteger.valueOf(125))
                .user_name("Dimas")
                .user_eMail("mailDimas@gmail.com")
                .user_password("passwordDima")
                .build();
        template.update(CREATE_USER);

        PersonalDebitAccount personalDebitAccount = new PersonalDebitAccount.Builder()
                .debitObjectName("PER_DEB_ACC1")
                .debitAmount(Long.valueOf(1234L))
                .debitPersonalAccountStatus(PersonalAccountStatusActive.YES)
                .debitOwner(owner)
                .build();

        PersonalDebitAccount personalDebitAccount1 = personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
        assertEquals("PER_DEB_ACC1",personalDebitAccount1.getObjectName());
    }

    @Test
    public void  deletePersonalAccountById (){
        personalDebitAccountDao.deletePersonalAccountById(BigInteger.valueOf(121), BigInteger.valueOf(43));
    }

    @Test
    public void deletePersonalAccountByUserId (){
        personalDebitAccountDao.deletePersonalAccountByUserId(BigInteger.valueOf(121));
    }
}