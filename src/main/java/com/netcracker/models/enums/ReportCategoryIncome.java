package com.netcracker.models.enums;

import java.math.BigInteger;

public enum ReportCategoryIncome {
    DEFAULT(new BigInteger("0")),
    SALARY(new BigInteger("32")),
    AWARD(new BigInteger("33")),
    PERSENTS(new BigInteger("34")),
    GIFTS(new BigInteger("35")),
    OTHER(new BigInteger("36"));

    private BigInteger id;

    ReportCategoryIncome(BigInteger bigIntegerId) {
        this.id = bigIntegerId;
    }

    public BigInteger getId() {
        return id;
    }

    public static ReportCategoryIncome getNameByKey(BigInteger key) {
        for (ReportCategoryIncome c : values())
            if (c.getId().equals(key))
                return c;
        throw new IllegalArgumentException();
    }
}
