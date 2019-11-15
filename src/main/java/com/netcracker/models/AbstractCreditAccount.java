package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public abstract   class AbstractCreditAccount {
    private  BigInteger creditId ;
    private String name;
    private Long amount;
    private Long paidAmount;
    private LocalDate date;
    private Long creditRate;
    private LocalDate dateTo;
    private  int monthDay;
    private boolean isPaid;

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

    public  class Builder{

    }

}
