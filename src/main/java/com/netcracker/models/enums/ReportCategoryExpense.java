package com.netcracker.models.enums;

import java.math.BigInteger;

public enum ReportCategoryExpense {
    DEFAULT(new BigInteger("0")),
    FOOD(new BigInteger("19")),
    RESIDENTIAL(new BigInteger("20")),
    ENTERTAINMENT(new BigInteger("21")),
    TAXES(new BigInteger("22")),
    EDUCATION(new BigInteger("23")),
    MEDICINE(new BigInteger("24")),
    TRANSPORT(new BigInteger("25")),
    GIFTS(new BigInteger("26")),
    CHILDREN(new BigInteger("27")),
    SPORT(new BigInteger("28")),
    CLOTHES(new BigInteger("29")),
    CREDIT(new BigInteger("30")),
    OTHER(new BigInteger("31"));

    private BigInteger id;

    ReportCategoryExpense(BigInteger bigIntegerId) {
        this.id = bigIntegerId;
    }

    public BigInteger getId() {
        return id;
    }

    public static ReportCategoryExpense getNameByKey(BigInteger key) {
        for (ReportCategoryExpense c : values())
            if (c.getId().equals(key))
                return c;
        throw new IllegalArgumentException();
    }
}
