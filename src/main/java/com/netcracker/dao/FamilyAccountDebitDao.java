package com.netcracker.dao;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;

import java.math.BigInteger;
import java.util.Collection;

public interface FamilyAccountDebitDao {

    /**
     * Get family account debit by family account debit id.
     *
     * @param id family account debit id
     * @return FamilyDebitAccount object
     */
    FamilyDebitAccount getFamilyAccountById(BigInteger id);

    /**
     * Create new family account debit to existing user
     *
     * @param familyDebitAccount family account debit object
     */
    FamilyDebitAccount createFamilyAccount(FamilyDebitAccount familyDebitAccount);

    /**
     * Set unActive family account debit by family account debit id.
     *
     * @param accountId family account debit id
     * @param userId    family account debit id
     */
    void deleteFamilyAccount(BigInteger accountId, BigInteger userId);

    /**
     * Add user to family account debit by family account debit id and user id.
     *
     * @param accountId family account debit id
     * @param userId    user id
     */
    void addUserToAccountById(BigInteger accountId, BigInteger userId);

    /**
     * Delete user to family account debit by family account debit id and user id.
     *
     * @param accountId family account debit id
     * @param userId    user id
     */
    void deleteUserFromAccountById(BigInteger accountId, BigInteger userId);

    /**
     * Update amount of family account debit by family account debit id
     *
     * @param accountId family account debit id
     * @param amount    amount of family account debit
     */
    void updateAmountOfFamilyAccount(BigInteger accountId, double amount);

