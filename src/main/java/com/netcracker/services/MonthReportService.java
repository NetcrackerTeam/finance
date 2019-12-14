package com.netcracker.services;

import com.netcracker.models.MonthReport;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.time.LocalDate;

public interface MonthReportService {


    void formMonthPersonalReportFromDb(BigInteger id, LocalDate date);

    void formMonthFamilyReportFromDb(BigInteger id, LocalDate date);

    FileOutputStream convertFamilyToTxt(MonthReport monthReport);

    FileOutputStream convertPersonalToTxt(MonthReport monthReport);

    MonthReport getMonthPersonalReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo);

    MonthReport getMonthFamilyReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo);

}
