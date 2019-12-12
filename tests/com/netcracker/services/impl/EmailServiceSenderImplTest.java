package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.TemplatesDao;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.AssertUtils;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import org.apache.log4j.Logger;
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
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class EmailServiceSenderImplTest {

    @Autowired
    UserDao userDao;
    @Autowired
    private EmailServiceSenderImpl emailServiceSender;

    TemplatesDao templatesDao;
    private DataSource dataSource;
    private JdbcTemplate template;

    private static final Logger logger = Logger.getLogger(EmailServiceSenderImplTest.class);
    private static final String DELETE_USER = " DELETE FROM OBJECTS WHERE NAME = 'VanyaEmail' ";

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        template = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Test
    public void delteUser(){
        template.update(DELETE_USER);
    }

    @Test
    public void sendBeforeDeactivate() {
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("VanyaEmail")
                .user_eMail("summerxwow@gmail.com")
                .user_password("1234")
                .userActive(UserStatusActive.NO)
                .build();
        template.update(DELETE_USER);
        User returnedUser = userDao.createUser(user);

        logger.debug("User created");
        emailServiceSender.sendMailBeforeDeactivate(returnedUser.geteMail(),returnedUser.getName(),returnedUser.getId());
        logger.debug("mail was send!");
        template.update(DELETE_USER);
    }
}
