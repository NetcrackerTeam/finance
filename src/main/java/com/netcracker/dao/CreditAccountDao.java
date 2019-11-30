package com.netcracker.dao;

import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;

import java.math.BigInteger;
import java.util.List;

public interface CreditAccountDao {

    /**
     * Get personal credit account from database.
     *
     * @param id personal credit id
     * @return personal credit object
     */
    PersonalCreditAccount getPersonalCreditById(BigInteger id);

    /**
     * Get family credit account from database.
     *
     * @param id personal credit id
     * @return personal credit object
     */
    FamilyCreditAccount getFamilyCreditById(BigInteger id);

    /**
     * Get list of existing personal credits using personal
     * debit account id.
     *
     * @param id personal debit id
     * @return existing personal credits
     */
    List<PersonalCreditAccount> getAllPersonalCreditsByAccountId(BigInteger id);

    /**
     * Get list of existing family credits using family
     * debit account id.
     *
     * @param id family debit id
     * @return existing family credits
     */
    List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(BigInteger id);

    /**
     * Change personal credit payed money to new value.
     *
     * @param id     personal credit account id
     * @param amount new payed amount
     */
    void updatePersonalCreditPayment(BigInteger id, long amount);

    /**
     * Change family credit payed money to new value.
     *
     * @param id     family credit account id
     * @param amount new payed amount
     */
    void updateFamilyCreditPayment(BigInteger id, long amount);

    /**
     * Create new personal credit account to existing personal
     * debit account with data. Also adds to database debt
     * with null date from and date to and 0 amount.
     *
     * @param id            personal debit account id
     * @param creditAccount personal credit account object
     */
    void createPersonalCreditByDebitAccountId(BigInteger id, PersonalCreditAccount creditAccount);

    /**
     * Create new family credit account to existing family
     * debit account with data. Also adds to database debt
     * with null date from and date to and 0 amount.
     *
     * @param id            family debit account id
     * @param creditAccount family credit account object
     */
    void createFamilyCreditByDebitAccountId(BigInteger id, FamilyCreditAccount creditAccount);

    /**
     * Change if paid status in personal credit account.
     *
     * @param id         personal credit account id
     * @param statusPaid enum status value
     */
    void updateIsPaidStatusPersonalCredit(BigInteger id, CreditStatusPaid statusPaid);

    /**
     * Change if paid status in family credit account.
     *
     * @param id         family credit account id
     * @param statusPaid enum status value
     */
    void updateIsPaidStatusFamilyCredit(BigInteger id, CreditStatusPaid statusPaid);

    String SELECT_FAMILY_CREDIT_QUERY = "SELECT CRED.OBJECT_ID CREDIT_ID, NAME_AT.VALUE NAME, AMOUNT_AT.VALUE AMOUNT,\n" +
            "  PAID_AT.VALUE PAID, DATE_AT.DATE_VALUE DATE_CR,  RATE_AT.VALUE CREDIT_RATE, \n" +
            "  DATE_TO_AT.DATE_VALUE DATE_TO, MONTH_DAY_AT.VALUE MONTH_DAY, IS_PAID_AT.LIST_VALUE_ID IS_PAID,\n" +
            "  DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "    FROM OBJECTS CRED\n" +
            "        INNER JOIN ATTRIBUTES NAME_AT ON CRED.OBJECT_ID = NAME_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES AMOUNT_AT ON CRED.OBJECT_ID = AMOUNT_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES PAID_AT ON CRED.OBJECT_ID = PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_AT ON CRED.OBJECT_ID = DATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES RATE_AT ON CRED.OBJECT_ID = RATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_TO_AT ON CRED.OBJECT_ID = DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES MONTH_DAY_AT ON CRED.OBJECT_ID = MONTH_DAY_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES IS_PAID_AT ON CRED.OBJECT_ID = IS_PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN OBJREFERENCE DEBT_REF ON CRED.OBJECT_ID = DEBT_REF.REFERENCE\n" +
            "        INNER JOIN OBJECTS DEBT ON DEBT_REF.OBJECT_ID = DEBT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "    WHERE CRED.OBJECT_ID = ?/*CREDIT FAMILY ACCOUNT ID*/\n" +
            "        AND DEBT.OBJECT_TYPE_ID = 19/*DEBT FAMILY OBJECT TYPE ID*/\n" +
            "        AND DATE_AT.ATTR_ID = 29/*CREATION DATE ATTRIBUTE*/\n" +
            "        AND NAME_AT.ATTR_ID = 30/*NAME ATTRIBUTE*/\n" +
            "        AND AMOUNT_AT.ATTR_ID = 31/*CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND PAID_AT.ATTR_ID = 32/*PAID CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND RATE_AT.ATTR_ID = 33/*CREDIT RATE ATTRIBUTE*/\n" +
            "        AND DATE_TO_AT.ATTR_ID = 34/*CREDIT DATE TO ATTRIBUTE*/\n" +
            "        AND IS_PAID_AT.ATTR_ID = 35/*STATUS OF CREDIT ATTRIBUTE*/\n" +
            "        AND MONTH_DAY_AT.ATTR_ID = 36/*PAYMENT CREDIT DAY OF MONTH ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_FROM_AT.ATTR_ID = 44/*DEBT DATE FROM ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_TO_AT.ATTR_ID = 45/*DEBT DATE TO ATTRIBUTE*/\n" +
            "        AND DEBT_AMOUNT_AT.ATTR_ID = 46/*DEBT AMOUNT ATTRIBUTE*/";

