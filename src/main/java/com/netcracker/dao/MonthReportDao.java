package com.netcracker.dao;

import com.netcracker.models.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface MonthReportDao {

    void createPersonalMonthReport(MonthReport monthReport);

    void createFamilyMonthReport(MonthReport monthReport);

    void deletePersonalMonthReport(BigInteger id);

    void deleteFamilyMonthReport(BigInteger id);

    MonthReport getMonthReportByFamilyAccountId(BigInteger id);

    MonthReport getMonthReportByPersonalAccountId(BigInteger id);


    String CREATE_PERSONAL_MONTH_REPORT = "INSERT ALL\n" +
            "         INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.nextval, null,3, 'test', 'family report')\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(12, OBJECTS_ID_S.currval, ?)\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(13, OBJECTS_ID_S.currval, ?)\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(14, OBJECTS_ID_S.currval, ?)\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(15, OBJECTS_ID_S.currval, TO_DATE(TO_CHAR(?), 'dd-mm-yyyy'))\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(16, OBJECTS_ID_S.currval,TO_DATE(TO_CHAR(), 'dd-mm-yyyy'))\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,OBJECTS_ID_S.currval,?)\n" +
            "         SELECT * FROM DUAL;";
    String CREATE_FAMILY_MONTH_REPORT = "INSERT ALL\n" +
            "         INTO OBJECTS(OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.nextval, null,14, 'test', 'family report')\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(12, OBJECTS_ID_S.currval, ?)\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(13, OBJECTS_ID_S.currval, ?)\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(14, OBJECTS_ID_S.currval, ?)\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(15, OBJECTS_ID_S.currval, TO_DATE(TO_CHAR(?), 'dd-mm-yyyy'))\n" +
            "\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(16, OBJECTS_ID_S.currval,TO_DATE(TO_CHAR(?), 'dd-mm-yyyy'))\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,OBJECTS_ID_S.currval,3)\n" +
            "         SELECT * FROM DUAL;";
    String DELETE_PERSONAL_MONTH_REPORT = "delete from OBJECTS where OBJECT_ID = ?";

    String DELETE_FAMILY_MONTH_REPORT = "delete from OBJECTS where OBJECT_ID = ?";

    String GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS MONTH_REPORT_ID, TOTALINCOME.VALUE AS INCOME,\n"+
            "                   TOTALEXPENSE.VALUE AS EXPENSE, BALANCE.VALUE AS BALANCE,\n"+
            "                   DATE_FROM.DATE_VALUE AS DATE_FROM,\n"+
            "                  DATE_TO.DATE_VALUE AS DATE_TO\n"+
            "                  ATTRIBUTES TOTALINCOME, ATTRIBUTES TOTALEXPENSE,\n"+
            "                  ATTRIBUTES BALANCE, ATTRIBUTES DATE_FROM, ATTRIBUTES DATE_TO, OBJECTS O\n"+
            "            \n"+
            "            WHERE O.OBJECT_ID = ? \n"+
            "              AND TOTALINCOME.OBJECT_ID = O.OBJECT_ID AND TOTALINCOME.ATTR_ID = 12 /*TOTALINCOME*/\n"+
            "              AND TOTALEXPENSE.OBJECT_ID = O.OBJECT_ID AND TOTALEXPENSE.ATTR_ID = 13 /*TOTALEXPENSE*/\n"+
            "              AND BALANCE.OBJECT_ID = O.OBJECT_ID AND BALANCE.ATTR_ID = 14 /*BALANCE*/\n"+
            "              AND DATE_FROM.OBJECT_ID = O.OBJECT_ID AND  DATE_FROM.ATTR_ID = 15\n"+
            "              AND DATE_TO.OBJECT_ID = O.OBJECT_ID AND DATE_TO.ATTR_ID = 16;";

    String GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID = "SELECT O.OBJECT_ID AS MONTH_REPORT_ID, TOTALINCOME.VALUE AS INCOME,\n"+
            "                   TOTALEXPENSE.VALUE AS EXPENSE, BALANCE.VALUE AS BALANCE,\n"+
            "                   DATE_FROM.DATE_VALUE AS DATE_FROM,\n"+
            "                  DATE_TO.DATE_VALUE AS DATE_TO\n"+
            "                  ATTRIBUTES TOTALINCOME, ATTRIBUTES TOTALEXPENSE,\n"+
            "                  ATTRIBUTES BALANCE, ATTRIBUTES DATE_FROM, ATTRIBUTES DATE_TO, OBJECTS O\n"+
            "            \n"+
            "            WHERE O.OBJECT_ID = ? \n"+
            "              AND TOTALINCOME.OBJECT_ID = O.OBJECT_ID AND TOTALINCOME.ATTR_ID = 12 /*TOTALINCOME*/\n"+
            "              AND TOTALEXPENSE.OBJECT_ID = O.OBJECT_ID AND TOTALEXPENSE.ATTR_ID = 13 /*TOTALEXPENSE*/\n"+
            "              AND BALANCE.OBJECT_ID = O.OBJECT_ID AND BALANCE.ATTR_ID = 14 /*BALANCE*/\n"+
            "              AND DATE_FROM.OBJECT_ID = O.OBJECT_ID AND  DATE_FROM.ATTR_ID = 15\n"+
            "              AND DATE_TO.OBJECT_ID = O.OBJECT_ID AND DATE_TO.ATTR_ID = 16;";

}
