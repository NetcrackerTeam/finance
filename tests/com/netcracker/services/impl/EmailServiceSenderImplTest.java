package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.TemplatesDao;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.AssertUtils;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.PersonalAccountStatusActive;
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
    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;

    TemplatesDao templatesDao;
    private DataSource dataSource;
    private JdbcTemplate template;

    private static final Logger logger = Logger.getLogger(EmailServiceSenderImplTest.class);
    private static final String  CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, 1, ' ') "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(5, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(3, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(4, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(6, objects_id_s.currval, ?) "
            +
            "SELECT * " +
            "FROM DUAL";
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
                .userActive(UserStatusActive.getStatusByKey(BigInteger.valueOf(40)))
                .build();
        template.update(CREATE_USER, new Object[] {
                user.getName(),
                user.geteMail(),
                user.getPassword(),
                user.getUserStatusActive().getId()
        });

        User returnedUser = userDao.createUser(user);

        logger.debug("User created");
        emailServiceSender.sendMailBeforeDeactivate(returnedUser.geteMail(),returnedUser.getName(),returnedUser.getId());
        logger.debug("mail was send!");
        template.update(DELETE_USER);
    }

    @Test
    public void sendAboutPersonalAccount(){
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("Dimas")
                .user_eMail("summerxwow@gmail.com")
                .user_password("passwordDima")
                .build();
        template.update(CREATE_USER, new Object[] {
                user.getName(),
                user.geteMail(),
                user.getPassword(),
                user.getUserStatusActive()
        });
        User returnedUser = userDao.createUser(user);

        PersonalDebitAccount personalDebitAccount = new PersonalDebitAccount.Builder()
                .debitId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .debitObjectName("PER_DEB_ACC1")
                .debitAmount(Long.valueOf(1234L))
                .debitPersonalAccountStatus(PersonalAccountStatusActive.YES)
                .debitOwner(user)
                .build();

        PersonalDebitAccount perDA = personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
        logger.debug("Personal account is created" + perDA.getId());

        emailServiceSender.sendMailAboutPersonalDebt(returnedUser.geteMail(),returnedUser.getName(),perDA.getObjectName(),perDA.getAmount(), returnedUser.getId());

    }
}
