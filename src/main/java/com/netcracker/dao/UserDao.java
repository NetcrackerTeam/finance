package com.netcracker.dao;

import com.netcracker.models.User;

import java.math.BigInteger;

public interface UserDao {

    /**
     * create new user
     * @param `user
     * @return  user
     */
    public User createUser(User user);

    /**
     * get user by id from db
     * @param id
     * @return user by id
     */
    public User getUserById(BigInteger id);

    /**
     * get user by login
     * @param login
     * @return user
     */
    public User getUserByLogin(String login);

    /**
     * update password
     * input old id user and new password
     * update values
     * @param id
     * @param newPassword
     */

    public void updateUserPasswordById(BigInteger id, String newPassword);


    String CREATE_USER = "INSERT ALL  " +
            "INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.NEXTVAL,NULL,1,?,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (1,objects_id_s.CURRVAL,?,NULL,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (2,objects_id_s.CURRVAL,?,NULL,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (3,objects_id_s.CURRVAL,?,NULL,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (4,objects_id_s.CURRVAL,?,NULL,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (5,objects_id_s.CURRVAL,?,NULL,NULL) "
            +
            "INTO ATTRIBUTES (ATTR_ID,OBJECT_ID,VALUE,DATE_VALUE,LIST_VALUE_ID) VALUES (6,objects_id_s.CURRVAL,NULL,NULL,?) "
            +
            "SELECT * " +
            "FROM Dual";
    ;

    String GET_USER_BY_USER_ID = "SELECT EMP.OBJECT_ID AS USER_ID,NAME.VALUE AS NAME, EMP_EMAIL.VALUE AS MAIL, "+
            "PASSWORD.VALUE AS PASSWORD, EMP_STATUS.VALUE AS IS_ACTIVE"+
            "FROM  OBJTYPE EMP_TYPE, OBJECTS EMP, "+
            "ATTRIBUTES NAME,ATTRIBUTES EMP_EMAIL, ATTRIBUTES PASSWORD,ATTRIBUTES EMP_STATUS "
            +"WHERE  EMP_TYPE.OBJECT_TYPE_ID = 1 AND " +
            "EMP.OBJECT_TYPE_ID = EMP_TYPE.OBJECT_TYPE_ID AND " +
            "EMP.OBJECT_ID = ? AND " +
            "EMP_NAME.ATTR_ID = 1 AND " +
            "EMP_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +"";


    String GET_USER_BY_LOGIN = "SELECT EMP.OBJECT_ID AS USER_ID,NAME.VALUE AS NAME, EMP_EMAIL.VALUE AS MAIL, "+
            "PASSWORD.VALUE AS PASSWORD, EMP_STATUS.VALUE AS IS_ACTIVE"+
            "FROM  OBJTYPE EMP_TYPE, OBJECTS EMP, "+
            "ATTRIBUTES NAME,ATTRIBUTES EMP_EMAIL, ATTRIBUTES PASSWORD,ATTRIBUTES EMP_STATUS "
            +"WHERE  EMP_TYPE.OBJECT_TYPE_ID = 1 AND " +
            "EMP.OBJECT_TYPE_ID = EMP_TYPE.OBJECT_TYPE_ID AND " +
            "EMP.OBJECT_ID = ? AND " +
            "EMP_NAME.ATTR_ID = 1 AND " +
            "EMP_NAME.OBJECT_ID = EMP.OBJECT_ID AND " +"";


    String UPDATE_PASSWORD = "UPDATE ATTRIBUTES " +
            "SET ATTRIBUTES.VALUE = ? " +
            "WHERE ATTRIBUTES.OBJECT_ID = ? AND " +
            "ATTRIBUTES.ATTR_ID = 4";


}
