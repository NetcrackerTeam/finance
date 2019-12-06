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
            "DEBIT.OBJECT_ID DEBIT_ID, DEBIT.NAME NAME_DEBIT, ATTR1.VALUE AMOUNT_DEBIT, ATTR2.LIST_VALUE_ID STATUS_DEBIT, "
            +
            "US.OBJECT_ID USER_ID,  ATTR1_USER.VALUE  NAME, ATTR2_USER.VALUE  EMAIL, ATTR3_USER.VALUE  PASSWORD, "
            +
            "ATTR4_USER.LIST_VALUE_ID IS_ACTIVE, OBJREF2.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1 "
            +
            "FROM OBJECTS DEBIT, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2,  "
            +
            "OBJECTS US, ATTRIBUTES ATTR1_USER, ATTRIBUTES ATTR2_USER, ATTRIBUTES ATTR3_USER, "
            +
            "ATTRIBUTES ATTR4_USER, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2  "
            +
            "WHERE DEBIT.OBJECT_TYPE_ID = 13 AND US.OBJECT_TYPE_ID = 1 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND ATTR1.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 9 " +
            "AND ATTR2.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 69 " +
            "AND OBJREF1.ATTR_ID = 2 " +
            "AND OBJREF1.REFERENCE = DEBIT.OBJECT_ID " +
            "AND US.OBJECT_ID = OBJREF1.OBJECT_ID " +
            "AND ATTR1_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR1_USER.ATTR_ID = 5 " +
            "AND ATTR2_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR2_USER.ATTR_ID = 3 " +
            "AND ATTR3_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR3_USER.ATTR_ID = 4 " +
            "AND ATTR4_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR4_USER.ATTR_ID = 6 " +
            "AND OBJREF2.ATTR_ID = 1 " +
            "AND OBJREF2.OBJECT_ID = US.OBJECT_ID ";

    String ADD_NEW_FAMILY_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL, 13, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(9, OBJECTS_ID_S.CURRVAL, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(69, OBJECTS_ID_S.CURRVAL, ?) "
            +
            "INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,?,OBJECTS_ID_S.CURRVAL) "
            +
            "INTO OBJREFERENCE(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,OBJECTS_ID_S.CURRVAL, ?) "
            +
            "SELECT * FROM DUAL";
    String DELETE_USER_FROM_FAMILY_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 8 AND OBJECT_ID = ? AND REFERENCE = ?";

    String SET_FAMILY_ACCOUNT_UNACTIVE = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = 42 WHERE ATTR_ID = 69 AND OBJECT_ID = ?";

    String UPDATE_FALIMY_ACCOUNT_AMOUNT = "UPDATE ATTRIBUTES SET VALUE = ? WHERE ATTR_ID = 9 AND OBJECT_ID = ?";

    String GET_PARTICIPANTS = "SELECT "
            +
            "US_PART.OBJECT_ID USER_ID, ATTR1_US_PART.VALUE NAME, ATTR2_US_PART.VALUE EMAIL, ATTR3_US_PART.VALUE PASSWORD, "
            +
            "ATTR4_US_PART.LIST_VALUE_ID IS_ACTIVE, OBJREF2.REFERENCE PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1 "
            +
            "FROM OBJECTS DEBIT, OBJECTS US_PART, ATTRIBUTES ATTR1_US_PART, ATTRIBUTES ATTR2_US_PART, ATTRIBUTES ATTR3_US_PART, "
            +
            "ATTRIBUTES ATTR4_US_PART, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2  " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 13 AND US_PART.OBJECT_TYPE_ID = 1 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND OBJREF1.ATTR_ID = 8 " +
            "AND OBJREF1.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND US_PART.OBJECT_ID = OBJREF1.REFERENCE " +
            "AND ATTR1_US_PART.OBJECT_ID = US_PART.OBJECT_ID " +
            "AND ATTR1_US_PART.ATTR_ID = 5 " +
            "AND ATTR2_US_PART.OBJECT_ID = US_PART.OBJECT_ID " +
            "AND ATTR2_US_PART.ATTR_ID = 3 " +
            "AND ATTR3_US_PART.OBJECT_ID = US_PART.OBJECT_ID " +
            "AND ATTR3_US_PART.ATTR_ID = 4" +
            "AND ATTR4_US_PART.OBJECT_ID = US_PART.OBJECT_ID " +
            "AND ATTR4_US_PART.ATTR_ID = 6 " +
            "AND OBJREF2.ATTR_ID = 1 " +
            "AND OBJREF2.OBJECT_ID = US_PART.OBJECT_ID ";


    String GET_INCOME_LIST = "SELECT " +
            "INCOME.OBJECT_ID ACCOUNT_INCOME_ID, ATTR1.VALUE INCOME_AMOUNT, ATTR2.DATE_VALUE DATE_INCOME, ATTR3.LIST_VALUE_ID CATEGORY_INCOME, OBJREF2.REFERENCE USER_ID " +
            "FROM OBJECTS INCOME, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2, ATTRIBUTES ATTR3, OBJECTS DEBIT, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2 " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 13 AND INCOME.OBJECT_TYPE_ID = 21 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND OBJREF1.ATTR_ID = 54 " +
            "AND OBJREF1.REFERENCE = DEBIT.OBJECT_ID " +
            "AND INCOME.OBJECT_ID = OBJREF1.OBJECT_ID " +
            "AND OBJREF2.ATTR_ID = 55 " +
            "AND OBJREF2.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR1.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 56 " +
            "AND ATTR2.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 58 " +
            "AND ATTR3.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR3.ATTR_ID = 57";

    String GET_EXPENSE_LIST = "SELECT " +
            "EXPENSE.OBJECT_ID ACCOUNT_EXPENSE_ID, ATTR1.VALUE EXPENSE_AMOUNT, ATTR2.DATE_VALUE DATE_EXPENSE, ATTR3.LIST_VALUE_ID CATEGORY_EXPENSE, OBJREF2.REFERENCE USER_ID " +
            "FROM OBJECTS EXPENSE, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2, ATTRIBUTES ATTR3, OBJECTS DEBIT, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2 " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 13 AND EXPENSE.OBJECT_TYPE_ID = 20 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND OBJREF1.ATTR_ID = 48 " +
            "AND OBJREF1.REFERENCE = DEBIT.OBJECT_ID " +
            "AND EXPENSE.OBJECT_ID = OBJREF1.OBJECT_ID " +
            "AND OBJREF2.ATTR_ID = 49 " +
            "AND OBJREF2.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR1.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 50 " +
            "AND ATTR2.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 52 " +
            "AND ATTR3.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR3.ATTR_ID = 51";
}
