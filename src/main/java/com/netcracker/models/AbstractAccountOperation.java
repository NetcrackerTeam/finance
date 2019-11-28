package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public abstract class AbstractAccountOperation {
    private BigInteger id;
    private BigInteger amount;
    private LocalDate date;
    private Debt debt;
    private BigInteger userId;

    protected static abstract class BaseBuilder<T extends AbstractAccountOperation, B extends BaseBuilder> {
        protected T actualClass;
        protected B actualClassBuilder;

        protected abstract T getActual();

        protected abstract B getActualBuilder();

        protected BaseBuilder() {
            actualClass = getActual();
            actualClassBuilder = getActualBuilder();
        }


        public B accountId(BigInteger id) {
            actualClass.setId(id);
            return actualClassBuilder;
        }

        public B accountAmount(BigInteger amount) {
            actualClass.setAmount(amount);
            return actualClassBuilder;
        }

        public B accountDate(LocalDate date) {
            actualClass.setDate(date);
            return actualClassBuilder;
        }

        public B accountDebt(Debt debt) {
            actualClass.setDebt(debt);
            return actualClassBuilder;
        }


        public B accountUserId(BigInteger id) {
            actualClass.setUserId(id);
            return actualClassBuilder;
        }

        public T build() {
            return actualClass;
        }

    }


    public BigInteger getId() {
        return id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Debt getDebt() {
        return debt;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}
