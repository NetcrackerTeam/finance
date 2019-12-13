package com.netcracker.exception;

import com.netcracker.models.User;

public class UserException extends RuntimeException {
    private User user;
    private static final String  ERROR_MESSAGE_USER = "The user is doesn`t exist";
    private static final String  ERROR_MESSAGE_USER_STATUS = "The user is unactive";
    private static final String  ERROR_MESSAGE_USER_EXIST_FAMILY ="The user has family debit account";

    public static String getErrorMessageUserStatus(){
        return ERROR_MESSAGE_USER_STATUS;
    }

    public static String getErrorMessageUser(){
        return ERROR_MESSAGE_USER;
    }

    public static String getErrorMessageUserExistFamily(){
        return ERROR_MESSAGE_USER_EXIST_FAMILY;
    }

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
