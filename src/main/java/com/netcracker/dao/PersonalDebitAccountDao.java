package com.netcracker.dao;

import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;
import java.util.Collection;

public interface PersonalDebitAccountDao {

    /**
     * Get personal account debit by personal account debit id.
     *
     * @param id personal account debit id
     * @return PersonalDebitAccount object
     */
    PersonalDebitAccount getPersonalAccountById(BigInteger id);

    /**
     * Create new personal account debit.
     *
     * @param personalDebitAccount personal account debit object
     */
    PersonalDebitAccount createPersonalAccount(PersonalDebitAccount personalDebitAccount);

    /**
     * Delete personal account debit.
     *
     * @param userId    user id
     * @param accountId personal account debit id
     */
    void deletePersonalAccountById(BigInteger accountId, BigInteger userId);

    /**
     * Set unActive personal account debit by personal account debit id.
     *
     * @param accountId personal account debit id
     */
    void deletePersonalAccountByUserId(BigInteger accountId, BigInteger userId);

    /**
     * Update amount of personal account debit by personal account debit id
     *
     * @param accountId perosnal account debit id
     * @param amount    amount of persoanl account debit
     */
    void updateAmountOfPersonalAccount(BigInteger accountId, double amount);

    /**
     * Get all personal accounts debit.
     *
     * @return PersonalDebitAccount Collection
     */
    Collection<PersonalDebitAccount> getAllPersonalAccounts();

    String GET_PERSONAL_ACCOUNT_BY_ID = "SELECT\n" +
            "    DEBIT.NAME NAME_PERSONAL_DEBIT, AMOUNT.VALUE AMOUNT_PERSONAL_DEBIT, STATUS_DEBIT.LIST_VALUE_ID STATUS_PERSONAL_DEBIT,\n" +
            "    USER_TO_PERSONAL.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD,\n" +
            "    STATUS_USER.LIST_VALUE_ID IS_ACTIVE, DEBIT.OBJECT_ID PER_DEB_ACC1,\n" +
            "    (SELECT FAM_ACC.OBJECT_ID FROM OBJREFERENCE FAM_ACC WHERE USER_TO_PERSONAL.OBJECT_ID  = FAM_ACC.REFERENCE AND FAM_ACC.ATTR_ID = 8) as FAM_DEB_ACC1,\n" +
            "    USER_ROLE.LIST_VALUE_ID AS ROLE\n" +
            "FROM OBJECTS DEBIT, ATTRIBUTES AMOUNT, ATTRIBUTES STATUS_DEBIT,\n" +
            "     ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER,\n" +
            "     ATTRIBUTES STATUS_USER, OBJREFERENCE USER_TO_PERSONAL, ATTRIBUTES USER_ROLE\n" +
            "WHERE DEBIT.OBJECT_ID = ?\n" +
            "  AND USER_TO_PERSONAL.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */\n" +
            "  AND USER_TO_PERSONAL.REFERENCE = DEBIT.OBJECT_ID\n" +
            "  AND AMOUNT.OBJECT_ID = DEBIT.OBJECT_ID\n" +
            "  AND AMOUNT.ATTR_ID = 7 /* ATTRIBUTE ID AMOUNT OF PERSONAL ACCOUNT */\n" +
            "  AND STATUS_DEBIT.OBJECT_ID = DEBIT.OBJECT_ID\n" +
            "  AND STATUS_DEBIT.ATTR_ID = 70 /* ATTRIBUTE ID STATUS OF PERSONAL ACCOUNT */\n" +
            "  AND NAME_USER.OBJECT_ID = USER_TO_PERSONAL.OBJECT_ID\n" +
            "  AND NAME_USER.ATTR_ID = 5 /* ATTRIBUTE ID NAME OF USER */\n" +
            "  AND EMAIL_USER.OBJECT_ID = USER_TO_PERSONAL.OBJECT_ID\n" +
            "  AND EMAIL_USER.ATTR_ID = 3 /* ATTRIBUTE ID EMAIL OF USER */\n" +
            "  AND PASSWORD_USER.OBJECT_ID = USER_TO_PERSONAL.OBJECT_ID\n" +
            "  AND PASSWORD_USER.ATTR_ID = 4 /* ATTRIBUTE ID PASSWORD OF USER */\n" +
            "  AND STATUS_USER.OBJECT_ID = USER_TO_PERSONAL.OBJECT_ID\n" +
            "  AND STATUS_USER.ATTR_ID = 6 /* ATTRIBUTE ID STATUS OF USER */\n" +
            "  AND USER_ROLE.OBJECT_ID = USER_TO_PERSONAL.OBJECT_ID\n" +
            "  AND USER_ROLE.ATTR_ID = 71";

