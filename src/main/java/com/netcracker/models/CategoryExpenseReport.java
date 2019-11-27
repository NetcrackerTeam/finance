package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CategoryExpenseReport {
    private BigInteger idExpenseReport;
    private BigDecimal amount;
    private CategoryExpense expense;

    public static class Builder {
        private  BigInteger idExpenseReport;
        private BigDecimal amount;
        private CategoryExpense expense;

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

        public CategoryExpenseReport build(){
            return new CategoryExpenseReport(this);
        }
    }

    private CategoryExpenseReport(Builder builder) {
        idExpenseReport = builder.idExpenseReport;
        amount = builder.amount;
        expense = builder.expense;
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
