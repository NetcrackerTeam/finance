package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class MonthReport {

    private final BigInteger id;
    private Long totalIncome;
    private Long totalExpense;
    private Long balance;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Collection<CategoryExpenseReport> categoryExpense;
    private Collection<CategoryIncomeReport> categoryIncome;
    public static  class Builder {
        private BigInteger id;
        private Long totalIncome;
        private Long totalExpense;
        private Long balance;
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

        public Builder totalIncome( Long val) {
            this.totalIncome = val;
            return this;
        }
        public Builder totalExpense( Long val) {
            this.totalExpense = val;
            return this;
        }
        public Builder balance( Long val) {
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

    public Long getTotalIncome() {
        return totalIncome;
    }

    public Long getTotalExpense() {
        return totalExpense;
    }

    public Long getBalance() {
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

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalExpense(Long totalExpense) {
        this.totalExpense = totalExpense;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }



    public void setCategoryExpense(Collection<CategoryExpenseReport> categoryExpense) {
        this.categoryExpense = categoryExpense;
    }

    public void setCategoryIncome(Collection<CategoryIncomeReport> categoryIncome) {
        this.categoryIncome = categoryIncome;
    }
}
