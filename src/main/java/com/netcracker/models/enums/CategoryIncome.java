package com.netcracker.models.enums;

import java.math.BigInteger;

public enum CategoryIncome {
    DEFAULT(new BigInteger("0")),
    SALARY(new BigInteger("1")),
    AWARD(new BigInteger("2")),
    PERSENTS(new BigInteger("3")),
    GIFTS(new BigInteger("4")),
    OTHER(new BigInteger("5"));

    private BigInteger id;

    CategoryIncome(BigInteger bigIntegerId) {
        this.id = bigIntegerId;
    }

    public BigInteger getId() {
        return id;
    }
}
