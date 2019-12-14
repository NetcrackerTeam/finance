package com.netcracker.exception;

import com.netcracker.models.FamilyDebitAccount;

public class FamilyDebitAccountException extends RuntimeException {
    private FamilyDebitAccount familyDebitAccount;
    public static final String ERROR_MESSAGE_FAMILY = "the family debit account doesn`t exist";
    public static final String ERROR_MESSAGE_FAMILY_STATUS = "The user is unactive";
    public static final String ERROR_MESSAGE_FAMILY_PARTICIPANTS = "the family debit account has participants";

    public FamilyDebitAccount getFamilyDebitAccount() {
        return this.familyDebitAccount;
    }

    public FamilyDebitAccountException() {
        super();
    }

    public FamilyDebitAccountException(String message) {
        super(message);
    }

    public FamilyDebitAccountException(String message, FamilyDebitAccount familyDebitAccount) {
        this(message);
        this.familyDebitAccount = familyDebitAccount;
    }
}
