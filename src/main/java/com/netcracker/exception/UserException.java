package com.netcracker.exception;

import com.netcracker.models.User;

public class UserException extends RuntimeException {
    private User user;

    public User getUser() {
        return this.user;
    }

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, User user) {
        this(message);
        this.user = user;
    }
}