    String SELECT_PERSONAL_CREDIT_QUERY = "SELECT CRED.OBJECT_ID CREDIT_ID, NAME_AT.VALUE NAME, AMOUNT_AT.VALUE AMOUNT,\n" +
            "  PAID_AT.VALUE PAID, DATE_AT.DATE_VALUE DATE_CR,  RATE_AT.VALUE CREDIT_RATE, \n" +
            "  DATE_TO_AT.DATE_VALUE DATE_TO, MONTH_DAY_AT.VALUE MONTH_DAY, IS_PAID_AT.LIST_VALUE_ID IS_PAID,\n" +
            "  DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "    FROM OBJECTS CRED\n" +
            "        INNER JOIN ATTRIBUTES NAME_AT ON CRED.OBJECT_ID = NAME_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES AMOUNT_AT ON CRED.OBJECT_ID = AMOUNT_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES PAID_AT ON CRED.OBJECT_ID = PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_AT ON CRED.OBJECT_ID = DATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES RATE_AT ON CRED.OBJECT_ID = RATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_TO_AT ON CRED.OBJECT_ID = DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES MONTH_DAY_AT ON CRED.OBJECT_ID = MONTH_DAY_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES IS_PAID_AT ON CRED.OBJECT_ID = IS_PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN OBJREFERENCE DEBT_REF ON CRED.OBJECT_ID = DEBT_REF.REFERENCE\n" +
            "        INNER JOIN OBJECTS DEBT ON DEBT_REF.OBJECT_ID = DEBT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "    WHERE CRED.OBJECT_ID = ?/*CREDIT PERSONAL ACCOUNT ID*/\n" +
            "        AND DEBT.OBJECT_TYPE_ID = 8/*DEBT PERSONAL OBJECT TYPE ID*/\n" +
            "        AND DATE_AT.ATTR_ID = 29/*CREATION DATE ATTRIBUTE*/\n" +
            "        AND NAME_AT.ATTR_ID = 30/*NAME ATTRIBUTE*/\n" +
            "        AND AMOUNT_AT.ATTR_ID = 31/*CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND PAID_AT.ATTR_ID = 32/*PAID CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND RATE_AT.ATTR_ID = 33/*CREDIT RATE ATTRIBUTE*/\n" +
            "        AND DATE_TO_AT.ATTR_ID = 34/*CREDIT DATE TO ATTRIBUTE*/\n" +
            "        AND IS_PAID_AT.ATTR_ID = 35/*STATUS OF CREDIT ATTRIBUTE*/\n" +
            "        AND MONTH_DAY_AT.ATTR_ID = 36/*PAYMENT CREDIT DAY OF MONTH ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_FROM_AT.ATTR_ID = 44/*DEBT DATE FROM ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_TO_AT.ATTR_ID = 45/*DEBT DATE TO ATTRIBUTE*/\n" +
            "        AND DEBT_AMOUNT_AT.ATTR_ID = 46/*DEBT AMOUNT ATTRIBUTE*/";

