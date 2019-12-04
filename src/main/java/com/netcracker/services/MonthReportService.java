package com.netcracker.services;

import java.time.LocalDate;

public interface MonthReportService {
    public void formMonthReportFromDb(LocalDate date);

    public void convertToTxt();

    public void getMonthReport();

}
