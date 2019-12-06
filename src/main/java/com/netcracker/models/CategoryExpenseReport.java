package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CategoryExpenseReport {
    private BigInteger idExpenseReport;
    private BigDecimal amount;
    private CategoryExpense expense;
    private BigInteger userReference;
    private BigInteger personalReference;
    private BigInteger familyReference;

    public static class Builder {
        private  BigInteger idExpenseReport;
        private BigDecimal amount;
        private CategoryExpense expense;
        private BigInteger userReference;
        private BigInteger personalReference;
        private BigInteger familyReference;

        public Builder() {
        }

        public Builder idExpenseReport(BigInteger id){
            this.idExpenseReport = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder expense(CategoryExpense expense) {
            this.expense = expense;
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

        public CategoryExpenseReport build(){
            return new CategoryExpenseReport(this);
        }
    }

    private CategoryExpenseReport(Builder builder) {
        idExpenseReport = builder.idExpenseReport;
        amount = builder.amount;
        expense = builder.expense;
        userReference = builder.userReference;
        familyReference = builder.familyReference;
        personalReference = builder.personalReference;
    }

    public BigInteger getIdExpenseReport() {
        return idExpenseReport;
    }

    public void setIdExpenseReport(BigInteger idExpenseReport) {
        this.idExpenseReport = idExpenseReport;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CategoryExpense getExpense() {
        return expense;
    }

    public void setExpense(CategoryExpense expense) {
        this.expense = expense;
    }
}
