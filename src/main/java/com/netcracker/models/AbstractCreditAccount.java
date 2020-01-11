package com.netcracker.models;

import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.utils.CreditUtils;

import java.math.BigInteger;
import java.time.LocalDate;

public abstract class AbstractCreditAccount {
    private BigInteger creditId;
    private String name;
    private double amount;
    private double paidAmount;
    private LocalDate date;
    private double creditRate;
    private LocalDate dateTo;
    private int monthDay;
    private Debt debt;
    private boolean isCommodity;
    private CreditStatusPaid isPaid;
    private double remainsToPay;
    private double monthPayment;
    private double totalCreditPayment;

    protected static abstract class BaseBuilder<T extends AbstractCreditAccount, B extends BaseBuilder> {
        protected T actualClass;
        protected B actualClassBuilder;

        protected abstract T getActual();

        protected abstract B getActualBuilder();

        protected BaseBuilder() {
            actualClass = getActual();
            actualClassBuilder = getActualBuilder();
        }

        public B name(String name) {
            actualClass.setName(name);
            return actualClassBuilder;
        }

        public B creditId(BigInteger creditId) {
            actualClass.setCreditId(creditId);
            return actualClassBuilder;
        }

        public B amount(double amount) {
            actualClass.setAmount(amount);
            return actualClassBuilder;
        }

        public B paidAmount(double paidAmount) {
            actualClass.setPaidAmount(paidAmount);
            return actualClassBuilder;
        }

        public B date(LocalDate date) {
            actualClass.setDate(date);
            return actualClassBuilder;
        }

        public B creditRate(double creditRate) {
            actualClass.setCreditRate(creditRate);
            return actualClassBuilder;
        }

        public B dateTo(LocalDate dateTo) {
            actualClass.setDateTo(dateTo);
            return actualClassBuilder;
        }

        public B monthDay(int monthDay) {
            actualClass.setMonthDay(monthDay);
            return actualClassBuilder;
        }

        public B debtCredit(Debt debt) {
            actualClass.setDebt(debt);
            return actualClassBuilder;
        }

        public B isPaid(CreditStatusPaid isPaid) {
            actualClass.setPaid(isPaid);
            return actualClassBuilder;
        }

        public B isCommodity(boolean isCommodity) {
            actualClass.setCommodity(isCommodity);
            return actualClassBuilder;
        }

        public B remainsToPay() {
            actualClass.setRemainsToPay();
            return actualClassBuilder;
        }

        public B monthPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
            actualClass.setMonthPayment(dateFrom, dateTo, amount, rate);
            return actualClassBuilder;
        }

        public B totalCreditPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
            actualClass.setTotalCreditPayment(dateFrom, dateTo, amount, rate);
            return actualClassBuilder;
        }

        public T build() {
            return actualClass;
        }
    }

    protected void setTotalCreditPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
        totalCreditPayment = CreditUtils.getTotalCreditPayment(getDate(), getDateTo(), getAmount(), getCreditRate());
    }

    public double getTotalCreditPayment() {
        return totalCreditPayment;
    }

    protected void setRemainsToPay() {
        remainsToPay = getTotalCreditPayment() - getPaidAmount();
    }

    public double getRemainsToPay() {
        return remainsToPay;
    }

    protected void setMonthPayment(LocalDate dateFrom, LocalDate dateTo, double amount, double rate) {
        monthPayment = CreditUtils.calculateMonthPayment(getDate(), getDateTo(), getAmount(), getCreditRate());
    }

    public double getMonthPayment() {
        return monthPayment;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setCreditId(BigInteger creditId) {
        this.creditId = creditId;
    }

    protected void setAmount(double amount) {
        this.amount = amount;
    }

    protected void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    protected void setDate(LocalDate date) {
        this.date = date;
    }

    protected void setCreditRate(double creditRate) {
        this.creditRate = creditRate;
    }

    protected void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    protected void setCommodity(boolean isCommodity) {
        this.isCommodity = isCommodity;
    }

    protected void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }

    protected void setPaid(CreditStatusPaid isPaid) {
        this.isPaid = isPaid;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public BigInteger getCreditId() {
        return creditId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCreditRate() {
        return creditRate;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public boolean isCommodity() {
        return isCommodity;
    }

    public CreditStatusPaid isPaid() {
        return isPaid;
    }



}
