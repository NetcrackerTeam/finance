package com.netcracker.dao;

import com.netcracker.models.CreditOperation;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CreditOperationDao {
    CreditOperation getCreditOperationPersonal(BigInteger creditOperationId);

    CreditOperation getCreditOperationFamily(BigInteger creditOperationId);

    CreditOperation createFamilyCreditOperation(double amount, LocalDate date, BigInteger creditFamilyAccountId, BigInteger userId);

    CreditOperation createPersonalCreditOperation(double amount, LocalDate date, BigInteger creditPersonalAccountId);

    List<CreditOperation> getAllCreditOperationsByCreditFamilyId(BigInteger creditFamilyAccountId);

    List<CreditOperation> getAllCreditOperationsByCreditPersonalId(BigInteger creditPersonalAccountId);

    void deleteCreditOperation(BigInteger creditOperationId);

    int personal_object_type_id_1 = 7;
    String personal_name_2 = "CREDIT_OPERATION_PERSONAL";
    int family_object_type_id_1 = 18;
    String family_name_2 = "CREDIT_OPERATION_FAMILY";

    String CREATE_OBJECT_CREDIT_OPERATION = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES (OBJECTS_ID_S.NEXTVAL, NULL, ?, ?, NULL)";

    String CREATE_CREDIT_OPERATION_PERSONAL = "INSERT ALL " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (40 /*AMOUNT*/, ?, ? /*AMOUNT*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (41 /*DATE*/, ?, NULL, ?, NULL) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (37, ?, ? /*REFERENCE TO CREDIT_ACC_PERSONAL*/) " + "SELECT * FROM DUAL";

    String CREATE_CREDIT_OPERATION_FAMILY = "INSERT ALL " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (40 /*AMOUNT*/, ?, ? /*AMOUNT*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (41 /*DATE*/, ?, NULL, ?, NULL) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (38, ?, ? /*REFERENCE TO CREDIT_ACC_FAMILY*/) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (39, ?, ? /*REFERENCE TO USER*/) " + "SELECT * FROM DUAL";

    String GET_CREDIT_OPERATION_PERSONAL =
            "SELECT CREDIT_ACC_PERSONAL.OBJECT_ID AS CREDIT_ACC_PERSONAL_ID, CREDIT_ACC_NAME.VALUE AS CREDIT_ACC_NAME, " +
                    "  CREDIT_OPERATION.OBJECT_ID AS OPERATION_ID, CREDIT_OPERATION.NAME AS OPERATION_NAME, " +
                    "  AMOUNT.VALUE AS AMOUNT, DATES.DATE_VALUE AS DATE_VALUE " +
                    "FROM OBJECTS CREDIT_ACC_PERSONAL, OBJECTS CREDIT_OPERATION, ATTRIBUTES CREDIT_ACC_NAME, " +
                    "  ATTRIBUTES AMOUNT, ATTRIBUTES DATES, OBJREFERENCE CREDIT_ACC_REF_USER " +
                    "WHERE CREDIT_ACC_REF_USER.ATTR_ID = 37 /*CREDIT_ACCOUNT_ID_PERSONAL*/" +
                    "  AND CREDIT_OPERATION.OBJECT_ID = ? " +
                    "  AND CREDIT_OPERATION.OBJECT_ID = CREDIT_ACC_REF_USER.OBJECT_ID " +
                    "  AND CREDIT_ACC_PERSONAL.OBJECT_ID = CREDIT_ACC_REF_USER.REFERENCE " +
                    "  AND CREDIT_ACC_NAME.ATTR_ID = 30 /*NAME*/" +
                    "  AND AMOUNT.ATTR_ID = 40 /*AMOUNT*/" +
                    "  AND DATES.ATTR_ID = 41 /*DATE*/" +
                    "  AND CREDIT_ACC_NAME.OBJECT_ID = CREDIT_ACC_PERSONAL.OBJECT_ID " +
                    "  AND AMOUNT.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID " +
                    "  AND DATES.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID";

    String GET_CREDIT_OPERATION_FAMILY =
            "SELECT CREDIT_ACC_FAMILY.OBJECT_ID AS CREDIT_ID_FAMILY, ACC_PERSONAL.OBJECT_ID AS ID_USER, CREDIT_USER.VALUE AS USERNAME, " +
                    "CREDIT_ACC_NAME.VALUE AS CREDIT_ACC_NAME, CREDIT_OPERATION.OBJECT_ID AS OPERATION_ID, " +
                    "CREDIT_OPERATION.NAME AS OPERATION_NAME, AMOUNT.VALUE AS AMOUNT, DATES.DATE_VALUE AS DATE_VALUE " +
                    "FROM OBJECTS CREDIT_ACC_FAMILY, OBJECTS ACC_PERSONAL, OBJECTS CREDIT_OPERATION, ATTRIBUTES CREDIT_ACC_NAME, " +
                    "ATTRIBUTES AMOUNT, ATTRIBUTES DATES, OBJREFERENCE ACC_REF_USER, OBJREFERENCE CREDIT_ACC_REF_FAMILY, " +
                    "ATTRIBUTES CREDIT_USER " +
                    "WHERE CREDIT_OPERATION.OBJECT_ID = ?  " +
                    "AND CREDIT_ACC_REF_FAMILY.ATTR_ID = 38 /*CREDIT_ACCOUNT_ID_FAMILY*/ " +
                    "AND ACC_REF_USER.ATTR_ID = 39 /*CREDIT_ACCOUNT_ID_USER*/ " +
                    "AND CREDIT_USER.ATTR_ID = 5 /*USER NAME*/ " +
                    "AND CREDIT_USER.OBJECT_ID = ACC_REF_USER.REFERENCE " +
                    "AND CREDIT_OPERATION.OBJECT_ID = CREDIT_ACC_REF_FAMILY.OBJECT_ID " +
                    "AND CREDIT_ACC_FAMILY.OBJECT_ID = CREDIT_ACC_REF_FAMILY.REFERENCE " +
                    "AND CREDIT_OPERATION.OBJECT_ID = ACC_REF_USER.OBJECT_ID " +
                    "AND ACC_PERSONAL.OBJECT_ID = ACC_REF_USER.REFERENCE " +
                    "AND CREDIT_ACC_NAME.ATTR_ID = 30 /*NAME*/ " +
                    "AND AMOUNT.ATTR_ID = 40 /*AMOUNT*/ " +
                    "AND DATES.ATTR_ID = 41 /*DATE*/ " +
                    "AND CREDIT_ACC_NAME.OBJECT_ID = CREDIT_ACC_FAMILY.OBJECT_ID " +
                    "AND AMOUNT.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID " +
                    "AND DATES.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID";

    String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";

    String GET_ALL_CREDIT_OPERATIONS_PERSONAL =
            "SELECT CREDIT_ACC_PERSONAL.OBJECT_ID AS CREDIT_ACC_PERSONAL_ID, CREDIT_ACC_NAME.VALUE AS CREDIT_ACC_NAME, " +
                    "  CREDIT_OPERATION.OBJECT_ID AS OPERATION_ID, CREDIT_OPERATION.NAME AS OPERATION_NAME, " +
                    "  AMOUNT.VALUE AS AMOUNT, DATES.DATE_VALUE AS DATE_VALUE " +
                    "FROM OBJECTS CREDIT_ACC_PERSONAL, OBJECTS CREDIT_OPERATION, ATTRIBUTES CREDIT_ACC_NAME, " +
                    "  ATTRIBUTES AMOUNT, ATTRIBUTES DATES, OBJREFERENCE CREDIT_ACC_REF_USER " +
                    "WHERE CREDIT_ACC_PERSONAL.OBJECT_ID = ? " +
                    "  AND CREDIT_ACC_REF_USER.ATTR_ID = 37 /*CREDIT_ACCOUNT_ID_PERSONAL*/" +
                    "  AND CREDIT_OPERATION.OBJECT_ID = CREDIT_ACC_REF_USER.OBJECT_ID " +
                    "  AND CREDIT_ACC_PERSONAL.OBJECT_ID = CREDIT_ACC_REF_USER.REFERENCE " +
                    "  AND CREDIT_ACC_NAME.ATTR_ID = 30 /*NAME*/" +
                    "  AND AMOUNT.ATTR_ID = 40 /*AMOUNT*/" +
                    "  AND DATES.ATTR_ID = 41 /*DATE*/" +
                    "  AND CREDIT_ACC_NAME.OBJECT_ID = CREDIT_ACC_PERSONAL.OBJECT_ID " +
                    "  AND AMOUNT.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID " +
                    "  AND DATES.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID ORDER BY OPERATION_ID";

    String GET_ALL_CREDIT_OPERATIONS_FAMILY =
            "SELECT CREDIT_ACC_FAMILY.OBJECT_ID AS CREDIT_ID_FAMILY, ACC_REF_USER.REFERENCE AS ID_USER, CREDIT_USER.VALUE AS USERNAME, " +
                    "                   CREDIT_ACC_NAME.VALUE AS CREDIT_ACC_NAME, CREDIT_OPERATION.OBJECT_ID AS OPERATION_ID, " +
                    "                   CREDIT_OPERATION.NAME AS OPERATION_NAME, AMOUNT.VALUE AS AMOUNT, DATES.DATE_VALUE AS DATE_VALUE " +
                    "                FROM OBJECTS CREDIT_ACC_FAMILY, OBJECTS CREDIT_OPERATION, ATTRIBUTES CREDIT_ACC_NAME, " +
                    "                   ATTRIBUTES AMOUNT, ATTRIBUTES DATES, OBJREFERENCE ACC_REF_USER, OBJREFERENCE CREDIT_ACC_REF_FAMILY, " +
                    "                     ATTRIBUTES CREDIT_USER " +
                    "                WHERE CREDIT_ACC_FAMILY.OBJECT_ID = ? " +
                    "                      AND CREDIT_ACC_REF_FAMILY.ATTR_ID = 38 /*CREDIT_ACCOUNT_ID_FAMILY*/ " +
                    "                      AND ACC_REF_USER.ATTR_ID = 39 /*CREDIT_ACCOUNT_ID_USER*/ " +
                    "                      AND CREDIT_USER.ATTR_ID = 5 /*USER NAME*/ " +
                    "                      AND CREDIT_USER.OBJECT_ID = ACC_REF_USER.REFERENCE " +
                    "                      AND CREDIT_OPERATION.OBJECT_ID = CREDIT_ACC_REF_FAMILY.OBJECT_ID " +
                    "                      AND CREDIT_ACC_FAMILY.OBJECT_ID = CREDIT_ACC_REF_FAMILY.REFERENCE " +
                    "                      AND CREDIT_OPERATION.OBJECT_ID = ACC_REF_USER.OBJECT_ID " +
                    "                      AND CREDIT_ACC_NAME.ATTR_ID = 30 /*CREDIT NAME*/ " +
                    "                      AND AMOUNT.ATTR_ID = 40 /*AMOUNT*/ " +
                    "                      AND DATES.ATTR_ID = 41 /*DATE*/ " +
                    "                      AND CREDIT_ACC_NAME.OBJECT_ID = CREDIT_ACC_FAMILY.OBJECT_ID " +
                    "                      AND AMOUNT.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID " +
                    "                      AND DATES.OBJECT_ID = CREDIT_OPERATION.OBJECT_ID ORDER BY OPERATION_ID";
}
