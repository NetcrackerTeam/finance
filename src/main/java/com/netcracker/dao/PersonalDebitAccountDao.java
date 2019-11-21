package com.netcracker.dao;

import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;

public interface PersonalDebitAccountDao {
    public PersonalDebitAccount getPersonalAccountById(BigInteger id);

    public void createPersonalAccount(PersonalDebitAccount pda);

    public void deletePersonalAccountById(BigInteger id);

    public void deletePersonalAccountByUserId(BigInteger id);

    String GET_PERSONAL_ACCOUNT_BY_ID = "SELECT * FROM Objects WHERE OBJECT_TYPE_ID = ?";
    String CREATE_PERSONAL_ACCOUNT = "INSERT ALL" +
            "INTO objects (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION)" +
            "VALUES (OBJECTS_ID_S.NEXTVAL,NULL,2,?,NULL);" +
            "into attributes(attr_id, object_id, values)" +
            "VALUES (2, OBJECT_SEQUENCE.CURRVAL,?)";
    String DELETE_USER_FROM_PERSONAL_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 2, OBJECT_ID = ?, REFERENCE = ?";
}