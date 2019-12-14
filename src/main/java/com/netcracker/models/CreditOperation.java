package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public class CreditOperation {
    private BigInteger creditOperationId;
    private Long amount;
    private LocalDate date;

    public CreditOperation(Long amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public BigInteger getCreditOperationId() {
        return creditOperationId;
    }

    public void setCreditOperationId(BigInteger creditOperationId) {
        this.creditOperationId = creditOperationId;
    }

    public Long getAmount() {
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
}
