package com.netcracker.models.enums;

import java.math.BigInteger;

public enum CategoryExpense {
    NONE(new BigInteger("0")),
    FOOD(new BigInteger("1")),
    RESIDENTIAL(new BigInteger("2")),
    ENTERTAINMENT(new BigInteger("3")),
    TAXES(new BigInteger("4")),
    EDUCATION(new BigInteger("5")),
    MEDICINE(new BigInteger("6")),
    TRANSPORT(new BigInteger("7")),
    GIFTS(new BigInteger("8")),
    CHILDREN(new BigInteger("9")),
    SPORT(new BigInteger("10")),
    CLOTHES(new BigInteger("11")),
    CREDIT(new BigInteger("12")),
    OTHER(new BigInteger("13"));

    private BigInteger id;

    CategoryExpense(BigInteger bigIntegerId) {
        this.id = bigIntegerId;
    }

    public BigInteger getId() {
        return id;
    }

    public static CategoryExpense getNameByKey(BigInteger key) {
        for (CategoryExpense c : values())
            if (c.getId().equals(key))
                return c;
        throw new IllegalArgumentException();
    }
}
