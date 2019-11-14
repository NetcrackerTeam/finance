package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;

public abstract class AbstractAccountOperation {
    private BigInteger amount;
    private LocalDate date;
    private Debt debt;
    private Long userId;
}
