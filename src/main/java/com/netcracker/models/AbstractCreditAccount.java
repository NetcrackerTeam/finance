package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public abstract class AbstractCreditAccount {
    private    BigInteger creditId ;
    private String name;
    private Long amount;
    private Long paidAmount;
    private LocalDate date;
    private Long creditRate;
    private LocalDate dateTo;
    private  int monthDay;
    private Debt debt;
    private boolean isPaid;

    protected static abstract class BaseBuilder <T extends AbstractCreditAccount, B extends BaseBuilder> {
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

        public B amount(Long amount) {
            actualClass.setAmount(amount);
            return actualClassBuilder;
        }

        public B paidAmount(Long paidAmount) {
            actualClass.setPaidAmount(paidAmount);
            return actualClassBuilder;
        }

        public B date(LocalDate date) {
            actualClass.setDate(date);
            return actualClassBuilder;
        }

        public B creditRate(Long creditRate) {
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

        public B isPaid(boolean isPaid) {
            actualClass.setPaid(isPaid);
            return actualClassBuilder;
        }

        public T build() {
            return actualClass;
        }
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setCreditId(BigInteger creditId) {
        this.creditId = creditId;
    }

    protected void setAmount(Long amount) {
        this.amount = amount;
    }

    protected void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    protected void setDate(LocalDate date) {
        this.date = date;
    }

    protected void setCreditRate(Long creditRate) {
        this.creditRate = creditRate;
    }

    protected void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    protected void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }

    protected void setPaid (boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Debt getDebt() { return debt; }

    public void setDebt(Debt debt) { this.debt = debt; }

    public BigInteger getCreditId() {
        return creditId;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getCreditRate() {
        return creditRate;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public boolean isPaid() {
        return isPaid;
    }

}
