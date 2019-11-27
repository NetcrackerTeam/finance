package com.netcracker.dao;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.MonthReport;
import com.netcracker.models.PersonalDebitAccount;

import java.math.BigInteger;

public interface MonthReportDao {

    void createPersonalMonthReport(PersonalDebitAccount personalDebitAccount);

    void createFamilyMonthReport(FamilyDebitAccount familyDebitAccount);

    void deletePersonalMonthReport(BigInteger id);

    void deleteFamilyMonthReport(BigInteger id);

    MonthReport getMonthReportByFamilyAccountId(BigInteger id);

    MonthReport getMonthReportByPersonalAccountId(BigInteger id);


    String CREATE_PERSONAL_MONTH_REPORT = "";
    String CREATE_FAMILY_MONTH_REPORT = "";
    String DELETE_PERSONAL_MONTH_REPORT = "";
    String DELETE_FAMILY_MONTH_REPORT = "";
    String GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID = "";
    String GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID = "";

}
