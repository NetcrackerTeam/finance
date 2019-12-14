package com.netcracker.exception;

import com.netcracker.models.User;

public class UserException extends RuntimeException {
    private User user;
    public static final String  ERROR_MESSAGE_USER = "The user is doesn`t exist";
    public static final String  ERROR_MESSAGE_USER_STATUS = "The user is unactive";
    public static final String  ERROR_MESSAGE_USER_EXIST_FAMILY ="The user has family debit account";
    public static final String ERROR_MESSAGE_OWNER = "Owner can`t delete yourself, try to delete account";

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
