package com.netcracker.services;

import com.netcracker.models.MonthReport;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;

public interface MonthReportService {


    void formMonthPersonalReportFromDb(BigInteger id, LocalDate date);

    void formMonthFamilyReportFromDb(BigInteger id, LocalDate date);

    Path convertToTxt(MonthReport monthReport);

    MonthReport getMonthPersonalReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo);

    MonthReport getMonthFamilyReport(BigInteger id, LocalDate dateFrom, LocalDate dateTo);

    String MONTH_REPORT_FROM = "Month report from ";
    String MONTH_REPORT_TO = " to ";
    String DOTTED_LINE = "-------------------------------------------\n";
    String ACTUAL_BALANCE = "Actual balance \t| ";
    String TOTAL_EXPENSE = "Total expense \t| ";
    String TOTAL_INCOME = "Total income \t| ";
    String EXPENSES_BY_CATEGORIES = "Expenses by categories\n";
    String INCOMES_BY_CATEGORIES = "\nIncomes by categories\n";
    String NEW_LINE = "\n";
    String TAB_AND_LINE = " \t|\n";
    String DOUBLE_DOTS = ": ";
    String SPACE = " ";
    String UNDERLINE = "_";
    String TXT_FORMAT = ".txt";

}
