package com.netcracker.exception;

import com.netcracker.models.AbstractCreditAccount;

public class CreditAccountException extends RuntimeException{
    private AbstractCreditAccount creditAccount;

    public CreditAccountException(String operation, AbstractCreditAccount creditAccount) {
        this(operation);
        this.creditAccount = creditAccount;
    }

    public CreditAccountException(String operation) {
        super(operation);
    }

    public AbstractCreditAccount getCreditAccount() {
        return creditAccount;
    }
}