package com.netcracker.exception;

import com.netcracker.models.PersonalDebitAccount;

public class PersonalDebitAccountException extends RuntimeException {
    private PersonalDebitAccount personalDebitAccount;
    public PersonalDebitAccount getPersonalDebitAccount(){return this.personalDebitAccount;}

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