    String SELECT_PERSONAL_CREDITS_BY_ACCOUNT_QUERY = "SELECT CRED.OBJECT_ID CREDIT_ID, NAME_AT.VALUE NAME, AMOUNT_AT.VALUE AMOUNT,\n" +
            "  PAID_AT.VALUE PAID, DATE_AT.DATE_VALUE DATE_CR,  RATE_AT.VALUE CREDIT_RATE, \n" +
            "  DATE_TO_AT.DATE_VALUE DATE_TO, MONTH_DAY_AT.VALUE MONTH_DAY, IS_PAID_AT.LIST_VALUE_ID IS_PAID,\n" +
            "  DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "    FROM OBJECTS CRED\n" +
            "        INNER JOIN OBJREFERENCE REFER_ACC ON CRED.OBJECT_ID = REFER_ACC.OBJECT_ID\n" +
            "        INNER JOIN OBJECTS ACC_OBJ ON REFER_ACC.REFERENCE = ACC_OBJ.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES NAME_AT ON CRED.OBJECT_ID = NAME_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES AMOUNT_AT ON CRED.OBJECT_ID = AMOUNT_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES PAID_AT ON CRED.OBJECT_ID = PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_AT ON CRED.OBJECT_ID = DATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES RATE_AT ON CRED.OBJECT_ID = RATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_TO_AT ON CRED.OBJECT_ID = DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES MONTH_DAY_AT ON CRED.OBJECT_ID = MONTH_DAY_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES IS_PAID_AT ON CRED.OBJECT_ID = IS_PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN OBJREFERENCE DEBT_REF ON CRED.OBJECT_ID = DEBT_REF.REFERENCE\n" +
            "        INNER JOIN OBJECTS DEBT ON DEBT_REF.OBJECT_ID = DEBT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "    WHERE REFER_ACC.REFERENCE = ?/*PERSONAL DEBIT ACCOUNT ID*/\n" +
            "        AND ACC_OBJ.OBJECT_TYPE_ID = 2/*PERSONAL ACCOUNT OBJECT TYPE ID*/\n" +
            "        AND REFER_ACC.ATTR_ID = 27/*ATTRIBUTE RELATION PER CREDIT AND DEBIT ACC*/\n" +
            "        AND DEBT.OBJECT_TYPE_ID = 8/*PERSONAL DEBT OBJECT TYPE ID*/\n" +
            "        AND DATE_AT.ATTR_ID = 29/*CREATION DATE ATTRIBUTE*/\n" +
            "        AND NAME_AT.ATTR_ID = 30/*NAME ATTRIBUTE*/\n" +
            "        AND AMOUNT_AT.ATTR_ID = 31/*CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND PAID_AT.ATTR_ID = 32/*PAID CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND RATE_AT.ATTR_ID = 33/*CREDIT RATE ATTRIBUTE*/\n" +
            "        AND DATE_TO_AT.ATTR_ID = 34/*CREDIT DATE TO ATTRIBUTE*/\n" +
            "        AND IS_PAID_AT.ATTR_ID = 35/*STATUS OF CREDIT ATTRIBUTE*/\n" +
            "        AND MONTH_DAY_AT.ATTR_ID = 36/*PAYMENT CREDIT DAY OF MONTH ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_FROM_AT.ATTR_ID = 44/*DEBT DATE FROM ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_TO_AT.ATTR_ID = 45/*DEBT DATE TO ATTRIBUTE*/\n" +
            "        AND DEBT_AMOUNT_AT.ATTR_ID = 46/*DEBT AMOUNT ATTRIBUTE*/";

