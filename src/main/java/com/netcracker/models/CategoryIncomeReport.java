package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CategoryIncomeReport {
    private BigInteger idIncomeReport;
    private BigDecimal amount;
    private CategoryIncome income;
    private BigInteger userReference;
    private BigInteger familyReference;
    private BigInteger personalReference;
    public static class Builder {
        private  BigInteger idIncomeReport;
        private BigDecimal amount;
        private CategoryIncome income;
        private BigInteger userReference;
        private BigInteger familyReference;
        private BigInteger personalReference;

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

        public Builder income(CategoryIncome income) {
            this.income = income;
            return this;
        }
        public Builder userReference(BigInteger userReference) {
            this.userReference = userReference;
            return this;
        }
        public Builder familyReference(BigInteger familyReference) {
            this.familyReference = familyReference;
            return this;
        }
        public Builder personalReference(BigInteger personalReference) {
            this.personalReference = personalReference;
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
        userReference = builder.userReference;
        familyReference = builder.familyReference;
        personalReference = builder.personalReference;
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
