package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

public class MonthReport {

    private final BigInteger id;
    private double totalIncome;
    private double totalExpense;
    private double balance;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Collection<CategoryExpenseReport> categoryExpense;
    private Collection<CategoryIncomeReport> categoryIncome;
    public static  class Builder {
        private BigInteger id;
        private double totalIncome;
        private double totalExpense;
        private double balance;
        private LocalDate dateFrom;
        private LocalDate dateTo;

        private Collection<CategoryExpenseReport> categoryExpense;
        private Collection<CategoryIncomeReport>  categoryIncome;

        public Builder() {
        }
        public Builder id(BigInteger val) {
            this.id = val;
            return this;
        }

        public Builder totalIncome(double val) {
            this.totalIncome = val;
            return this;
        }
        public Builder totalExpense(double val) {
            this.totalExpense = val;
            return this;
        }
        public Builder balance(double val) {
            this.balance = val;
            return this;
        }
        public Builder dateFrom( LocalDate val) {
            this.dateFrom = val;
            return this;
        }
        public Builder dateTo( LocalDate val) {
            this.dateTo = val;
            return this;
        }
        public Builder categoryExpense(Collection<CategoryExpenseReport> val) {
            this.categoryExpense = val;
            return this;
        }
        public Builder categoryIncome(Collection<CategoryIncomeReport> val) {
            this.categoryIncome = val;
            return this;
        }

        public MonthReport build() {
            return new MonthReport(this);
        }
    }

    private MonthReport(Builder builder) {
        id = builder.id;
        totalExpense = builder.totalExpense;
        totalIncome = builder.totalIncome;
        balance = builder.balance;
        dateFrom = builder.dateFrom;
        dateTo = builder.dateTo;
        categoryExpense = builder.categoryExpense;
        categoryIncome = builder.categoryIncome;
    }

    public BigInteger getId() {
        return id;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Collection<CategoryExpenseReport> getCategoryExpense() {
        return categoryExpense;
    }

    public Collection<CategoryIncomeReport> getCategoryIncome() {
        return categoryIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



    public void setCategoryExpense(Collection<CategoryExpenseReport> categoryExpense) {
        this.categoryExpense = categoryExpense;
    }

    public void setCategoryIncome(Collection<CategoryIncomeReport> categoryIncome) {
        this.categoryIncome = categoryIncome;
    }
}
