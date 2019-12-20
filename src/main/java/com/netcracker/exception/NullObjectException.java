package com.netcracker.exception;

import com.netcracker.services.utils.ExceptionMessages;

public class NullObjectException extends RuntimeException{
    public NullObjectException() {
        super(ExceptionMessages.NULL_OBJECT_ERROR);
    }
}
