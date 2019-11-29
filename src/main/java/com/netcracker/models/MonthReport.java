package com.netcracker.models;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MonthReport {

    private final BigInteger id;
    private Long totalIncome;
    private Long totalExpense;
    private Long balance;
    private LocalDate date_of;
    private List<CategoryExpenseReport> categoryExpense;
    private List<CategoryIncomeReport> categoryIncome;
    public static  class Builder {
        private BigInteger id;
        private Long totalIncome;
        private Long totalExpense;
        private Long balance;
        private LocalDate date_of;

        private List<CategoryExpenseReport> categoryExpense;
        private List<CategoryIncomeReport>  categoryIncome;

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
        public Builder date_of( LocalDate val) {
            this.date_of = val;
            return this;
        }
        public Builder categoryExpense(List<CategoryExpenseReport> val) {
            this.categoryExpense = val;
            return this;
        }
        public Builder categoryIncome( List<CategoryIncomeReport> val) {
            this.categoryIncome = val;
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

    public LocalDate getDate_of() {
        return date_of;
    }

    public void setDate_of(LocalDate date_of) {
        this.date_of = date_of;
    }




    public List<CategoryExpenseReport> getCategoryExpense() {
        return categoryExpense;
    }

    public List<CategoryIncomeReport> getCategoryIncome() {
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



    public void setCategoryExpense(List<CategoryExpenseReport> categoryExpense) {
        this.categoryExpense = categoryExpense;
    }

    public void setCategoryIncome(List<CategoryIncomeReport> categoryIncome) {
        this.categoryIncome = categoryIncome;
    }
}
