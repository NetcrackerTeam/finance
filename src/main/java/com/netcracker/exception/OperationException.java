package com.netcracker.exception;

import com.netcracker.models.AbstractAccountOperation;

public class OperationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Operation is not valid: ";
    private AbstractAccountOperation accountOperation;

    public OperationException() {
        super();
    }

    public AbstractAccountOperation getAccountOperation() {
        return accountOperation;
    }

    public OperationException(String message) {
        super(DEFAULT_MESSAGE + (message == null ? "" : " " + message));
    }

    public OperationException(String message, AbstractAccountOperation accountOperation) {
        this(message);
        this.accountOperation = accountOperation;
    }
}
