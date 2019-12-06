package com.netcracker.dao;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;
import java.util.ArrayList;

public interface PersonalDebitAccountDao {

    PersonalDebitAccount getPersonalAccountById(BigInteger id);

    PersonalDebitAccount createPersonalAccount(PersonalDebitAccount personalDebitAccount);

    void deletePersonalAccountById(BigInteger account_id, BigInteger user_id);

    void deletePersonalAccountByUserId(BigInteger account_id);

    ArrayList<AccountIncome> getIncomesOfPersonalAccount(BigInteger debit_id);

    ArrayList<AccountExpense> getExpensesOfPersonalAccount(BigInteger debit_id);

    String GET_PERSONAL_ACCOUNT_BY_ID ="SELECT " +
            "DEBIT.OBJECT_ID PERSONAL_ID, DEBIT.NAME NAME_PERSONAL_DEBIT, ATTR1.VALUE AMOUNT_PERSONAL_DEBIT, ATTR2.LIST_VALUE_ID STATUS_PERSONAL_DEBIT, "
            +
            "US.OBJECT_ID USER_ID,  ATTR1_USER.VALUE  NAME, ATTR2_USER.VALUE  EMAIL, ATTR3_USER.VALUE  PASSWORD, "
            +
            "ATTR4_USER.LIST_VALUE_ID IS_ACTIVE, PER_DEBIT.OBJECT_ID PER_DEB_ACC1, DEBIT.OBJECT_ID FAM_DEB_ACC1 "
            +
            "FROM OBJECTS DEBIT, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2,  "
            +
            "OBJECTS US, ATTRIBUTES ATTR1_USER, ATTRIBUTES ATTR2_USER, ATTRIBUTES ATTR3_USER, "
            +
            "ATTRIBUTES ATTR4_USER, OBJECTS PER_DEBIT, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2  "
            +
            "WHERE DEBIT.OBJECT_TYPE_ID = 2 AND US.OBJECT_TYPE_ID = 1 AND PER_DEBIT.OBJECT_TYPE_ID = 2 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND ATTR1.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 7 " +
            "AND ATTR2.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 70 " +
            "AND OBJREF1.ATTR_ID = 1 " +
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
            "AND OBJREF2.OBJECT_ID = US.OBJECT_ID " +
            "AND PER_DEBIT.OBJECT_ID = OBJREF2.REFERENCE ";

    String CREATE_PERSONAL_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS (OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL, 2, ?) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE) VALUES(7, OBJECTS_ID_S.CURRVAL, ?) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(70, OBJECTS_ID_S.CURRVAL, ?) " +
            "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,OBJECTS_ID_S.CURRVAL,?) " +
            "SELECT * " +
            "FROM DUAL";
    String GET_INCOME_LIST = "SELECT " +
            "INCOME.OBJECT_ID ACCOUNT_INCOME_ID, ATTR1.VALUE INCOME_AMOUNT, ATTR2.DATE_VALUE DATE_INCOME, ATTR3.LIST_VALUE_ID CATEGORY_INCOME, US.OBJECT_ID USER_ID " +
            "FROM OBJECTS INCOME,  OBJECTS US, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2, ATTRIBUTES ATTR3, OBJECTS DEBIT, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2 " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 2 AND US.OBJECT_TYPE_ID = 1 AND INCOME.OBJECT_TYPE_ID = 10 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND OBJREF1.ATTR_ID = 53 " +
            "AND OBJREF1.REFERENCE = DEBIT.OBJECT_ID " +
            "AND INCOME.OBJECT_ID = OBJREF1.OBJECT_ID " +
            "AND OBJREF2.ATTR_ID = 1 " +
            "AND OBJREF2.REFERENCE = DEBIT.OBJECT_ID " +
            "AND US.OBJECT_ID = OBJREF2.REFERENCE " +
            "AND ATTR1.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 56 " +
            "AND ATTR2.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 58 " +
            "AND ATTR3.OBJECT_ID = INCOME.OBJECT_ID " +
            "AND ATTR3.ATTR_ID = 57";

    String GET_EXPENSE_LIST = "SELECT " +
            "EXPENSE.OBJECT_ID ACCOUNT_EXPENSE_ID, ATTR1.VALUE EXPENSE_AMOUNT, ATTR2.DATE_VALUE DATE_EXPENSE, ATTR3.LIST_VALUE_ID CATEGORY_EXPENSE, US.OBJECT_ID USER_ID " +
            "FROM OBJECTS EXPENSE,  OBJECTS US, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2, ATTRIBUTES ATTR3, OBJECTS DEBIT, OBJREFERENCE OBJREF1, OBJREFERENCE OBJREF2 " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 2 AND US.OBJECT_TYPE_ID = 1 AND EXPENSE.OBJECT_TYPE_ID = 9 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND OBJREF1.ATTR_ID = 47 " +
            "AND OBJREF1.REFERENCE = DEBIT.OBJECT_ID " +
            "AND EXPENSE.OBJECT_ID = OBJREF1.OBJECT_ID " +
            "AND OBJREF2.ATTR_ID = 1 " +
            "AND OBJREF2.REFERENCE = DEBIT.OBJECT_ID " +
            "AND US.OBJECT_ID = OBJREF2.REFERENCE " +
            "AND ATTR1.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 50 " +
            "AND ATTR2.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 52 " +
            "AND ATTR3.OBJECT_ID = EXPENSE.OBJECT_ID " +
            "AND ATTR3.ATTR_ID = 51";

    String DELETE_USER_FROM_PERSONAL_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 1 AND OBJECT_ID = ? AND REFERENCE = ?";
    String UNACTIVE_USER_FROM_PERSONAL_ACCOUNT = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = 44 WHERE ATTR_ID = 70 AND OBJECT_ID = ?";
}