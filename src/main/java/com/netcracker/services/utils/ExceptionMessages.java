package com.netcracker.services.utils;

public interface ExceptionMessages {
    String NOT_ENOUGH_MONEY_ERROR = "Not enough money on debit account";
    String NULL_OBJECT_ERROR = "Null object was found";
    String LEFT_TO_PAY_ERROR = "Left to pay only %.2f";
    String ERROR_MESSAGE_FAMILY = "the family debit account doesn`t exist";
    String ERROR_MESSAGE_FAMILY_STATUS = "The user is unactive";
    String ERROR_MESSAGE_FAMILY_PARTICIPANTS = "the family debit account has participants";
    String ERROR_MESSAGE_USER = "The user is doesn`t exist";
    String ERROR_MESSAGE_USER_STATUS = "The user is unactive";
    String ERROR_MESSAGE_USER_EXIST_FAMILY = "The user has family debit account";
    String ERROR_MESSAGE_OWNER = "Owner can`t delete yourself, try to delete account";
    String ERROR_MESSAGE_MONTH_REPORT_WRITE = "Month report is null";
    String ERROR_MESSAGE_PREDICTION = "Not enough reports";
    String ERROR_MESSAGE_PERSONAL = "the personal debit account doesn`t exist";
    String ERROR_MESSAGE_PERSONAL_STATUS = "The user is unactive";
    String ERROR_MESSAGE_ILLEGAL_AMOUNT = "Amount can`t be negative";
    String ERROR_MESSAGE_NUMBER_ZERO = "The value of the entered number is zero";

    String INVALID_EMAIL = "The email is invalid";
    String INVALID_PASSWORD = "The password is invalid";
    String PASS_UPPER = "Not found an uppercase";
    String PASS_LOWER = "Not found a lowercase";
    String PASS_NUM = "Not found a number";
    String PASS_SHORT = "Password is too short";
    String USER_ALREADY_EXIST = "User already exist";
    String EMPTY_FIELD = "The field is empty";
    String LATIN_CHAR = "String hasn't latin char";
    String LATIN_LETTERS = "Must be only latin char without numbers";
    String NAME_SHORT = "Username is too short";
    String NAME_FAMILY_SHORT = "account name is too short";
}
