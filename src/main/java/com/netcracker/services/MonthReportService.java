package com.netcracker.services;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.models.MonthReport;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.time.LocalDate;

public interface MonthReportService {


    void formMonthPersonalReportFromDb(BigInteger id, LocalDate date);

    void formMonthFamilyReportFromDb(BigInteger id, LocalDate date);

    void convertToTxt();

    MonthReport getMonthPersonalReport(LocalDate date_from, LocalDate date_to);

    MonthReport getMonthFamilyReport(LocalDate date_from, LocalDate date_to);

}
