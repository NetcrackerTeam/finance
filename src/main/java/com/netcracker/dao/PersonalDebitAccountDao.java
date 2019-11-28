package com.netcracker.dao;

import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;

public interface PersonalDebitAccountDao {

    PersonalDebitAccount getPersonalAccountById(BigInteger id);

    PersonalDebitAccount createPersonalAccount(PersonalDebitAccount personalDebitAccount);

    void deletePersonalAccountById(BigInteger id);

    void deletePersonalAccountByUserId(BigInteger id);

    String GET_PERSONAL_ACCOUNT_BY_ID = "SELECT " +
            "debit.object_id as personal_id, debit.name as name_personal_debit, attr1.value as amount_personal_debit, attr2.list_value_id as status_personal_debit, " +
            "us.object_id as USER_ID, attr1_user.value as NAME, attr2_user.value as EMAIL, attr3_user.value as PASSWORD " +
            "from OBJECTS debit, attributes attr1, attributes attr2,  " +
            "objects us, attributes attr1_user, attributes attr2_user, attributes attr3_user, OBJREFERENCE objref " +
            "where debit.object_type_id = 2 and us.object_type_id = 1 " +
            "and debit.object_id = ? " +
            "and attr1.object_id = debit.object_id " +
            "and attr1.attr_id = 7 " +
            "and attr2.object_id = debit.object_id " +
            "and attr2.attr_id = 70 " +
            "and objref.attr_id = 1 " +
            "and objref.reference = debit.object_id " +
            "and us.object_id = objref.object_id " +
            "and attr1_user.object_id = us.object_id " +
            "and attr1_user.attr_id = 5 " +
            "and attr2_user.object_id = us.object_id " +
            "and attr2_user.attr_id = 3 " +
            "and attr3_user.object_id = us.object_id " +
            "and attr3_user.attr_id = 4 ";

    String CREATE_PERSONAL_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, 2, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(7, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(70, objects_id_s.currval, ?) ";
    String DELETE_USER_FROM_PERSONAL_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 2, OBJECT_ID = ?, REFERENCE = ?";
    String UNACTIVE_USER_FROM_PERSONAL_ACCOUNT = "update attributes set list_value_id = 44 where attr_id = 70 and object_id = ?";
}