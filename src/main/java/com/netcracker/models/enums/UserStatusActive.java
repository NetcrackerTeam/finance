package com.netcracker.models.enums;

import java.math.BigInteger;

public enum UserStatusActive {
    YES (new BigInteger("0")),
    NO (new BigInteger("1"));
    private BigInteger id ;

    UserStatusActive(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
