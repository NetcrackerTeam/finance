package com.netcracker.exception;

import com.netcracker.models.MonthReport;

public class MonthReportException extends RuntimeException {
    private MonthReport monthReport;

    public MonthReportException(String message, MonthReport monthReport) {
        super(message);
        this.monthReport = monthReport;
    }

    public MonthReportException(String message) {
        super(message);
    }

    public MonthReport getMonthReport() {
        return monthReport;
    }
}
