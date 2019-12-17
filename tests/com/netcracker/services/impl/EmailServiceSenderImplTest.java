package com.netcracker.services.impl;

import com.netcracker.AssertUtils;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.*;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.MessagingException;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class EmailServiceSenderImplTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EmailServiceSenderImpl emailServiceSender;
    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private AutoOperationDao autoOperationDao;

    private AbstractCreditAccount personalCredit;
    private AbstractCreditAccount familyCredit;
    private DataSource dataSource;
    private JdbcTemplate template;
    private AutoOperationIncome autoOperationIncomePersonalExpected;
    private AutoOperationExpense autoOperationExpensePersonalExpected;
    private AutoOperationIncome autoOperationIncomeFamilyExpected;
    private AutoOperationExpense autoOperationExpenseFamilyExpected;
    private LocalDate today;
    //id for income and expences method
    private BigInteger familyIncomeObjectIdAO = BigInteger.valueOf(96);
    private BigInteger familyExpenseObjectIdAO = BigInteger.valueOf(94);
    private BigInteger personalIncomeObjectIdAO = BigInteger.valueOf(95);
    private BigInteger personalExpenseObjectIdAO = BigInteger.valueOf(93);

    private BigInteger userId = BigInteger.valueOf(74);
    private BigInteger familyDebitId = BigInteger.valueOf(76);
    private BigInteger personalDebitId = BigInteger.valueOf(75);

    Path path;

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
    private static final String DELETE_ACC = " DELETE FROM OBJECTS WHERE NAME = 'famPerAccTest' ";

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
                .debitObjectName("famPerAccTest")
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
        template.update(DELETE_USER);
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
                .debitObjectName("famPerAccTest")
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
        template.update(DELETE_USER);
        template.update(DELETE_ACC);
    }

    @Test
    public void monthReport() throws MessagingException {
        User user = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("UserTest")
                .user_eMail("jenjamak@gmail.com")
                .user_password("passwordDima")
                .userActive(UserStatusActive.getStatusByKey(BigInteger.valueOf(39)))
                .build();

        User returnedUser = userDao.createUser(user);
        logger.debug("user is ready " + user.getId());

        emailServiceSender.monthReport(returnedUser.geteMail(), returnedUser.getName(),returnedUser.getId(), path);
        template.update(DELETE_USER);
        template.update(DELETE_ACC);
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
        template.update(DELETE_USER);

    }

    @Rollback
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

    @Test
    public void sendFamilyAutoIncome() {
        autoOperationIncomeFamilyExpected = new AutoOperationIncome.Builder()
                .accountId(familyIncomeObjectIdAO)
                .accountUserId(userId)
                .dayOfMonth(5)
                .accountAmount(12000L)
                .categoryIncome(CategoryIncome.AWARD)
                .accountDate(LocalDate.parse("2019-12-15"))
                .build();
        AutoOperationIncome incFamAutoOperation = autoOperationDao.createFamilyIncomeAutoOperation(autoOperationIncomeFamilyExpected, userId, familyDebitId);
        logger.debug("family income was create with id :" + incFamAutoOperation.getId());

        emailServiceSender.sendMailAutoFamilyIncome(
                userDao.getUserById(incFamAutoOperation.getUserId()).geteMail(),
                userDao.getUserById(incFamAutoOperation.getUserId()).getName(),
                incFamAutoOperation.getAmount(),
                userDao.getUserById(incFamAutoOperation.getUserId()).getName(),
                incFamAutoOperation.getUserId()
        );

    }

    @Test
    public void sendFamAutoExpense() {
        autoOperationExpenseFamilyExpected = new AutoOperationExpense.Builder()
                .accountId(familyExpenseObjectIdAO)
                .accountUserId(userId)
                .dayOfMonth(5)
                .accountAmount(16000L)
                .categoryExpense(CategoryExpense.FOOD)
                .accountDate(LocalDate.parse("2019-12-15"))
                .build();
        AutoOperationExpense expFamAutoOperation = autoOperationDao.createFamilyExpenseAutoOperation(autoOperationExpenseFamilyExpected, userId, familyDebitId);
        logger.debug("family expense was create with id :" + expFamAutoOperation.getId());

        emailServiceSender.sendMailAutoFamilyExpense(
                userDao.getUserById(expFamAutoOperation.getUserId()).geteMail(),
                userDao.getUserById(expFamAutoOperation.getUserId()).getName(),
                expFamAutoOperation.getAmount(),
                userDao.getUserById(expFamAutoOperation.getUserId()).getName(),
                expFamAutoOperation.getUserId()
        );
    }

    @Test
    public void sendPersAutoIncome() {
        autoOperationIncomePersonalExpected = new AutoOperationIncome.Builder()
                .accountId(personalIncomeObjectIdAO)
                .accountUserId(userId)
                .dayOfMonth(5)
                .accountAmount(13000L)
                .categoryIncome(CategoryIncome.AWARD)
                .accountDate(LocalDate.parse("2019-12-15"))
                .build();
        AutoOperationIncome incPersAccount = autoOperationDao.createPersonalIncomeAutoOperation(autoOperationIncomePersonalExpected, personalDebitId);
        logger.debug("personal income was create with id :" + incPersAccount.getId());

        emailServiceSender.sendMailAutoPersonalIncome(
                userDao.getUserById(incPersAccount.getUserId()).geteMail(),
                userDao.getUserById(incPersAccount.getUserId()).getName(),
                incPersAccount.getAmount(),
                userDao.getUserById(incPersAccount.getUserId()).getName(),
                incPersAccount.getUserId()
        );
    }

    @Test
    public void sendPersAutoExpense() {
        autoOperationExpensePersonalExpected = new AutoOperationExpense.Builder()
                .accountId(BigInteger.valueOf(93))
                .accountUserId(BigInteger.valueOf(74))
                .dayOfMonth(5)
                .accountAmount(17000L)
                .categoryExpense(CategoryExpense.FOOD)
                .accountDate(LocalDate.parse("2019-12-15"))
                .build();
        AutoOperationExpense expPersExpense = autoOperationDao.createPersonalExpenseAutoOperation(autoOperationExpensePersonalExpected, personalDebitId);
        logger.debug("personal expense was create with id :" + expPersExpense.getId());

        emailServiceSender.sendMailAutoFamilyExpense(
                userDao.getUserById(expPersExpense.getUserId()).geteMail(),
                userDao.getUserById(expPersExpense.getUserId()).getName(),
                expPersExpense.getAmount(),
                userDao.getUserById(expPersExpense.getUserId()).getName(),
                expPersExpense.getUserId()
        );
    }
}
