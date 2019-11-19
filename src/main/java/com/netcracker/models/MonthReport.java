package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Map;

public class MonthReport {

    private final BigInteger id;
    private Long totalIncome;
    private Long totalExpense;
    private Long balance;
    private LocalDate date;
    private Map<CategoryExpense, Long> categoryExpenseLongMap;
    private Map<CategoryIncome, Long>  categoryIncomeLongMap;
    public static  class Builder {
        private final BigInteger id;
        private Long totalIncome;
        private Long totalExpense;
        private Long balance;
        private LocalDate date;
        private Map<CategoryExpense, Long> categoryExpenseLongMap;
        private Map<CategoryIncome, Long>  categoryIncomeLongMap;

        public Builder(BigInteger id) {
            this.id = id;
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
        public Builder date( LocalDate val) {
            this.date = val;
            return this;
        }
        public Builder categoryExpenseLongMap( Map<CategoryExpense,Long> val) {
            this.categoryExpenseLongMap = val;
            return this;
        }
        public Builder categoryIncomeLongMap( Map<CategoryIncome, Long> val) {
            this.categoryIncomeLongMap = val;
            return this;
        }

        public MonthReport build() {
            return new MonthReport(this);
        }
    }

    public MonthReport(Builder builder) {
        this.id = builder.id;
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

    public LocalDate getDate() {
        return date;
    }

    public Map<CategoryExpense, Long> getCategoryExpenseLongMap() {
        return categoryExpenseLongMap;
    }

    public Map<CategoryIncome, Long> getCategoryIncomeLongMap() {
        return categoryIncomeLongMap;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategoryExpenseLongMap(Map<CategoryExpense, Long> categoryExpenseLongMap) {
        this.categoryExpenseLongMap = categoryExpenseLongMap;
    }

    public void setCategoryIncomeLongMap(Map<CategoryIncome, Long> categoryIncomeLongMap) {
        this.categoryIncomeLongMap = categoryIncomeLongMap;
    }
}
