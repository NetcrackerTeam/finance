package com.netcracker.exception;

import com.netcracker.models.MonthReport;

public class PredictionException extends RuntimeException {
    private MonthReport monthReport;

    public PredictionException(String message, MonthReport monthReport) {
        super(message);
        this.monthReport = monthReport;
    }

    public PredictionException(String message) {
        super(message);
    }

    public MonthReport getMonthReport() {
        return monthReport;
    }
}
