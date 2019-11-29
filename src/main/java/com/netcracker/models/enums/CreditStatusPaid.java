package com.netcracker.models.enums;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public enum CreditStatusPaid {
    YES (new BigInteger("37")),
    NO (new BigInteger("38"));
    private BigInteger id ;

    CreditStatusPaid(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public static CreditStatusPaid getStatusByKey(BigInteger key) {
        for (CreditStatusPaid c : values())
            if (c.getId().equals(key))
                return c;
            throw new IllegalArgumentException();
    }
}
