package com.netcracker.dao;

import com.netcracker.models.Debt;

import java.math.BigInteger;
import java.util.Date;

public interface CreditDeptDao {

    /**
     * Get personal credit debt by personal credit id.
     *
     * @param id personal credit id
     * @return debt object
     */
    public Debt getPersonalDebtByCreditId(BigInteger id);

    /**
     * Get family credit debt by family credit id.
     *
     * @param id family credit id
     * @return debt object
     */
    public Debt getFamilyDebtByCreditId(BigInteger id);

    /**
     * Get personal debt by debt id.
     *
     * @param id personal debt id
     * @return debt object
     */
    public Debt getPersonalDebtById(BigInteger id);

    /**
     * Get family debt by debt id.
     *
     * @param id family debt id
     * @return debt object
     */
    public Debt getFamilyDebtById(BigInteger id);

    /**
     * Change starting debt date of personal credit.
     *
     * @param id   debt id
     * @param date new starting debt date
     */
    public void updatePersonalDebtDateFrom(BigInteger id, Date date);

    /**
     * Change starting debt date of family credit.
     *
     * @param id   debt id
     * @param date new starting debt date
     */
    public void updateFamilyDebtDateFrom(BigInteger id, Date date);

    /**
     * Change ending debt date of personal credit.
     *
     * @param id   debt id
     * @param date new ending debt date
     */
    public void updatePersonalDebtDateTo(BigInteger id, Date date);

    /**
     * Change ending debt date of family credit.
     *
     * @param id   debt id
     * @param date new ending debt date
     */
    public void updateFamilyDebtDateTo(BigInteger id, Date date);

    /**
     * Change debt total amount of personal credit.
     *
     * @param id     debt id
     * @param amount new debt amount
     */
    public void updatePersonalDebtAmount(BigInteger id, long amount);

    /**
     * Change debt total amount of family credit.
     *
     * @param id     debt id
     * @param amount new debt amount
     */
    public void updateFamilyDebtAmount(BigInteger id, long amount);

    /**
     * Create personal debt to personal credit. Needed when
     * create personal credit account.
     *
     * @param id personal credit id
     */
    public void createPersonalDebtByCreditId(BigInteger id);

    /**
     * Create family debt to personal credit. Needed when
     * create family credit account.
     *
     * @param id family credit id
     */
    public void createFamilyDebtByCreditId(BigInteger id);

    String SELECT_PERSONAL_DEBT_BY_CREDIT_ID_QUERY = "SELECT DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "  FROM OBJECTS DEBT\n" +
            "    LEFT JOIN OBJREFERENCE DEBT_REF ON DEBT.OBJECT_ID = DEBT_REF.OBJECT_ID\n" +
            "    LEFT JOIN OBJECTS DEBT ON DEBT_REF.OBJECT_ID = DEBT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "  WHERE DEBT_REF.REFERENCE = ? /*CREDIT ACCOUNT ID*/\n" +
            "    AND DEBT_REF.ATTR_ID = 42 /*REFERENCE ATTR BETWEEN DEBT AND CREDIT*/\n" +
            "    AND DEBT.OBJECT_TYPE_ID = 8 /*PERSONAL DEBT OBJECT TYPE ID*/\n" +
            "    AND DEBT_DATE_FROM_AT.ATTR_ID = 44 /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    AND DEBT_DATE_TO_AT.ATTR_ID = 45 /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    AND DEBT_AMOUNT_AT.ATTR_ID = 46 /*DEBT AMOUNT ATTRIBUTE*/";

    String SELECT_FAMILY_DEBT_BY_CREDIT_ID_QUERY = "SELECT DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "  FROM OBJECTS DEBT\n" +
            "    LEFT JOIN OBJREFERENCE DEBT_REF ON DEBT.OBJECT_ID = DEBT_REF.OBJECT_ID\n" +
            "    LEFT JOIN OBJECTS DEBT ON DEBT_REF.OBJECT_ID = DEBT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "  WHERE DEBT_REF.REFERENCE = ?/*CREDIT ACCOUNT ID*/\n" +
            "    AND DEBT_REF.ATTR_ID = 43 /*REFERENCE ATTR BETWEEN DEBT AND CREDIT*/\n" +
            "    AND DEBT.OBJECT_TYPE_ID = 19 /*PERSONAL DEBT OBJECT TYPE ID*/\n" +
            "    AND DEBT_DATE_FROM_AT.ATTR_ID = 44 /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    AND DEBT_DATE_TO_AT.ATTR_ID = 45 /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    AND DEBT_AMOUNT_AT.ATTR_ID = 46 /*DEBT AMOUNT ATTRIBUTE*/";

    String SELECT_DEBT_BY_ID_QUERY = "SELECT DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "  FROM OBJECTS DEBT\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "    LEFT JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "  WHERE DEBT.OBJECT_ID = ? /*DEBT ID*/\n" +
            "    AND DEBT_DATE_FROM_AT.ATTR_ID = 44 /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    AND DEBT_DATE_TO_AT.ATTR_ID = 45 /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    AND DEBT_AMOUNT_AT.ATTR_ID = 46 /*DEBT AMOUNT ATTRIBUTE*/";

    String UPDATE_DEBT_DATE_FROM_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET DATE_VALUE = ? /*NEW DATE FROM*/\n" +
            "    WHERE ATTR_ID = 44 /*DATE FROM ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ? /*DEBT ID*/";

    String UPDATE_DEBT_DATE_TO_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET DATE_VALUE = ? /*NEW DATE TO PAYED*/\n" +
            "    WHERE ATTR_ID = 45 /*DATE TO ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ? /*DEBT ID*/";

    String UPDATE_DEBT_AMOUNT_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET DATE_VALUE = ? /*NEW AMOUNT */\n" +
            "    WHERE ATTR_ID = 45 /*AMOUNT ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ? /*DEBT ID*/";

    String CREATE_PERSONAL_DEBT_BY_CREDIT_ID = "INSERT ALL\n" +
            "    INTO  OBJECTS (OBJECT_ID,OBJECT_TYPE_ID) VALUES (OBJECTS_ID_S.NEXTVAL,8)\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(44, OBJECTS_ID_S.CURRVAL) /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(45, OBJECTS_ID_S.CURRVAL) /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(46, OBJECTS_ID_S.CURRVAL, '0') /*DEBT AMOUNT ATTRIBUTE*/\n" +
            "    INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,OBJECTS_ID_S.CURRVAL, ?) /*REFERENCE BETWEEN DEBT AND CREDIT ACC*/\n" +
            "    SELECT * FROM DUAL";

    String CREATE_FAMILY_DEBT_BY_CREDIT_ID = "INSERT ALL\n" +
            "    INTO  OBJECTS (OBJECT_ID,OBJECT_TYPE_ID) VALUES (OBJECTS_ID_S.NEXTVAL,19)\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(44, OBJECTS_ID_S.CURRVAL) /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(45, OBJECTS_ID_S.CURRVAL) /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(46, OBJECTS_ID_S.CURRVAL, '0') /*DEBT AMOUNT ATTRIBUTE*/\n" +
            "    INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,OBJECTS_ID_S.CURRVAL, ?) /*REFERENCE BETWEEN DEBT AND CREDIT ACC*/\n" +
            "    SELECT * FROM DUAL";
}
