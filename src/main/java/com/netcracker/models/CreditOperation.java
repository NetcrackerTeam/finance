package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public class CreditOperation {
    private BigInteger creditOperationId;
    private double amount;
    private LocalDate date;
    private String username;

    public CreditOperation(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public BigInteger getCreditOperationId() {
        return creditOperationId;
    }

    public void setCreditOperationId(BigInteger creditOperationId) {
        this.creditOperationId = creditOperationId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
