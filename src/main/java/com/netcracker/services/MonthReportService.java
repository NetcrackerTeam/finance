package com.netcracker.services;

import com.netcracker.models.MonthReport;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MonthReportService {


    void formMonthPersonalReportFromDb(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo);

    void formMonthFamilyReportFromDb(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo);

    Path convertToTxt(MonthReport monthReport);

    MonthReport getMonthPersonalReport(BigInteger id, LocalDateTime date, boolean isJob);

    MonthReport getMonthFamilyReport(BigInteger id, LocalDateTime date, boolean isJob);

    String convertToString(Path path);

    String MONTH_REPORT_FROM = "Month report from ";
    String MONTH_REPORT_TO = " to ";
    String DOTTED_LINE = "-------------------------------------------\n";
    String ACTUAL_BALANCE = "Actual balance";
    String TOTAL_EXPENSE = "Total expense";
    String TOTAL_INCOME = "Total income";
    String EXPENSES_BY_CATEGORIES = "Expenses by categories";
    String INCOMES_BY_CATEGORIES = "Incomes by categories";
    String NEW_LINE = "\n";
    String TAB_AND_LINE = " \t|\n";
    String DOUBLE_DOTS = ": ";
    String SPACE = " ";
    String UNDERLINE = "_";
    String TXT_FORMAT = ".txt";
    String HYPHEN = "-";
    String LOGO = "logo_blue_big.png";

}
