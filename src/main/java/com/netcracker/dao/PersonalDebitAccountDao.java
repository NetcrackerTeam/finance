package com.netcracker.dao;

import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;

public interface PersonalDebitAccountDao {

    PersonalDebitAccount getPersonalAccountById(BigInteger id);

    PersonalDebitAccount createPersonalAccount(PersonalDebitAccount personalDebitAccount);

    void deletePersonalAccountById(BigInteger account_id, BigInteger user_id);

    void deletePersonalAccountByUserId(BigInteger account_id);

    String GET_PERSONAL_ACCOUNT_BY_ID = "SELECT " +
            "DEBIT.OBJECT_ID AS PERSONAL_ID, DEBIT.NAME AS NAME_PERSONAL_DEBIT, ATTR1.VALUE AS AMOUNT_PERSONAL_DEBIT, ATTR2.LIST_VALUE_ID AS STATUS_PERSONAL_DEBIT, " +
            "US.OBJECT_ID AS USER_ID, ATTR1_USER.VALUE AS NAME, ATTR2_USER.VALUE AS EMAIL, ATTR3_USER.VALUE AS PASSWORD " +
            "FROM OBJECTS DEBIT, ATTRIBUTES ATTR1, ATTRIBUTES ATTR2,  " +
            "OBJECTS US, ATTRIBUTES ATTR1_USER, ATTRIBUTES ATTR2_USER, ATTRIBUTES ATTR3_USER, OBJREFERENCE OBJREF " +
            "WHERE DEBIT.OBJECT_TYPE_ID = 2 AND US.OBJECT_TYPE_ID = 1 " +
            "AND DEBIT.OBJECT_ID = ? " +
            "AND ATTR1.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND ATTR1.ATTR_ID = 7 " +
            "AND ATTR2.OBJECT_ID = DEBIT.OBJECT_ID " +
            "AND ATTR2.ATTR_ID = 70 " +
            "AND OBJREF.ATTR_ID = 1 " +
            "AND OBJREF.REFERENCE = DEBIT.OBJECT_ID " +
            "AND US.OBJECT_ID = OBJREF.OBJECT_ID " +
            "AND ATTR1_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR1_USER.ATTR_ID = 5 " +
            "AND ATTR2_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR2_USER.ATTR_ID = 3 " +
            "AND ATTR3_USER.OBJECT_ID = US.OBJECT_ID " +
            "AND ATTR3_USER.ATTR_ID = 4 ";



    String CREATE_PERSONAL_ACCOUNT = "INSERT ALL " +
                    "INTO OBJECTS (OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL, 2, ?) " +
                    "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE) VALUES(7, OBJECTS_ID_S.CURRVAL, ?) " +
                    "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE) VALUES(70, OBJECTS_ID_S.CURRVAL, ?) " +
                    "INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,OBJECTS_ID_S.CURRVAL,?) " +
                    "SELECT * " +
                    "FROM DUAL";

    String DELETE_USER_FROM_PERSONAL_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 7 AND OBJECT_ID = ? AND REFERENCE = ?";
    String UNACTIVE_USER_FROM_PERSONAL_ACCOUNT = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = 44 WHERE ATTR_ID = 70 AND OBJECT_ID = ?";
}