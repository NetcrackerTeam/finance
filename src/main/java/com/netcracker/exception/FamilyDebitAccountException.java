package com.netcracker.exception;

import com.netcracker.models.FamilyDebitAccount;

public class FamilyDebitAccountException extends RuntimeException {
    protected FamilyDebitAccount familyDebitAccount;

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
