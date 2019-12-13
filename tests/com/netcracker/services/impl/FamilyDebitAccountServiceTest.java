package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.exception.OperationException;
import com.netcracker.exception.UserException;
import com.netcracker.models.FamilyDebitAccount;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class FamilyDebitAccountServiceTest {

    @Autowired
    private FamilyDebitServiceImpl familyDebitService;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate template;
    private static final Logger logger = Logger.getLogger(FamilyDebitAccountServiceTest.class);
    private static final BigInteger ID_USER = BigInteger.valueOf(1);
    private static final BigInteger ID_USER_ALONE = BigInteger.valueOf(24);
    private static final BigInteger ID = BigInteger.valueOf(3);
    private static final BigInteger ID_ALONE = BigInteger.valueOf(26);
    private static final BigInteger ERROR_ID = BigInteger.valueOf(999);
    private static final String ERROR_MESSAGE_FAMILY = "the family debit account doesn`t exist";
    private static final String ERROR_MESSAGE_USER = "The user is doesn`t exist";
    private static final String ERROR_MESSAGE_USER_STATUS = "The user is unactive";
    private static final String ERROR_TRANSACTION = "The transactions is doesn`t exist";
    private static final String ERROR_MESSAGE_FAMILY_PARTICIPANTS = "the family debit account has participants";
    private static final String ERROR_MESSAGE_USER_EXIST_FAMILY = "The user has family debit account";
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
            "SELECT * " +
            "FROM DUAL";


    @Test
    public void getFamilyAccount() {
        FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(ID);
        assertNotNull(familyDebitAccount);
    }

    @Test
    public void getFamilyAccountNull() {
        try {
            FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(null);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Test
    public void getFamilyAccountIncorrectId() {
        try {
            FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(ERROR_ID);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Rollback
    @Test
    public void deleteFamilyDebitAccountWithParticipants() {
        try {
            familyDebitService.deleteFamilyDebitAccount(ID);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Rollback
    @Test
    public void deleteFamilyDebitAccountAlone() {
        try {
            familyDebitService.deleteFamilyDebitAccount(ID_ALONE);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Rollback
    @Test
    public void deleteFamilyDebitAccountNull() {
        try {
            familyDebitService.deleteFamilyDebitAccount(null);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Rollback
    @Test
    public void addUserToAccountNullNull() {
        try {
            familyDebitService.addUserToAccount(null, null);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_USER);
        }
    }

    @Rollback
    @Test
    public void addUserToAccountNullNotNull() {
        try {
            familyDebitService.addUserToAccount(null, ID_USER);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Rollback
    @Test
    public void addUserToAccountIdSome() {
        try {
            familyDebitService.addUserToAccount(ID, ID_USER_ALONE);
        } catch (UserException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_USER_EXIST_FAMILY);
        }
    }

    @Rollback
    @Test
    public void addUserToAccountNotExistNotNull() {
        try {
            familyDebitService.addUserToAccount(ERROR_ID, ID_USER_ALONE);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Test
    public void getParticipantsNull() {
        try {
            familyDebitService.getParticipantsOfFamilyAccount(null);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Test
    public void getParticipantsNotExist() {
        try {
            familyDebitService.getParticipantsOfFamilyAccount(ERROR_ID);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Test
    public void getHistoryNull() {
        try {
            familyDebitService.getHistory(null, null);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Test
    public void getHistoryDateNull() {
        try {
            familyDebitService.getHistory(ID, null);
        } catch (OperationException ex) {
            assertEquals(ex.getMessage(), ERROR_TRANSACTION);
        }
    }

    @Rollback
    @Test
    public void deleteFamilyAccountNull() {
        try {
            familyDebitService.deleteFamilyDebitAccount(null);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY);
        }
    }

    @Rollback
    @Test
    public void deleteFamilyAccountID() {
        try {
            familyDebitService.deleteFamilyDebitAccount(ID);
        } catch (FamilyDebitAccountException ex) {
            assertEquals(ex.getMessage(), ERROR_MESSAGE_FAMILY_PARTICIPANTS);
        }
    }
}
