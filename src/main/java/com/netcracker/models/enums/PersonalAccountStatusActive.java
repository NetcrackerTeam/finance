package com.netcracker.models.enums;

import java.math.BigInteger;

public enum PersonalAccountStatusActive {
    YES (new BigInteger("43")),
    NO (new BigInteger("44"));
    private BigInteger id ;

    PersonalAccountStatusActive(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
