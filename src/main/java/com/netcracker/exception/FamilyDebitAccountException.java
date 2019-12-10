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

    public FamilyDebitAccountException(String operation) {
        super(operation);
    }

    public FamilyDebitAccountException(String operation, FamilyDebitAccount familyDebitAccount) {
        this(operation);
        this.familyDebitAccount = familyDebitAccount;
    }
}
