package com.netcracker.exception;

import com.netcracker.models.FamilyDebitAccount;

public class FamilyDebitAccountException extends RuntimeException {
    private FamilyDebitAccount familyDebitAccount;
    private static final String  ERROR_MESSAGE_FAMILY = "the family debit account doesn`t exist";
    private static final String  ERROR_MESSAGE_FAMILY_STATUS = "The user is unactive";
    private static final String  ERROR_MESSAGE_FAMILY_PARTICIPANTS = "the family debit account has participants";

    public FamilyDebitAccount getFamilyDebitAccount() {
        return this.familyDebitAccount;
    }

    public static String getErrorMessageFamily(){
        return ERROR_MESSAGE_FAMILY;
    }

    public static String getErrorMessageFamilyStatus(){
        return ERROR_MESSAGE_FAMILY_STATUS;
    }

    public static String getErrorMessageFamilyParticipants(){
        return ERROR_MESSAGE_FAMILY_PARTICIPANTS;
    }

    public FamilyDebitAccountException() {
        super();
    }

    public FamilyDebitAccountException(String message) {
        super(message);
    }

    public FamilyDebitAccountException(String message, FamilyDebitAccount familyDebitAccount) {
        this(message);
        this.familyDebitAccount = familyDebitAccount;
    }
}
