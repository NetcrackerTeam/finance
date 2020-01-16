package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDateTime;

public abstract class AbstractAccountOperation {
    private BigInteger id;
    private double amount;
    private LocalDateTime date;
    private Debt debt;
    private BigInteger userId;
    private BigInteger debitId;

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

        public B accountAmount(double amount) {
            actualClass.setAmount(amount);
            return actualClassBuilder;
        }

        public B accountDate(LocalDateTime date) {
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

        public B accountDebitId(BigInteger debitId) {
            actualClass.setDebitId(debitId);
            return actualClassBuilder;
        }

        public T build() {
            return actualClass;
        }

    }

    public BigInteger getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Debt getDebt() {
        return debt;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigInteger getDebitId() {
        return debitId;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setDebitId(BigInteger debitId) {
        this.debitId = debitId;
    }
}
