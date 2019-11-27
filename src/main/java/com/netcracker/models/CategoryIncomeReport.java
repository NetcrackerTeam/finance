package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CategoryIncomeReport {
    private BigInteger idIncomeReport;
    private BigDecimal amount;
    private CategoryIncome income;

    public static class Builder {
        private  BigInteger idIncomeReport;
        private BigDecimal amount;
        private CategoryIncome income;

        public Builder() {
        }

        public Builder idIncomeReport(BigInteger id){
            this.idIncomeReport = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder expense(CategoryIncome income) {
            this.income = income;
            return this;
        }

        public CategoryIncomeReport build(){
            return new CategoryIncomeReport(this);
        }
    }

    private CategoryIncomeReport(Builder builder) {
        idIncomeReport = builder.idIncomeReport;
        amount = builder.amount;
        income = builder.income;
    }

    public BigInteger getIdIncomeReport() {
        return idIncomeReport;
    }

    public void setIdIncomeReport(BigInteger idIncomeReport) {
        this.idIncomeReport = idIncomeReport;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CategoryIncome getIncome() {
        return income;
    }

    public void setIncome(CategoryIncome income) {
        this.income = income;
    }
}
