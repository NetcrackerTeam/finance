package com.netcracker.exception;

import com.netcracker.models.PersonalDebitAccount;

public class PersonalDebitAccountException extends RuntimeException {
    private PersonalDebitAccount personalDebitAccount;
    private static final String ERROR_MESSAGE_PERSONAL = "the personal debit account doesn`t exist";
    private static final String ERROR_MESSAGE_PERSONAL_STATUS = "The user is unactive";

    public PersonalDebitAccount getPersonalDebitAccount() {
        return this.personalDebitAccount;
    }

    public static String getErrorMessagePersonal() {
        return ERROR_MESSAGE_PERSONAL;
    }

    public static String getErrorMessageFamilyStatus() {
        return ERROR_MESSAGE_PERSONAL_STATUS;
    }

    public PersonalDebitAccountException() {
        super();
    }

    public PersonalDebitAccountException(String message) {
        super(message);
    }

    public PersonalDebitAccountException(String message, PersonalDebitAccount personalDebitAccount) {
        this(message);
        this.personalDebitAccount = personalDebitAccount;
    }
}
