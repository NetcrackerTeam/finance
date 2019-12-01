package com.netcracker.models.enums;

import java.math.BigInteger;

public enum CategoryIncome {
    DEFAULT(new BigInteger("0")),
    SALARY(new BigInteger("14")),
    AWARD(new BigInteger("15")),
    PRESENTS(new BigInteger("16")),
    GIFTS(new BigInteger("17")),
    OTHER(new BigInteger("18"));

    private BigInteger id;

    CategoryIncome(BigInteger bigIntegerId) {
        this.id = bigIntegerId;
    }

    public BigInteger getId() {
        return id;
    }

    public static CategoryIncome getNameByKey(BigInteger key) {
        for (CategoryIncome c : values())
            if (c.getId().equals(key))
                return c;
        throw new IllegalArgumentException();
    }
}
