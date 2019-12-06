package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.services.validation.UserValidation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class FamilyAccountDebitDaoTests {

    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private UserValidation userValidation;
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate template;
    private static final String  SQL_ACTIVE = "update attributes set list_value_id = 41 where attr_id = 69 and object_id = ?";
    private static final String  CREATE_USER = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, 1, 'user_new') "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(5, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(3, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(4, objects_id_s.currval, ?) "
            +
            "SELECT * FROM DUAL";
    private static final String DELETE_USER = "DELETE FROM OBJECTS WHERE NAME = 'user_new' ";

    private static final String DELETE_ACC = "DELETE FROM OBJECTS WHERE NAME = 'Name1' ";
    private String email = "mail@gmail.com";
    private String password = "password";
    private BigInteger id = BigInteger.valueOf(3);

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        template = new JdbcTemplate(dataSource);
    }


    @Test
    public void getFamilyAccountById() {

        FamilyDebitAccount familyDebitAccount = familyAccountDebitDao.getFamilyAccountById(id);
        String name = "FAM_DEB_ACC1";
        Long amount = 9000L;
        assertEquals(id,familyDebitAccount.getId());
        assertEquals(name, familyDebitAccount.getObjectName());
        assertEquals(amount, familyDebitAccount.getAmount());

        String username = "Eugen";
        String is_active = "YES";
        assertEquals(BigInteger.valueOf(1), familyDebitAccount.getOwner().getId());
        assertEquals(username, familyDebitAccount.getOwner().getName());
        assertEquals(email, familyDebitAccount.getOwner().geteMail());
        assertEquals(password, familyDebitAccount.getOwner().getPassword());
        assertEquals(is_active, familyDebitAccount.getOwner().getUserStatusActive().toString());
    }
    @Test
    public void createFamilyAccount(){
        User owner = new User.Builder()
                .user_id(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
                .user_name("Eugen9")
                .user_eMail(email)
                .user_password(password).build();
     //   System.out.println(owner.getId());
    //    userDao.createUser(owner);
       template.update(CREATE_USER, new Object[]{owner.getName(), owner.geteMail(), owner.getPassword()});
        FamilyDebitAccount familyDebitAccount = new FamilyDebitAccount.Builder()
                .debitId(BigInteger.valueOf(AssertUtils.getCurrentSequenceId(template)))
               .debitObjectName("Name1")
               .debitAmount(6000L)
               .debitFamilyAccountStatus(FamilyAccountStatusActive.YES)
               .debitOwner(owner).build();

        FamilyDebitAccount familyDebitAccount2 = familyAccountDebitDao.createFamilyAccount(familyDebitAccount);
//
//
  //      System.out.println(familyDebitAccount2.getId());
       assertEquals("Name1",familyDebitAccount2.getObjectName());
       template.update(DELETE_USER);
       template.update(DELETE_ACC);
    }
    @Test
    public void deleteUserFromFamilyAccount(){
        familyAccountDebitDao.deleteUserFromAccountById(BigInteger.valueOf(3),BigInteger.valueOf(47));
    }
    @Test
    public void getListParticipants(){
      List<User> users =  familyAccountDebitDao.getParticipantsOfFamilyAccount(id);
        for (User expected : users) {
            System.out.println(expected.getId() + " " + expected.getName() + " " + expected.geteMail() + " " + expected.getPassword() + " "
                    + expected.getUserStatusActive().toString() + " " + expected.getPersonalDebitAccount() );
        }
        assertEquals(BigInteger.valueOf(1), users.get(0).getId());
        assertEquals(BigInteger.valueOf(49), users.get(1).getId());
    }
    @Test
    public  void getListIncome(){
        List<AccountIncome> incomes = familyAccountDebitDao.getIncomesOfFamilyAccount(BigInteger.valueOf(3));
        for (AccountIncome expected : incomes) {
            System.out.println(expected.getId() + " " + expected.getCategoryIncome() + " " + expected.getAmount() + " " + expected.getDate());
        }
    }
    @Test
    public  void getListExpense(){
        List<AccountExpense> expenses = familyAccountDebitDao.getExpensesOfFamilyAccount(BigInteger.valueOf(3));
        for (AccountExpense expected : expenses) {
            System.out.println(expected.getId() + " " + expected.getCategoryExpense() + " " + expected.getAmount() + " " + expected.getDate());
        }
    }
    @Test
    public void deleteFamilyAcc(){
        familyAccountDebitDao.deleteFamilyAccount(BigInteger.valueOf(76));
        assertEquals("NO",familyAccountDebitDao.getFamilyAccountById(BigInteger.valueOf(76)).getStatus().toString());
        template.update(SQL_ACTIVE, new BigDecimal(76));
       // System.out.println(familyAccountDebitDao.getFamilyAccountById(BigInteger.valueOf(76)).getStatus());
    }
    @Test
    public void addUserToFamilyAcc(){
        familyAccountDebitDao.addUserToAccountById(BigInteger.valueOf(3), BigInteger.valueOf(47));
    }

    @Test
    public void updateAmount(){
        familyAccountDebitDao.updateAmountOfFamilyAccount(id,  20000L);
        Long expected = 20000L;
        assertEquals(expected, familyAccountDebitDao.getFamilyAccountById(id).getAmount());
        familyAccountDebitDao.updateAmountOfFamilyAccount(id, 9000L);
    }

    @Test
    public void ValidationName() {
        System.out.println(userValidation.saveNameByPreparedStatement("54"));
    }
}