    /**
     * Get participants of family account debit by family account debit id.
     *
     * @param accountId family account debit id
     * @return User Collection
     */
    Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId);

    /**
     * Get participants all of family accounts debit.
     *
     * @return User Collection
     */
    Collection<User> getAllParticipantsOfFamilyAccounts();

    /**
     * Get all family accounts debit.
     *
     * @return FamilyDebitAccount Collection
     */
    Collection<FamilyDebitAccount> getAllFamilyAccounts();


    String ADD_USER_BY_ID = "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,?,?)";

    String FIND_FAMILY_ACCOUNT_BY_ID = "SELECT " +
            "DEBIT.OBJECT_ID DEBIT_ID, DEBIT.NAME NAME_DEBIT, AMOUNT.VALUE AMOUNT_DEBIT, STATUS_DEBIT.LIST_VALUE_ID STATUS_DEBIT, "
            +
            "USER_ID.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD, "
            +
            "STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1, USER_ROLE.LIST_VALUE_ID AS ROLE "
            +
            "FROM OBJECTS DEBIT, ATTRIBUTES AMOUNT, ATTRIBUTES STATUS_DEBIT, "
            +
            "OBJECTS USER_ID, ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER, "
            +
            "ATTRIBUTES STATUS_USER, OBJREFERENCE USER_TO_DEBIT, OBJREFERENCE USER_TO_PERSONAL,  ATTRIBUTES USER_ROLE  "
            +
            "WHERE DEBIT.OBJECT_ID = ? " +
            "AND AMOUNT.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND AMOUNT.ATTR_ID = 9 /* ATTRIBUTE ID AMOUNT OF FAMILY ACCOUNT */" +
            "AND STATUS_DEBIT.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND STATUS_DEBIT.ATTR_ID = 69 /* ATTRIBUTE ID STATUS OF FAMILY ACCOUNT */" +
            "AND USER_TO_DEBIT.ATTR_ID = 2 /*  REFERENCE USER TO FAMILY ACCOUNT  */" +
            "AND USER_TO_DEBIT.REFERENCE = DEBIT.OBJECT_ID " +
            "AND USER_ID.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID " +
            "AND NAME_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND NAME_USER.ATTR_ID = 5 /* ATTRIBUTE ID NAME OF USER */" +
            "AND EMAIL_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND EMAIL_USER.ATTR_ID = 3 /* ATTRIBUTE ID EMAIL OF USER */" +
            "AND PASSWORD_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND PASSWORD_USER.ATTR_ID = 4 /* ATTRIBUTE ID PASSWORD OF USER */" +
            "AND STATUS_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND STATUS_USER.ATTR_ID = 6 /* ATTRIBUTE ID STATUS OF USER */" +
            "AND USER_TO_PERSONAL.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */" +
            "AND USER_TO_PERSONAL.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.ATTR_ID = 71";

    String ADD_NEW_FAMILY_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL, 13, ? /* NAME FAMILY DEBIT ACCOUNT */ ) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(9, OBJECTS_ID_S.CURRVAL, ? /* AMOUNT */) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(69, OBJECTS_ID_S.CURRVAL, ? /* STATUS */) "
            +
            "INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,?,OBJECTS_ID_S.CURRVAL /* REFERENCE USER(OWNER) TO FAMILY ACCOUNT */ ) "
            +
            "INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,OBJECTS_ID_S.CURRVAL, ? /* REFERENCE FAMILY ACCOUNT TO USER(PARTICIPANT) */) "
            +
            "SELECT * FROM DUAL";
    String DELETE_USER_FROM_FAMILY_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 8 AND OBJECT_ID = ? AND REFERENCE = ?";

    String SET_FAMILY_ACCOUNT_UNACTIVE = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = 42 WHERE ATTR_ID = 69 AND OBJECT_ID = ?";

    String DELETE_REFERENCE_FROM_USER_TO_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 2 AND OBJECT_ID = ? AND REFERENCE = ?";

    String UPDATE_FAMILY_ACCOUNT_AMOUNT = "UPDATE ATTRIBUTES SET VALUE = ? WHERE ATTR_ID = 9 AND OBJECT_ID = ?";

    String GET_PARTICIPANTS = "SELECT "
            +
            "USER_ID.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD, "
            +
            "STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL_DEBIT.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1, USER_ROLE.LIST_VALUE_ID AS ROLE "
            +
            "FROM OBJECTS DEBIT, OBJECTS USER_ID, ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER, "
            +
            "ATTRIBUTES STATUS_USER, OBJREFERENCE DEBIT_TO_USER, OBJREFERENCE USER_TO_PERSONAL_DEBIT, ATTRIBUTES USER_ROLE  " +
            "WHERE DEBIT.OBJECT_ID = ? " +
            "AND DEBIT_TO_USER.ATTR_ID = 8 /*  REFERENCE FAMILY ACCOUNT TO USER */" +
            "AND DEBIT_TO_USER.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND USER_ID.OBJECT_ID = DEBIT_TO_USER.REFERENCE " +
            "AND NAME_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND NAME_USER.ATTR_ID = 5 /* ATTRIBUTE ID NAME OF USER */" +
            "AND EMAIL_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND EMAIL_USER.ATTR_ID = 3 /* ATTRIBUTE ID EMAIL OF USER */" +
            "AND PASSWORD_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND PASSWORD_USER.ATTR_ID = 4 /* ATTRIBUTE ID PASSWORD OF USER */" +
            "AND STATUS_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND STATUS_USER.ATTR_ID = 6 /* ATTRIBUTE ID STATUS OF USER */" +
            "AND USER_TO_PERSONAL_DEBIT.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */" +
            "AND USER_TO_PERSONAL_DEBIT.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.ATTR_ID = 71";

    String GET_ALL_PARTICIPANTS = "SELECT "
            +
            "USER_ID.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD, "
            +
            "STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL_DEBIT.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1, USER_ROLE.LIST_VALUE_ID AS ROLE "
            +
            "FROM OBJECTS DEBIT, OBJECTS USER_ID, ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER, "
            +
            "ATTRIBUTES STATUS_USER, OBJREFERENCE DEBIT_TO_USER, OBJREFERENCE USER_TO_PERSONAL_DEBIT, ATTRIBUTES USER_ROLE " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 13 " +
            "AND DEBIT_TO_USER.ATTR_ID = 8 /*  REFERENCE FAMILY ACCOUNT TO USER */" +
            "AND DEBIT_TO_USER.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND USER_ID.OBJECT_ID = DEBIT_TO_USER.REFERENCE " +
            "AND NAME_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND NAME_USER.ATTR_ID = 5 /* ATTRIBUTE ID NAME OF USER */" +
            "AND EMAIL_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND EMAIL_USER.ATTR_ID = 3 /* ATTRIBUTE ID EMAIL OF USER */" +
            "AND PASSWORD_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND PASSWORD_USER.ATTR_ID = 4 /* ATTRIBUTE ID PASSWORD OF USER */" +
            "AND STATUS_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND STATUS_USER.ATTR_ID = 6 /* ATTRIBUTE ID STATUS OF USER */" +
            "AND USER_TO_PERSONAL_DEBIT.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */" +
            "AND USER_TO_PERSONAL_DEBIT.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.ATTR_ID = 71";

    String GET_ALL_FAMILY_ACCOUNTS = "SELECT " +
            "DEBIT.OBJECT_ID DEBIT_ID, DEBIT.NAME NAME_DEBIT, AMOUNT.VALUE AMOUNT_DEBIT, STATUS_DEBIT.LIST_VALUE_ID STATUS_DEBIT, "
            +
            "USER_ID.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD, "
            +
            "STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1, USER_ROLE.LIST_VALUE_ID AS ROLE "
            +
            "FROM OBJECTS DEBIT, ATTRIBUTES AMOUNT, ATTRIBUTES STATUS_DEBIT, "
            +
            "OBJECTS USER_ID, ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER, ATTRIBUTES USER_ROLE "
            +
            "ATTRIBUTES STATUS_USER, OBJREFERENCE USER_TO_DEBIT, OBJREFERENCE USER_TO_PERSONAL  "
            +
            "WHERE DEBIT.OBJECT_TYPE_ID = 13 " +
            "AND AMOUNT.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND AMOUNT.ATTR_ID = 9 /* ATTRIBUTE ID AMOUNT OF FAMILY ACCOUNT */" +
            "AND STATUS_DEBIT.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND STATUS_DEBIT.ATTR_ID = 69 /* ATTRIBUTE ID STATUS OF FAMILY ACCOUNT */" +
            "AND USER_TO_DEBIT.ATTR_ID = 2 /*  REFERENCE USER TO FAMILY ACCOUNT  */" +
            "AND USER_TO_DEBIT.REFERENCE = DEBIT.OBJECT_ID " +
            "AND USER_ID.OBJECT_ID = USER_TO_DEBIT.OBJECT_ID " +
            "AND NAME_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND NAME_USER.ATTR_ID = 5 /* ATTRIBUTE ID NAME OF USER */" +
            "AND EMAIL_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND EMAIL_USER.ATTR_ID = 3 /* ATTRIBUTE ID EMAIL OF USER */" +
            "AND PASSWORD_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND PASSWORD_USER.ATTR_ID = 4 /* ATTRIBUTE ID PASSWORD OF USER */" +
            "AND STATUS_USER.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND STATUS_USER.ATTR_ID = 6 /* ATTRIBUTE ID STATUS OF USER */" +
            "AND USER_TO_PERSONAL.ATTR_ID = 1 /*  REFERENCE USER TO PERSONAL ACCOUNT  */" +
            "AND USER_TO_PERSONAL.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.OBJECT_ID = USER_ID.OBJECT_ID " +
            "AND USER_ROLE.ATTR_ID = 71";

    String GET_FAMILY_ACCOUNT_OF_PARTICIPANT_BY_LOGIN ="SELECT USERS.NAME OBJ_NAME, " +
            "NAME.VALUE NAME,  MAIL.VALUE EMAIL, USERS.OBJECT_ID AS USER_ID, " +
            "PASSWORD.VALUE AS PASSWORD, STATUS.LIST_VALUE_ID AS IS_ACTIVE, USER_ROLE.LIST_VALUE_ID AS ROLE, " +
            "(SELECT PER_ACC.REFERENCE FROM OBJREFERENCE PER_ACC WHERE USERS.OBJECT_ID  = PER_ACC.OBJECT_ID AND PER_ACC.ATTR_ID = 1) as PER_DEB_ACC1, " +
            "(SELECT FAM_ACC.OBJECT_ID FROM OBJREFERENCE FAM_ACC WHERE USERS.OBJECT_ID  = FAM_ACC.REFERENCE AND FAM_ACC.ATTR_ID = 8) as FAM_DEB_ACC1  " +
            "from OBJECTS USERS, ATTRIBUTES MAIL, ATTRIBUTES PASSWORD, ATTRIBUTES NAME, ATTRIBUTES STATUS, ATTRIBUTES USER_ROLE " +
            "where USERS.OBJECT_ID = MAIL.OBJECT_ID AND MAIL.ATTR_ID = 3 AND MAIL.VALUE = ? " +
            "AND USERS.OBJECT_ID = PASSWORD.OBJECT_ID AND PASSWORD.ATTR_ID = 4 " +
            "AND USERS.OBJECT_ID = NAME.OBJECT_ID AND NAME.ATTR_ID = 5 " +
            "AND USERS.OBJECT_ID = STATUS.OBJECT_ID AND STATUS.ATTR_ID = 6 " +
            "AND USERS.OBJECT_ID = USER_ROLE.OBJECT_ID AND USER_ROLE.ATTR_ID = 71";
}
