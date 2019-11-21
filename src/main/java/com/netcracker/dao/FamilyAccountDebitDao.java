package com.netcracker.dao;
import com.netcracker.models.FamilyDebitAccount;


import java.math.BigInteger;

public interface FamilyAccountDebitDao {
    FamilyDebitAccount getFamilyAccountById(BigInteger id);

    FamilyDebitAccount createFamilyAccount(FamilyDebitAccount familyDebitAccount);

    void deleteFamilyAccount(BigInteger id);

    void addUserToAccountById(BigInteger user_id, BigInteger account_id);

    void deleteUserFromAccountById(BigInteger account_id, BigInteger user_id);

    String ADD_USER_BY_ID = "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,?,?)";
    String FIND_FAMILY_ACCOUNT_BY_ID = "SELECT NAME FROM OBJECTS WHERE OBJECT_ID = ?";
    String ADD_NEW_FAMILY_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, 2, ?)"
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(9, objects_id_s.currval, ?)";
    String DELETE_USER_FROM_FAMILY_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 8, OBJECT_ID = ?, REFERENCE = ?";
    String SET_FAMILY_ACCOUNT_UNACTIVE = "update attributes set list_value_id = 42 where attr_id = 69 and object_id = ?";
    String CHEK_FAMILY_ACC = "SELECT reference FROM objreference WHERE attr_id = 8 and object_id = ? and reference = ?";
    String CHEK_USER_ACTIVE = "SELECT value FROM attributes WHERE attr_id = 6 object_id = ?";
}
