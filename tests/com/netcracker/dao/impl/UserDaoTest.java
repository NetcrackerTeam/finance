package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
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
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class UserDaoTest {
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Test
    public void getUserById(){
        User user = userDao.getUserById(BigInteger.valueOf(1));
        assertEquals(BigInteger.valueOf(1),user.getId());
        assertEquals("User{} ", user.toString());
    }
    @Test
    public void getUserByLogin(){
        User user = userDao.getUserByLogin("Something");
        assertEquals(BigInteger.valueOf(1), user.getId());
        assertEquals("User{}", user.toString());
    }
    @Test
    public  void updateUserPasswordById(){
        userDao.updateUserPasswordById(BigInteger.valueOf(1),"123456789");
    }

    @Test
    public void getAllUsersByFamilyAccountId(){
        Collection<User> result = userDao.getAllUsersByFamilyAccountId(BigInteger.valueOf(1));
        assertEquals("{USer}", result.toString());
    }

}