    String SELECT_FAMILY_CREDITS_BY_ACCOUNT_QUERY = "SELECT CRED.OBJECT_ID CREDIT_ID, NAME_AT.VALUE NAME, AMOUNT_AT.VALUE AMOUNT,\n" +
            "  PAID_AT.VALUE PAID, DATE_AT.DATE_VALUE DATE_CR,  RATE_AT.VALUE CREDIT_RATE, \n" +
            "  DATE_TO_AT.DATE_VALUE DATE_TO, MONTH_DAY_AT.VALUE MONTH_DAY, IS_PAID_AT.LIST_VALUE_ID IS_PAID,\n" +
            "  DEBT_DATE_FROM_AT.DATE_VALUE DEBT_FROM, DEBT_DATE_TO_AT.DATE_VALUE DEBT_TO,\n" +
            "  DEBT_AMOUNT_AT.VALUE DEBT_AMOUNT, DEBT.OBJECT_ID DEBT_ID\n" +
            "    FROM OBJECTS CRED\n" +
            "        INNER JOIN OBJREFERENCE REFER_ACC ON CRED.OBJECT_ID = REFER_ACC.OBJECT_ID\n" +
            "        INNER JOIN OBJECTS ACC_OBJ ON REFER_ACC.REFERENCE = ACC_OBJ.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES NAME_AT ON CRED.OBJECT_ID = NAME_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES AMOUNT_AT ON CRED.OBJECT_ID = AMOUNT_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES PAID_AT ON CRED.OBJECT_ID = PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_AT ON CRED.OBJECT_ID = DATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES RATE_AT ON CRED.OBJECT_ID = RATE_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DATE_TO_AT ON CRED.OBJECT_ID = DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES MONTH_DAY_AT ON CRED.OBJECT_ID = MONTH_DAY_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES IS_PAID_AT ON CRED.OBJECT_ID = IS_PAID_AT.OBJECT_ID\n" +
            "        INNER JOIN OBJREFERENCE DEBT_REF ON CRED.OBJECT_ID = DEBT_REF.REFERENCE\n" +
            "        INNER JOIN OBJECTS DEBT ON DEBT_REF.OBJECT_ID = DEBT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_FROM_AT ON DEBT.OBJECT_ID = DEBT_DATE_FROM_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_DATE_TO_AT ON DEBT.OBJECT_ID = DEBT_DATE_TO_AT.OBJECT_ID\n" +
            "        INNER JOIN ATTRIBUTES DEBT_AMOUNT_AT ON DEBT.OBJECT_ID = DEBT_AMOUNT_AT.OBJECT_ID\n" +
            "    WHERE REFER_ACC.REFERENCE = ?/*FAMILY DEBIT ACCOUNT ID*/\n" +
            "        AND ACC_OBJ.OBJECT_TYPE_ID = 13/*FAMILY ACCOUNT OBJECT TYPE ID*/\n" +
            "        AND REFER_ACC.ATTR_ID = 28/*ATTRIBUTE RELATION FAM CREDIT AND DEBIT ACC*/\n" +
            "        AND DEBT.OBJECT_TYPE_ID = 19/*FAMILY DEBT OBJECT TYPE ID*/\n" +
            "        AND DATE_AT.ATTR_ID = 29/*CREATION DATE ATTRIBUTE*/\n" +
            "        AND NAME_AT.ATTR_ID = 30/*NAME ATTRIBUTE*/\n" +
            "        AND AMOUNT_AT.ATTR_ID = 31/*CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND PAID_AT.ATTR_ID = 32/*PAID CREDIT AMOUNT ATTRIBUTE*/\n" +
            "        AND RATE_AT.ATTR_ID = 33/*CREDIT RATE ATTRIBUTE*/\n" +
            "        AND DATE_TO_AT.ATTR_ID = 34/*CREDIT DATE TO ATTRIBUTE*/\n" +
            "        AND IS_PAID_AT.ATTR_ID = 35/*STATUS OF CREDIT ATTRIBUTE*/\n" +
            "        AND MONTH_DAY_AT.ATTR_ID = 36/*PAYMENT CREDIT DAY OF MONTH ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_FROM_AT.ATTR_ID = 44/*DEBT DATE FROM ATTRIBUTE*/\n" +
            "        AND DEBT_DATE_TO_AT.ATTR_ID = 45/*DEBT DATE TO ATTRIBUTE*/\n" +
            "        AND DEBT_AMOUNT_AT.ATTR_ID = 46/*DEBT AMOUNT ATTRIBUTE*/";

    String UPDATE_CREDIT_PAYMENT_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET VALUE = ?/*NEW CREDIT PAYED AMOUNT */\n" +
            "    WHERE ATTR_ID = 32/*PAID AMOUNT ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ?/*CREDIT ACCOUNT ID*/";

