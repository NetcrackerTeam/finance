package com.netcracker.models.enums;

import java.math.BigInteger;

public enum EmailServiceTemplateCategory {
    DEACTIVATE(new BigInteger("1")),
    PERSONAL_DEBIT(new BigInteger("2")),
    FAMILY_DEBIT(new BigInteger("3")),
    REMINDER_PERSONAL_CREDIT(new BigInteger("4")),
    REMINDER_FAMILY_CREDIT(new BigInteger("5")),
    AUTO_PERSONAL_INCOME(new BigInteger("6")),
    AUTO_PERSONAL_EXPENSE(new BigInteger("7")),
    AUTO_FAMILY_INCOME(new BigInteger("8")),
    AUTO_FAMILY_EXPENSE(new BigInteger("9")),
    MONTH_REPORT(new BigInteger("10"));
    private BigInteger id;


    EmailServiceTemplateCategory(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }
}
