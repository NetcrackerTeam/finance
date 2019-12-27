package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
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
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
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

    @Rollback
    @Test
    public void insertUser(){
        User user = new User.Builder()
                .user_name("Eugene")
                .user_eMail("some@gmail")
                .user_password("1234")
                .userActive(UserStatusActive.YES)
                .build();
        System.out.println(userDao.createUser(user));
        jdbcTemplate.update(DELETE_USER);
    }
    private static final String DELETE_USER = " DELETE FROM OBJECTS WHERE NAME = 'Eugene' ";

    @Test
    public void getUserById(){
        User user = userDao.getUserById(BigInteger.valueOf(24));
        assertEquals("user_id=24 ListStatus=YES name=Dimas email=mailDimas@gmail.com password=passwordDima personal_Acc=25 familyAcc=26",
                "user_id=" + user.getId() + " ListStatus=" + user.getUserStatusActive() +
                        " name=" + user.getName() + " email=" + user.geteMail() +
                        " password=" + user.getPassword() + " personal_Acc=" + user.getPersonalDebitAccount() + " familyAcc=" + user.getFamilyDebitAccount());
    }

    @Test
    public void getUserByLogin(){
        User user = userDao.getUserByEmail("name123kdmwel;d");
        assertEquals("user_id=24 ListStatus=YES name=Dimas email=mailDimas@gmail.com password=passwordDima personal_Acc=25 familyAcc=26",
                "user_id=" + user.getId() + " ListStatus=" + user.getUserStatusActive() +
                        " name=" + user.getName() + " email=" + user.geteMail() +
                        " password=" + user.getPassword() + " personal_Acc=" + user.getPersonalDebitAccount() + " familyAcc=" + user.getFamilyDebitAccount());

    }
    @Rollback
    @Test
    public  void updateUserPasswordById(){
        userDao.updateUserPasswordById(BigInteger.ONE,"123456789");
    }


    @Rollback
    @Test
    public  void updateUserStatus(){
        userDao.updateUserStatus(BigInteger.ONE,UserStatusActive.NO.getId());
    }


    @Rollback
    @Test
    public  void  updateEmail(){
        userDao.updateEmail(BigInteger.ONE,"someemail@gmail.com");
    }

}
