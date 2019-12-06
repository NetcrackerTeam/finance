package com.netcracker.services.impl;

import com.netcracker.models.MonthReport;
import com.netcracker.services.MonthReportService;

import java.math.BigInteger;
import java.time.LocalDate;

public class MonthReportServiceImpl implements MonthReportService {

//    @Override
//    public void formMonthReportFromDb(LocalDate date) {
//
//    }

    @Override
    public void formMonthPersonalReportFromDb(BigInteger id, LocalDate date) {

    }

    @Override
    public void formMonthFamilyReportFromDb(BigInteger id, LocalDate date) {

    }

    @Override
    public void convertToTxt() {

    }

    @Override
    public MonthReport getMonthPersonalReport(LocalDate date_from, LocalDate date_to) {
        return null;
    }

    @Override
    public MonthReport getMonthFamilyReport(LocalDate date_from, LocalDate date_to) {
        return null;
    }

//    @Override
//    public void getMonthReport() {
//
//    }
}
