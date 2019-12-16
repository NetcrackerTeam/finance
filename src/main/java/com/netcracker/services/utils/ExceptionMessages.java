package com.netcracker.services.utils;

public interface ExceptionMessages {
    String NOT_ENOUGH_MONEY_ERROR = "Not enough money on debit account";
    String NULL_OBJECT_ERROR = "Null object was found";
    String LEFT_TO_PAY_ERROR = "Left to pay only %f";
    String ERROR_MESSAGE_FAMILY = "the family debit account doesn`t exist";
    String ERROR_MESSAGE_FAMILY_STATUS = "The user is unactive";
    String ERROR_MESSAGE_FAMILY_PARTICIPANTS = "the family debit account has participants";
    String ERROR_MESSAGE_USER = "The user is doesn`t exist";
    String ERROR_MESSAGE_USER_STATUS = "The user is unactive";
    String ERROR_MESSAGE_USER_EXIST_FAMILY = "The user has family debit account";
    String ERROR_MESSAGE_OWNER = "Owner can`t delete yourself, try to delete account";
    String ERROR_MESSAGE_MONTH_REPORT_WRITE = "Month report is null";
    String ERROR_MESSAGE_PREDICTION = "Not enough reports";
}
