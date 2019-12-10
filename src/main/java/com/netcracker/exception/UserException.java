package com.netcracker.exception;

import com.netcracker.models.User;

public class UserException extends RuntimeException {
    protected User user;

    public User getUser() {
        return this.user;
    }

    public UserException() {
        super();
    }

    public UserException(String operation) {
        super(operation);
    }

    public UserException(String operation, User user) {
        this(operation);
        this.user = user;
    }
}
