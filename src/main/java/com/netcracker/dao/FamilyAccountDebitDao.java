package com.netcracker.dao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;


import java.math.BigInteger;
import java.util.ArrayList;
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
     * @param id family account debit id
     */
    void deleteFamilyAccount(BigInteger id);

    /**
     * Add user to family account debit by family account debit id and user id.
     *
     * @param accountId family account debit id
     * @param userId user id
     */
    void addUserToAccountById(BigInteger accountId, BigInteger userId);

    /**
     * Delete user to family account debit by family account debit id and user id.
     *
     * @param accountId family account debit id
     * @param userId user id
     */
    void deleteUserFromAccountById(BigInteger accountId, BigInteger userId);

    /**
     * Update amount of family account debit by family account debit id
     *
     * @param accountId family account debit id
     * @param amount amount of family account debit
     */
    void updateAmountOfFamilyAccount(BigInteger accountId, Long amount);

    /**
     * Get participants of family account debit by family account debit id.
     *
     * @param accountId family account debit id
     * @return User Collection
     */
    Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId);

    /**
     * Get transactions income of family account debit by family account debit id.
     *
     * @param accountId family account debit id
     * @return AccountIncome Collection
     */
    Collection<AccountIncome> getIncomesOfFamilyAccount(BigInteger accountId);

    /**
     * Get transactions expense of family account debit by family account debit id.
     *
     * @param accountId family account debit id
     * @return AccountExpense Collection
     */
    Collection<AccountExpense> getExpensesOfFamilyAccount(BigInteger accountId);

    String ADD_USER_BY_ID = "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,?,?)";

    String FIND_FAMILY_ACCOUNT_BY_ID = "SELECT " +
            "DEBIT.OBJECT_ID DEBIT_ID, DEBIT.NAME NAME_DEBIT, AMOUNT.VALUE AMOUNT_DEBIT, STATUS_DEBIT.LIST_VALUE_ID STATUS_DEBIT, "
            +
            "USER_ID.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD, "
            +
            "STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1 "
            +
            "FROM OBJECTS DEBIT, ATTRIBUTES AMOUNT, ATTRIBUTES STATUS_DEBIT, "
            +
            "OBJECTS USER_ID, ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER, "
            +
            "ATTRIBUTES STATUS_USER, OBJREFERENCE USER_TO_DEBIT, OBJREFERENCE USER_TO_PERSONAL  "
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
            "AND USER_TO_PERSONAL.OBJECT_ID = USER_ID.OBJECT_ID ";

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

    String UPDATE_FALIMY_ACCOUNT_AMOUNT = "UPDATE ATTRIBUTES SET VALUE = ? WHERE ATTR_ID = 9 AND OBJECT_ID = ?";

    String GET_PARTICIPANTS = "SELECT "
            +
            "USER_ID.OBJECT_ID USER_ID, NAME_USER.VALUE NAME, EMAIL_USER.VALUE EMAIL, PASSWORD_USER.VALUE PASSWORD, "
            +
            "STATUS_USER.LIST_VALUE_ID IS_ACTIVE, USER_TO_PERSONAL_DEBIT.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1 "
            +
            "FROM OBJECTS DEBIT, OBJECTS USER_ID, ATTRIBUTES NAME_USER, ATTRIBUTES EMAIL_USER, ATTRIBUTES PASSWORD_USER, "
            +
            "ATTRIBUTES STATUS_USER, OBJREFERENCE DEBIT_TO_USER, OBJREFERENCE USER_TO_PERSONAL_DEBIT  " +
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
            "AND USER_TO_PERSONAL_DEBIT.OBJECT_ID = USER_ID.OBJECT_ID ";


    String GET_INCOME_LIST = "SELECT " +
            "INCOME.OBJECT_ID ACCOUNT_INCOME_ID, AMOUNT.VALUE INCOME_AMOUNT, DATE_TRANSACTION.DATE_VALUE DATE_INCOME, CATEGORY.LIST_VALUE_ID CATEGORY_INCOME, INCOME_TO_USER.REFERENCE USER_ID " +
            "FROM OBJECTS INCOME, ATTRIBUTES AMOUNT, ATTRIBUTES DATE_TRANSACTION, ATTRIBUTES CATEGORY, OBJECTS DEBIT, OBJREFERENCE INCOME_TO_DEBIT, OBJREFERENCE INCOME_TO_USER " +
            "WHERE INCOME.OBJECT_TYPE_ID = 21 /* TYPE OBJECT OF INCOME*/" +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND INCOME_TO_DEBIT.ATTR_ID = 54 /* REFERENCE INCOME TO FAMILY ACCOUNT */" +
            "AND INCOME_TO_DEBIT.REFERENCE = DEBIT.OBJECT_ID " +
            "AND INCOME.OBJECT_ID = INCOME_TO_DEBIT.OBJECT_ID " +
            "AND INCOME_TO_USER.ATTR_ID = 55 /* REFERENCE INCOME TO USER */" +
            "AND INCOME_TO_USER.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND AMOUNT.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND AMOUNT.ATTR_ID = 56 /* ATTRIBUTE ID AMOUNT OF INCOME */" +
            "AND DATE_TRANSACTION.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND DATE_TRANSACTION.ATTR_ID = 58 /* ATTRIBUTE ID DATE TRANSACTION OF INCOME */" +
            "AND CATEGORY.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND CATEGORY.ATTR_ID = 57 /* ATTRIBUTE ID CATEGORY OF INCOME */";

    String GET_EXPENSE_LIST = "SELECT " +
            "EXPENSE.OBJECT_ID ACCOUNT_EXPENSE_ID, AMOUNT.VALUE EXPENSE_AMOUNT, DATE_TRANSACTION.DATE_VALUE DATE_EXPENSE, CATEGORY.LIST_VALUE_ID CATEGORY_EXPENSE, EXPENSE_TO_USER.REFERENCE USER_ID " +
            "FROM OBJECTS EXPENSE, ATTRIBUTES AMOUNT, ATTRIBUTES DATE_TRANSACTION, ATTRIBUTES CATEGORY, OBJECTS DEBIT, OBJREFERENCE EXPENSE_TO_DEBIT, OBJREFERENCE EXPENSE_TO_USER " +
            "WHERE EXPENSE.OBJECT_TYPE_ID = 20 /* TYPE OBJECT OF EXPENSE*/" +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND EXPENSE_TO_DEBIT.ATTR_ID = 48 /* REFERENCE EXPENSE TO FAMILY ACCOUNT */" +
            "AND EXPENSE_TO_DEBIT.REFERENCE = DEBIT.OBJECT_ID " +
            "AND EXPENSE.OBJECT_ID = EXPENSE_TO_DEBIT.OBJECT_ID " +
            "AND EXPENSE_TO_USER.ATTR_ID = 49 /* REFERENCE EXPENSE TO USER */" +
            "AND EXPENSE_TO_USER.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND AMOUNT.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND AMOUNT.ATTR_ID = 50 /* ATTRIBUTE ID AMOUNT OF EXPENSE */" +
            "AND DATE_TRANSACTION.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND DATE_TRANSACTION.ATTR_ID = 52 /* ATTRIBUTE ID DATE TRANSACTION OF EXPENSE */" +
            "AND CATEGORY.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND CATEGORY.ATTR_ID = 51 /* ATTRIBUTE ID CATEGORY OF EXPENSE */";
}
