package com.netcracker.dao;

import com.netcracker.models.User;

import java.math.BigInteger;

public interface UserDao {

    public User createUser(User user);


    public User getUserById(BigInteger id);


    public User getUserByLogin(String login);



    public void updateUserPasswordById(BigInteger id, String newPassword);


    String CREATE_USER = "INSERT ALL  " +
            "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.NEXTVAL,NULL,1,'user'||objects_id_s.CURRVAL,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (3,objects_id_s.CURRVAL,?,NULL,NULL /* MAIL*/) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (4,objects_id_s.CURRVAL,?,NULL,NULL /* PASSWORD*/) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (5,objects_id_s.CURRVAL,?,NULL,NULL /* NAME*/) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (6,objects_id_s.CURRVAL,NULL,NULL,? /* IS_ACTIVE*/) "
            +
            "SELECT * " +
            "FROM Dual";

    String GET_USER_BY_LOGIN =  "SELECT EMP.OBJECT_ID AS USER_ID, EMP_NAME.VALUE AS NAME, EMP_EMAIL.VALUE AS EMAIL, "+
            "PASSWORD.VALUE AS PASSWORD, EMP_STATUS.LIST_VALUE_ID AS IS_ACTIVE, PER.OBJECT_ID AS PER_DEB_ACC1, FAM.OBJECT_ID AS FAM_DEB_ACC1 "+
            "FROM  OBJECTS EMP, OBJECTS PER, OBJECTS FAM, "+
            "ATTRIBUTES EMP_NAME, ATTRIBUTES EMP_EMAIL, ATTRIBUTES PASSWORD, ATTRIBUTES EMP_STATUS, "+
            "OBJREFERENCE PER_US, OBJREFERENCE FAM_US "
            +"WHERE  EMP.OBJECT_TYPE_ID = 1 AND PER.OBJECT_TYPE_ID = 2 AND FAM.OBJECT_TYPE_ID = 13 AND " +
            "EMP_NAME.ATTR_ID = 5 AND /* NAME*/ " +
            "EMP_NAME.VALUE = ? AND " +
            "EMP_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_EMAIL.ATTR_ID = 3 AND /* MAIL*/ "+
            "EMP_EMAIL.OBJECT_ID = EMP.OBJECT_ID AND "+
            "PASSWORD.ATTR_ID = 4 AND /* PASSWORD*/ "+
            "PASSWORD.OBJECT_ID = EMP.OBJECT_ID AND "+
            "EMP_STATUS.ATTR_ID = 6 AND /* IS_ACTIVE*/ " +
            "EMP_STATUS.OBJECT_ID = EMP.OBJECT_ID AND "+
            "PER_US.ATTR_ID = 1 AND /* OWNER_PERSONAL_ACC */ "+
            "PER_US.OBJECT_ID = EMP.OBJECT_ID AND "+
            "PER.OBJECT_ID = PER_US.REFERENCE AND "+
            "FAM_US.ATTR_ID = 2 AND /* OWNER_FAMILY_ACC */ "+
            "FAM_US.OBJECT_ID = EMP.OBJECT_ID AND " +
            "FAM.OBJECT_ID = FAM_US.REFERENCE ";


    String GET_USER_BY_USER_ID = "SELECT EMP.OBJECT_ID AS USER_ID, EMP_NAME.VALUE AS NAME, EMP_EMAIL.VALUE AS EMAIL, "+
            "PASSWORD.VALUE AS PASSWORD, EMP_STATUS.LIST_VALUE_ID AS IS_ACTIVE, PER.OBJECT_ID AS PER_DEB_ACC1, FAM.OBJECT_ID AS FAM_DEB_ACC1 "+
            "FROM  OBJECTS EMP, OBJECTS PER, OBJECTS FAM, "+
            "ATTRIBUTES EMP_NAME, ATTRIBUTES EMP_EMAIL, ATTRIBUTES PASSWORD, ATTRIBUTES EMP_STATUS, "+
            "OBJREFERENCE PER_US, OBJREFERENCE FAM_US "
            +"WHERE  EMP.OBJECT_TYPE_ID = 1 AND PER.OBJECT_TYPE_ID = 2 AND FAM.OBJECT_TYPE_ID = 13 AND " +
            "EMP.OBJECT_ID = ? AND " +
            "EMP_NAME.ATTR_ID = 5 AND /* NAME */ " +
            "EMP_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +
            "EMP_EMAIL.ATTR_ID = 3 AND /* MAIL*/ "+
            "EMP_EMAIL.OBJECT_ID = EMP.OBJECT_ID AND "+
            "PASSWORD.ATTR_ID = 4 AND /* PASSWORD*/ "+
            "PASSWORD.OBJECT_ID = EMP.OBJECT_ID AND "+
            "EMP_STATUS.ATTR_ID = 6 AND /* IS_ACTIVE*/ " +
            "EMP_STATUS.OBJECT_ID = EMP.OBJECT_ID AND "+
            "PER_US.ATTR_ID = 1 AND /* OWNER_PERSONAL_ACC */ "+
            "PER_US.OBJECT_ID = EMP.OBJECT_ID AND "+
            "PER.OBJECT_ID = PER_US.REFERENCE AND "+
            "FAM_US.ATTR_ID = 2 AND /* OWNER_FAMILY_ACC */ "+
            "FAM_US.OBJECT_ID = EMP.OBJECT_ID AND " +
            "FAM.OBJECT_ID = FAM_US.REFERENCE ";


    String UPDATE_PASSWORD = "UPDATE ATTRIBUTES " +
            "SET VALUE = ? " +
            "WHERE OBJECT_ID = ? AND " +
            "ATTR_ID = 4 /* PASSWORD*/ ";
}
