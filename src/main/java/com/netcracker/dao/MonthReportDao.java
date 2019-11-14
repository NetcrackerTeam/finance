package com.netcracker.dao;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.PersonalDebitAccount;

public interface MonthReportDao {

    public void createPersonalMonthReport(PersonalDebitAccount personalDebitAccount);

    public void createFamilyMonthReport(FamilyDebitAccount familyDebitAccount);

    public void deletePersonalMonthReport();

    public void deleteFamilyMonthReport();

    public void getMonthReportByFamilyAccountId();

    public void getMonthReportByPersonalAccountId();

}
