package com.netcracker.dao;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.MonthReport;
import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;

public interface MonthReportDao {

    void createPersonalMonthReport(MonthReport monthReport);

    void createFamilyMonthReport(MonthReport monthReport);

    void deletePersonalMonthReport(BigInteger id);

    void deleteFamilyMonthReport(BigInteger id);

    MonthReport getMonthReportByFamilyAccountId(BigInteger id);

    MonthReport getMonthReportByPersonalAccountId(BigInteger id);


    String CREATE_PERSONAL_MONTH_REPORT = "INSERT ALL\n" +
            "            INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID) VALUES (objects_id_s.nextval, 3)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(12, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(13, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(14, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(15, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(16, objects_id_s.currval, ?);";
    String CREATE_FAMILY_MONTH_REPORT = "INSERT ALL\n" +
            "            INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID) VALUES (objects_id_s.nextval, 14)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(12, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(13, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(14, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(15, objects_id_s.currval, ?)\n" +
            "\n" +
            "            INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(16, objects_id_s.currval, ?);";
    String DELETE_PERSONAL_MONTH_REPORT = "delete from OBJECTS where OBJECT_ID = ?"
            + "delete from ATTRIBUTES where OBJECT_ID = ?";
    String DELETE_FAMILY_MONTH_REPORT = "delete from OBJECTS where OBJECT_ID = ?"
            + "delete from ATTRIBUTES where OBJECT_ID = ?";
    String GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID = "select o.OBJECT_ID as month_report_id, totalincome.VALUE as income,\n" +
            "       totalexpense.VALUE as expense, balance.VALUE as balance,\n" +
            "       date_from.DATE_VALUE as date_from,\n" +
            "       date_to.DATE_VALUE as date_to\n" +
            "from  ATTRIBUTES totalincome, ATTRIBUTES totalexpense,\n" +
            "      ATTRIBUTES balance, ATTRIBUTES date_from, ATTRIBUTES date_to, OBJECTS o\n" +
            "\n" +
            "where o.OBJECT_ID = ? \n" +
            "  and totalincome.OBJECT_ID = o.OBJECT_ID and totalincome.ATTR_ID = 12 /*totalincome*/\n" +
            "  and totalexpense.OBJECT_ID = o.OBJECT_ID and totalexpense.ATTR_ID = 13 /*totalexpense*/\n" +
            "  and balance.OBJECT_ID = o.OBJECT_ID and balance.ATTR_ID = 14\n" + /*balance*/
            "  and date_from.OBJECT_ID = o.OBJECT_ID and  date_from.ATTR_ID = 15\n" +
            "  and date_to.OBJECT_ID = o.OBJECT_ID and date_to.ATTR_ID = 16";
    String GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID = "select o.OBJECT_ID as month_report_id, totalincome.VALUE as income,\n" +
            "       totalexpense.VALUE as expense, balance.VALUE as balance,\n" +
            "       date_from.DATE_VALUE as date_from,\n" +
            "       date_to.DATE_VALUE as date_to\n" +
            "from  ATTRIBUTES totalincome, ATTRIBUTES totalexpense,\n" +
            "      ATTRIBUTES balance, ATTRIBUTES date_from, ATTRIBUTES date_to, OBJECTS o\n" +
            "\n" +
            "where o.OBJECT_ID = ? /*?*/\n" +
            "  and totalincome.OBJECT_ID = o.OBJECT_ID and totalincome.ATTR_ID = 12 /*totalincome*/\n" +
            "  and totalexpense.OBJECT_ID = o.OBJECT_ID and totalexpense.ATTR_ID = 13 /*totalexpense*/\n" +
            "  and balance.OBJECT_ID = o.OBJECT_ID and balance.ATTR_ID = 14 /*balance*/\n" +
            "  and date_from.OBJECT_ID = o.OBJECT_ID and  date_from.ATTR_ID = 15 /*date_from*/\n" +
            "  and date_to.OBJECT_ID = o.OBJECT_ID and date_to.ATTR_ID = 16 /*date_to*/";

}
