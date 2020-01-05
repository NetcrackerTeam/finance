package com.netcracker.models.enums;

import java.math.BigInteger;

public enum UserRole {
    USER (new BigInteger("45")),
    OWNER (new BigInteger("46")),
    PARTICIPANT (new BigInteger("47"));
    private BigInteger id ;

    UserRole(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
    public static UserRole getStatusByKey(BigInteger key) {
        for (UserRole u : values())
            if (u.getId().equals(key))
                return u;
        throw new IllegalArgumentException();
    }
}