    String CREATE_PERSONAL_CREDIT_QUERY = "INSERT ALL\n" +
            "     INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID, NAME) VALUES (OBJECTS_ID_S.NEXTVAL,NULL,6, ?)\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(29, OBJECTS_ID_S.CURRVAL, ?) /*CREATION DATE ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(30, OBJECTS_ID_S.CURRVAL, ?) /*NAME ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(31, OBJECTS_ID_S.CURRVAL, ?) /*CREDIT AMOUNT ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(32, OBJECTS_ID_S.CURRVAL, ?) /*PAID CREDIT AMOUNT ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(33, OBJECTS_ID_S.CURRVAL, ?) /*CREDIT RATE ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(34, OBJECTS_ID_S.CURRVAL, ?) /*CREDIT DATE TO ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(35, OBJECTS_ID_S.CURRVAL, ?) /*STATUS OF CREDIT ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(36, OBJECTS_ID_S.CURRVAL, ?) /*PAYMENT CREDIT DAY OF MONTH ATTRIBUTE*/\n" +
            "     INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,OBJECTS_ID_S.CURRVAL,?)/*ATTRIBUTE RELATION PER CREDIT AND DEBIT ACC*/\n" +
            "    SELECT * FROM DUAL";

    String CREATE_FAMILY_CREDIT_QUERY = "INSERT ALL\n" +
            "     INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID, NAME) VALUES (OBJECTS_ID_S.NEXTVAL,NULL,17, ?) /*17 OBJECT TYPE FAMILY CREDIT ACC*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(29, OBJECTS_ID_S.CURRVAL, ?) /*CREATION DATE ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(30, OBJECTS_ID_S.CURRVAL, ?) /*NAME ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(31, OBJECTS_ID_S.CURRVAL, ?) /*CREDIT AMOUNT ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(32, OBJECTS_ID_S.CURRVAL, ?) /*PAID CREDIT AMOUNT ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(33, OBJECTS_ID_S.CURRVAL, ?) /*CREDIT RATE ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(34, OBJECTS_ID_S.CURRVAL, ?) /*CREDIT DATE TO ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(35, OBJECTS_ID_S.CURRVAL, ?) /*STATUS OF CREDIT ATTRIBUTE*/\n" +
            "     INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(36, OBJECTS_ID_S.CURRVAL, ?) /*PAYMENT CREDIT DAY OF MONTH ATTRIBUTE*/\n" +
            "    INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,OBJECTS_ID_S.CURRVAL,?)/*ATTRIBUTE RELATION PER CREDIT AND DEBIT ACC*/\n" +
            "    SELECT * FROM DUAL";

    String UPDATE_ISPAID_STATUS_CREDIT_QUERY = "UPDATE ATTRIBUTES\n" +
            "    SET LIST_VALUE_ID = ?/*NEW STATUS */\n" +
            "    WHERE ATTR_ID = 35/*CREDIT STATUS ATTRIBUTE ID*/\n" +
            "    AND OBJECT_ID = ?/*DEBT ID*/\n";

    String CREATE_PERSONAL_DEBT_BY_CREDIT_ID = "INSERT ALL\n" +
            "    INTO  OBJECTS (OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL,8,'DEBT')\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(44, OBJECTS_ID_S.CURRVAL) /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(45, OBJECTS_ID_S.CURRVAL) /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(46, OBJECTS_ID_S.CURRVAL, '0') /*DEBT AMOUNT ATTRIBUTE*/\n" +
            "    INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,OBJECTS_ID_S.CURRVAL, ?) /*REFERENCE BETWEEN DEBT AND CREDIT ACC*/\n" +
            "    SELECT * FROM DUAL";

    String CREATE_FAMILY_DEBT_BY_CREDIT_ID = "INSERT ALL\n" +
            "    INTO  OBJECTS (OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (OBJECTS_ID_S.NEXTVAL,19,'DEBT')\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(44, OBJECTS_ID_S.CURRVAL) /*DEBT DATE FROM ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID) VALUES(45, OBJECTS_ID_S.CURRVAL) /*DEBT DATE TO ATTRIBUTE*/\n" +
            "    INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(46, OBJECTS_ID_S.CURRVAL, '0') /*DEBT AMOUNT ATTRIBUTE*/\n" +
            "    INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,OBJECTS_ID_S.CURRVAL, ?) /*REFERENCE BETWEEN DEBT AND CREDIT ACC*/\n" +
            "    SELECT * FROM DUAL";

    String SELECT_CREDIT_ID_BY_NAME = "SELECT OBJECT_ID FROM OBJECTS WHERE NAME = ?";
}
