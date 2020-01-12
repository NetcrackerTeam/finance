package com.netcracker.models.enums;

import org.springframework.lang.Nullable;

import java.math.BigInteger;

public enum CategoryIncome {
    NONE(new BigInteger("0")),
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

    @Nullable
    public static CategoryIncome getNameByKey(BigInteger key) {
        if(key == null){
            return null;
        }
        for (CategoryIncome c : values())
            if (c.getId().equals(key))
                return c;
        throw new IllegalArgumentException();
    }
}
