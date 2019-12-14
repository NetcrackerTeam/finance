package com.netcracker.exception;

import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.enums.ErrorVisibility;

public class CreditAccountException extends RuntimeException{
    private AbstractCreditAccount creditAccount;
    private ErrorVisibility errorVisibility;

    public CreditAccountException(String operation, AbstractCreditAccount creditAccount, ErrorVisibility errorVisibility) {
        this(operation, errorVisibility);
        this.creditAccount = creditAccount;
        this.errorVisibility = errorVisibility;
    }

    public CreditAccountException(String operation, ErrorVisibility errorVisibility) {
        super(operation);
    }

    public AbstractCreditAccount getCreditAccount() {
        return creditAccount;
    }

    public ErrorVisibility getErrorVisibility() {
        return errorVisibility;
    }
}