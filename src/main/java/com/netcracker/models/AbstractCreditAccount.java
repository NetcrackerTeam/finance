package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public abstract class AbstractCreditAccount {
    private BigInteger creditId ;
    private String name;
    private Long amount;
    private Long paidAmount;
    private LocalDate date;
    private Long creditRate;
    private LocalDate dateTo;
    private  int monthDay;
    private boolean isPaid;
}
