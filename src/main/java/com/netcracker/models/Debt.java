package com.netcracker.models;

import java.math.BigInteger;
import java.util.Date;

public class Debt {
    private  BigInteger debtId;
    private Date dateFrom;
    private Date dateTo;
    private Long amountDebt;


    public static class Builder {
        private  BigInteger debtId;
        private Date dateFrom;
        private Date dateTo;
        private Long amountDebt;

        public Builder() {
        }

        public Builder debtId(BigInteger id){
            this.debtId = id;
            return this;
        }

        public Builder dateFrom(Date date) {
            this.dateFrom = date;
            return this;
        }

        public Builder dateTo(Date date) {
            this.dateTo = date;
            return this;
        }

        public Builder amountDebt(Long date) {
            this.amountDebt = date;
            return this;
        }
        public Debt build(){
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Long getAmountDebt() {
        return amountDebt;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setAmountDebt(Long amountDebt) {
        this.amountDebt = amountDebt;
    }
}
