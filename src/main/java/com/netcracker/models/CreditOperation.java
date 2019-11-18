package com.netcracker.models;

import java.math.BigInteger;
import java.util.Date;

public class CreditOperation {
    private  BigInteger creditOperationId;
    private Long amount;
    private Date date;

    public CreditOperation(BigInteger creditOperationId, Long amount, Date date) {
        this.creditOperationId = creditOperationId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
