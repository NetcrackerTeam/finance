package com.netcracker.models;

import org.decimal4j.util.DoubleRounder;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractDebitAccount {
    private BigInteger id;
    private double amount;
    private User owner;
    private String name;
    private List<AccountIncome> accountIncomesList;
    private List<AccountExpense> accountExpensesList;
    private double monthIncome;
    private double monthExpense;

    protected static abstract class BaseBuilder<T extends AbstractDebitAccount, B extends BaseBuilder> {
        protected T actualClass;
        protected B actualClassBuilder;
        protected abstract T getActual();
        protected abstract B getActualBuilder();

        public BaseBuilder() {
            this.actualClass = getActual();
            this.actualClassBuilder = getActualBuilder();
        }

        public B debitObjectName(String name) {
            actualClass.setObjectName(name);
            return actualClassBuilder;
        }


        public B debitId(BigInteger id) {
            actualClass.setId(id);
            return actualClassBuilder;
        }

        public B debitAmount(double amount) {
            actualClass.setAmount(DoubleRounder.round(amount, 2));
            return actualClassBuilder;
        }

        public B debitOwner(User owner) {
            actualClass.setOwner(owner);
            return actualClassBuilder;
        }

        public B debitAccountIncomesList(List<AccountIncome> list ) {
            actualClass.setAccountIncomesList(list);
            return actualClassBuilder;
        }
        public B debitAccountExpensesList(List<AccountExpense> list) {
            actualClass.setAccountExpensesList(list);
            return actualClassBuilder;
        }

        public B monthIncome(double monthIncome) {
            actualClass.setMonthIncome(monthIncome);
            return actualClassBuilder;
        }
        public B monthExpense(double monthExpense) {
            actualClass.setMonthIncome(monthExpense);
            return actualClassBuilder;
        }



        public T build() {
            return actualClass;
        }
    }


    public BigInteger getId() { return id; }

    public void setId(BigInteger id) { this.id = id; }

    public String getObjectName() { return name; }

    public void setObjectName(String name) { this.name = name; }

    public double getAmount() {
        amount = DoubleRounder.round(amount, 2);
        return amount;
    }

    public void setAmount(double amount) { this.amount = amount; }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }

    public List<AccountIncome> getAccountIncomesList() { return accountIncomesList; }

    public void setAccountIncomesList(List<AccountIncome> accountIncomesList) {
        this.accountIncomesList = accountIncomesList;
    }

    public List<AccountExpense> getAccountExpensesList() {
        return accountExpensesList;
    }

    public void setAccountExpensesList(List<AccountExpense> accountExpensesList) {
        this.accountExpensesList = accountExpensesList;
    }

    public double getMonthIncome() {
        monthIncome = DoubleRounder.round(monthIncome, 2);
        return monthIncome;
    }

    public void setMonthIncome(double monthIncome) {
        this.monthIncome = DoubleRounder.round(monthIncome, 2);
    }

    public double getMonthExpense() {
        monthExpense = DoubleRounder.round(monthExpense, 2);
        return monthExpense;
    }

    public void setMonthExpense(double monthExpense) {
        this.monthExpense = DoubleRounder.round(monthExpense, 2);
    }
}