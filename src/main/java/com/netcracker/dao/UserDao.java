package com.netcracker.dao;

import com.netcracker.models.User;
import com.netcracker.models.enums.UserRole;

import java.math.BigInteger;

public interface UserDao {

    User createUser(User user);


    User getUserById(BigInteger id);


    User getUserByEmail(String login);


    void updateUserPasswordById(BigInteger id, String newPassword);


    void updateUserStatus(BigInteger id, BigInteger newStatus);


    void updateEmail(BigInteger id, String newEmail);

    void updateRole(BigInteger id, UserRole role);


    String CREATE_USER = "INSERT ALL  " +
            "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.NEXTVAL,NULL,1,'user'||OBJECTS_ID_S.CURRVAL,NULL) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (3,OBJECTS_ID_S.CURRVAL,?,NULL,NULL /* MAIL*/) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (4,OBJECTS_ID_S.CURRVAL,?,NULL,NULL /* PASSWORD*/) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (5,OBJECTS_ID_S.CURRVAL,?,NULL,NULL /* NAME*/) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (6,OBJECTS_ID_S.CURRVAL,NULL,NULL,? /* IS_ACTIVE*/) " +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (71,OBJECTS_ID_S.CURRVAL,NULL,NULL,45 /* ROLE*/) " +
            "SELECT * " +
            "FROM Dual";

    String GET_USER_BY_LOGIN = "SELECT USERS.NAME OBJ_NAME, " +
            "       NAME.VALUE NAME,  MAIL.VALUE EMAIL, USERS.OBJECT_ID AS USER_ID, " +
            "       PASSWORD.VALUE AS PASSWORD, STATUS.LIST_VALUE_ID AS IS_ACTIVE, USER_ROLE.LIST_VALUE_ID AS ROLE, " +
            "       (SELECT PER_ACC.REFERENCE FROM OBJREFERENCE PER_ACC WHERE USERS.OBJECT_ID  = PER_ACC.OBJECT_ID AND PER_ACC.ATTR_ID = 1) as PER_DEB_ACC1, " +
            "       (SELECT FAM_ACC.REFERENCE FROM OBJREFERENCE FAM_ACC WHERE USERS.OBJECT_ID  = FAM_ACC.OBJECT_ID AND FAM_ACC.ATTR_ID = 2) as FAM_DEB_ACC1 " +
            "from OBJECTS USERS, ATTRIBUTES MAIL, ATTRIBUTES PASSWORD, ATTRIBUTES NAME, ATTRIBUTES STATUS, ATTRIBUTES USER_ROLE " +
            "where USERS.OBJECT_ID = MAIL.OBJECT_ID AND MAIL.ATTR_ID = 3 AND MAIL.VALUE = ? " +
            "  AND USERS.OBJECT_ID = PASSWORD.OBJECT_ID AND PASSWORD.ATTR_ID = 4 " +
            "  AND USERS.OBJECT_ID = NAME.OBJECT_ID AND NAME.ATTR_ID = 5 " +
            "  AND USERS.OBJECT_ID = STATUS.OBJECT_ID AND STATUS.ATTR_ID = 6" +
            "  and USERS.OBJECT_ID = USER_ROLE.OBJECT_ID AND USER_ROLE.ATTR_ID = 71";

String GET_USER_BY_USER_ID = "select USERS.NAME OBJ_NAME,\n" +
        "   NAME.VALUE NAME,  MAIL.VALUE EMAIL, USERS.OBJECT_ID AS USER_ID,\n" +
        "   PASSWORD.VALUE AS PASSWORD, STATUS.LIST_VALUE_ID AS IS_ACTIVE, USER_ROLE.LIST_VALUE_ID AS ROLE,\n" +
        "\n" +
        "   (SELECT PER_ACC.REFERENCE FROM OBJREFERENCE PER_ACC WHERE USERS.OBJECT_ID  = PER_ACC.OBJECT_ID AND PER_ACC.ATTR_ID = 1) as PER_DEB_ACC1,\n" +
        "   (SELECT FAM_ACC.REFERENCE FROM OBJREFERENCE FAM_ACC WHERE USERS.OBJECT_ID  = FAM_ACC.OBJECT_ID AND FAM_ACC.ATTR_ID = 2) as FAM_DEB_ACC1\n" +
        "from OBJECTS USERS, ATTRIBUTES MAIL, ATTRIBUTES PASSWORD, ATTRIBUTES NAME, ATTRIBUTES STATUS, ATTRIBUTES USER_ROLE\n" +
        "where USERS.OBJECT_ID = MAIL.OBJECT_ID AND MAIL.ATTR_ID = 3\n" +
        "  AND USERS.OBJECT_ID = ?\n" +
        "  AND USERS.OBJECT_ID = PASSWORD.OBJECT_ID AND PASSWORD.ATTR_ID = 4\n" +
        "  AND USERS.OBJECT_ID = NAME.OBJECT_ID AND NAME.ATTR_ID = 5\n" +
        "  AND USERS.OBJECT_ID = STATUS.OBJECT_ID AND STATUS.ATTR_ID = 6\n" +
        "  and USERS.OBJECT_ID = USER_ROLE.OBJECT_ID AND USER_ROLE.ATTR_ID = 71";


    String UPDATE_PASSWORD = "UPDATE ATTRIBUTES " +
            "SET VALUE = ? " +
            "WHERE OBJECT_ID = ?  " +
            " AND ATTR_ID = 4 /* PASSWORD*/ ";


    String UPDATE_STATUS = "UPDATE ATTRIBUTES " +
            "SET LIST_VALUE_ID = ? " +
            "WHERE OBJECT_ID = ?  " +
            " AND ATTR_ID = 6 /*IS_ACTIVE */ ";


    String UPDATE_EMAIL = "UPDATE ATTRIBUTES " +
            "SET VALUE = ? " +
            "WHERE OBJECT_ID = ?  " +
            " AND ATTR_ID = 3 /* MAIL*/ ";

    String UPDATE_ROLE = "UPDATE ATTRIBUTES SET LIST_VALUE_ID = ? WHERE OBJECT_ID = ? AND ATTR_ID = 71";
}
