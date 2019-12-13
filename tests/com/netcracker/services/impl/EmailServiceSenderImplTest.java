package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.AssertUtils;
import com.netcracker.models.*;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.models.enums.FamilyAccountStatusActive;
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

import javax.mail.MessagingException;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.time.LocalDate;
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
    @Autowired
    FamilyAccountDebitDao familyAccountDebitDao;

    private AbstractCreditAccount personalCredit;
    private AbstractCreditAccount familyCredit;
    private DataSource dataSource;
    private JdbcTemplate template;

    private static final Logger logger = Logger.getLogger(EmailServiceSenderImplTest.class);
    private static final String CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, 1, ' ') "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(5, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(3, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(4, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(6, objects_id_s.currval, ?) "
            +
            "INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,OBJECTS_ID_S.CURRVAL, ? /* REFERENCE USER(OWNER) TO FAMILY ACCOUNT */ ) "
            +
            "INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,OBJECTS_ID_S.CURRVAL, ? /* REFERENCE USER(OWNER) TO FAMILY ACCOUNT */ ) "
            +
            "SELECT * " +
            "FROM DUAL";
    private static final String DELETE_USER = " DELETE FROM OBJECTS WHERE NAME = 'UserTest' ";

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
    public void delteUser() {
        template.update(DELETE_USER);
    }

    @Test
    public void sendBeforeDeactivate() {
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("summerxwow@gmail.com")
                .user_password("1234")
                .userActive(UserStatusActive.getStatusByKey(BigInteger.valueOf(40)))
                .personalDebit(BigInteger.valueOf(2))
                .familyDebit(BigInteger.valueOf(3))
                .build();

        this.template.update(CREATE_USER,
                user.getName(),
                user.geteMail(),
                user.getPassword(),
                user.getUserStatusActive().getId(),
                user.getPersonalDebitAccount(),
                user.getFamilyDebitAccount()
        );

        logger.debug("User created");
        emailServiceSender.sendMailBeforeDeactivate(
                user.geteMail(),
                user.getName(),
                user.getId()
        );
        logger.debug("mail was send!");
        template.update(DELETE_USER);
    }

    @Test
    public void sendAboutPersonalAccount() {
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("summerxwow@gmail.com")
                .user_password("1234")
                .userActive(UserStatusActive.YES)
                .build();

        PersonalDebitAccount personalDebitAccount = new PersonalDebitAccount.Builder()
                .debitId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .debitObjectName("PER_DEB_ACC1")
                .debitAmount(Long.valueOf(1234L))
                .debitPersonalAccountStatus(PersonalAccountStatusActive.YES)
                .debitOwner(user)
                .build();

        PersonalDebitAccount perDA = personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
        logger.debug("Personal account is created" + perDA.getId());

        emailServiceSender.sendMailAboutPersonalDebt(
                user.geteMail(),
                user.getName(),
                perDA.getObjectName(),
                perDA.getAmount(),
                user.getId()
        );

    }

    @Test
    public void sendAboutFamilyAccount() {
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("summerxwow@gmail.com")
                .user_password("1234")
                .userActive(UserStatusActive.YES)
                .build();

        FamilyDebitAccount familyDebitAccount = new FamilyDebitAccount.Builder()
                .debitId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .debitObjectName("Name1")
                .debitAmount(Long.valueOf(6000L))
                .debitFamilyAccountStatus(FamilyAccountStatusActive.YES)
                .debitOwner(user)
                .build();

        FamilyDebitAccount famDA = familyAccountDebitDao.createFamilyAccount(familyDebitAccount);

        emailServiceSender.sendMailAboutFamilyDebt(
                user.geteMail(),
                user.getName(),
                famDA.getObjectName(),
                famDA.getAmount(),
                user.getId()
        );

    }

    @Test
    public void sendTxt() throws MessagingException {
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("jenjamak@gmail.com")
                .user_password("passwordDima")
                .userActive(UserStatusActive.getStatusByKey(BigInteger.valueOf(39)))
                .build();

        User returnedUser = userDao.createUser(user);
        logger.debug("user is ready " + user.getId());

        emailServiceSender.sendText(returnedUser.geteMail(), returnedUser.getId());
    }

    @Test
    public void sendPersCredit() {
        Debt perDebtOne = new Debt.Builder()
                .debtId(new BigInteger("14"))
                .amountDebt(0L)
                .dateFrom(LocalDate.of(2019, 11, 30))
                .dateTo(LocalDate.of(2020, 2, 28))
                .build();

        personalCredit =
                new PersonalCreditAccount.Builder()
                        .creditId(new BigInteger("10"))
                        .name("Credit_Money1")
                        .amount(10000L)
                        .paidAmount(2000L)
                        .date(LocalDate.of(2019, 11, 30))
                        .creditRate(20L)
                        .dateTo(LocalDate.of(2020, 2, 28))
                        .monthDay(1)
                        .isPaid(CreditStatusPaid.getStatusByKey(new BigInteger("38")))
                        .debtCredit(perDebtOne)
                        .build();

        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("summerxwow@gmail.com")
                .user_password("passwordDima")
                .userActive(UserStatusActive.getStatusByKey(BigInteger.valueOf(39)))
                .build();

        User returnedUser = userDao.createUser(user);
        logger.debug("user is ready " + user.getId());

        logger.debug("was created " + personalCredit.getName());

        emailServiceSender.sendMailReminderPersonalCredit(
                returnedUser.geteMail(),
                returnedUser.getName(),
                personalCredit.getPaidAmount(),
                personalCredit.getName(),
                returnedUser.getId(),
                personalCredit.getDateTo()
        );
    }

    @Test
    public void sendFamCredit() {
        Debt famDebtOne = new Debt.Builder()
                .amountDebt(0L)
                .dateFrom(LocalDate.of(2019, 11, 30))
                .dateTo(LocalDate.of(2020, 2, 28))
                .debtId(new BigInteger("15"))
                .build();

        familyCredit =
                new FamilyCreditAccount.Builder()
                        .creditId(new BigInteger("11"))
                        .name("Credit_Money1")
                        .amount(15000L)
                        .paidAmount(3000L)
                        .creditRate(20L)
                        .date(LocalDate.of(2019, 11, 30))
                        .dateTo(LocalDate.of(2020, 2, 28))
                        .monthDay(1)
                        .isPaid(CreditStatusPaid.getStatusByKey(new BigInteger("38")))
                        .debtCredit(famDebtOne)
                        .build();

        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("summerxwow@gmail.com")
                .user_password("passwordDima")
                .userActive(UserStatusActive.getStatusByKey(BigInteger.valueOf(39)))
                .build();

        User returnedUser = userDao.createUser(user);
        logger.debug("user is ready " + user.getId());

        logger.debug("was created " + familyCredit.getName());

        emailServiceSender.sendMailReminderFamilyCredit(
                returnedUser.geteMail(),
                returnedUser.getName(),
                familyCredit.getPaidAmount(),
                familyCredit.getName(),
                returnedUser.getId(),
                familyCredit.getDateTo()
        );
    }

}
