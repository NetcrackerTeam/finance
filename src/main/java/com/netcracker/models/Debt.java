package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public class Debt {
    private BigInteger debtId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double amountDebt;


    public static class Builder {
        private BigInteger debtId;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private double amountDebt;

        public Builder() {
        }

        public Builder debtId(BigInteger id) {
            this.debtId = id;
            return this;
        }

        public Builder dateFrom(LocalDate date) {
            this.dateFrom = date;
            return this;
        }

        public Builder dateTo(LocalDate date) {
            this.dateTo = date;
            return this;
        }

        public Builder amountDebt(double amountDebt) {
            this.amountDebt = amountDebt;
            return this;
        }

        public Debt build() {
            return new Debt(this);
        }
    }

    private Debt(Builder builder) {
        debtId = builder.debtId;
        dateFrom = builder.dateFrom;
        dateTo = builder.dateTo;
        amountDebt = builder.amountDebt;
    }

    public BigInteger getDebtId() {
        return debtId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public double getAmountDebt() {
        return amountDebt;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setAmountDebt(double amountDebt) {
        this.amountDebt = amountDebt;
    }
}