    String CREATE_PERSONAL_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS (OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL, 2, ? ) /* NAME PERSONAL DEBIT ACCOUNT */" +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE) VALUES(7, OBJECTS_ID_S.CURRVAL, ?) /* AMOUNT*/" +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(70, OBJECTS_ID_S.CURRVAL, ?) /* STATUS */" +
            "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,?,OBJECTS_ID_S.CURRVAL) /* REFERENCE USER TO PERSONAL ACCOUNT */" +
            "SELECT * " +
            "FROM DUAL";

    String GET_PERSONAL_ACCOUNTS = "SELECT\n" +
            "    DEBIT.NAME NAME_PERSONAL_DEBIT, AMOUNT.VALUE AMOUNT_PERSONAL_DEBIT, STATUS_DEBIT.LIST_VALUE_ID STATUS_PERSONAL_DEBIT,\n" +
            "    USER_TO_DEBIT.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD,\n" +
            "    STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL.REFERENCE PER_DEB_ACC1,\n" +
            "    (SELECT FAM_ACC.OBJECT_ID FROM OBJREFERENCE FAM_ACC WHERE USER_TO_PERSONAL.OBJECT_ID  = FAM_ACC.REFERENCE AND FAM_ACC.ATTR_ID = 8) as FAM_DEB_ACC1,\n" +
            "    USER_ROLE.LIST_VALUE_ID AS ROLE\n" +
            "FROM OBJECTS DEBIT, ATTRIBUTES AMOUNT, ATTRIBUTES STATUS_DEBIT,\n" +
            "     ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER,\n" +
            "     ATTRIBUTES STATUS_USER, OBJREFERENCE USER_TO_DEBIT, OBJREFERENCE USER_TO_PERSONAL, ATTRIBUTES USER_ROLE\n" +
            "WHERE DEBIT.OBJECT_TYPE_ID = 2\n" +
            "  AND USER_TO_DEBIT.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */\n" +
            "  AND USER_TO_DEBIT.REFERENCE = DEBIT.OBJECT_ID\n" +
            "  AND AMOUNT.OBJECT_ID = DEBIT.OBJECT_ID\n" +
            "  AND AMOUNT.ATTR_ID = 7 /* ATTRIBUTE ID AMOUNT OF PERSONAL ACCOUNT */\n" +
            "  AND STATUS_DEBIT.OBJECT_ID = DEBIT.OBJECT_ID\n" +
            "  AND STATUS_DEBIT.ATTR_ID = 70 /* ATTRIBUTE ID STATUS OF PERSONAL ACCOUNT */\n" +
            "  AND NAME_USER.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID\n" +
            "  AND NAME_USER.ATTR_ID = 5 /* ATTRIBUTE ID NAME OF USER */\n" +
            "  AND EMAIL_USER.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID\n" +
            "  AND EMAIL_USER.ATTR_ID = 3 /* ATTRIBUTE ID EMAIL OF USER */\n" +
            "  AND PASSWORD_USER.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID\n" +
            "  AND PASSWORD_USER.ATTR_ID = 4 /* ATTRIBUTE ID PASSWORD OF USER */\n" +
            "  AND STATUS_USER.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID\n" +
            "  AND STATUS_USER.ATTR_ID = 6 /* ATTRIBUTE ID STATUS OF USER */\n" +
            "  AND USER_TO_PERSONAL.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */\n" +
            "  AND USER_TO_PERSONAL.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID\n" +
            "  AND USER_TO_DEBIT.OBJECT_ID = USER_ROLE.OBJECT_ID AND USER_ROLE.ATTR_ID = 71";

    String PERSONAL_ACCOUNT_IS_UNACTIVE = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = 42 WHERE ATTR_ID = 69 AND OBJECT_ID = ?";
    String DELETE_PERSONAL_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 1 AND OBJECT_ID = ? AND REFERENCE = ?";
    String UNACTIVE_USER_FROM_PERSONAL_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 1 AND OBJECT_ID = ? AND REFERENCE = ?";
    String UPDATE_PERSONAL_ACCOUNT_AMOUNT = "UPDATE ATTRIBUTES SET VALUE = ? WHERE ATTR_ID = 7 AND OBJECT_ID = ?";

}