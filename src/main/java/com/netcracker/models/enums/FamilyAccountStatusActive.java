package com.netcracker.models.enums;

import java.math.BigInteger;

public enum FamilyAccountStatusActive {
    YES (new BigInteger("41")),
    NO (new BigInteger("42"));
    private BigInteger id ;

    FamilyAccountStatusActive(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public static FamilyAccountStatusActive getStatusByKey(BigInteger key) {
        for (FamilyAccountStatusActive c : values())
            if (c.getId().equals(key))
                return c;
        throw new IllegalArgumentException();
    }
}
