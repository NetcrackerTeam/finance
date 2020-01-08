package com.netcracker.controllers;

public interface MessageController {
    //UserController
    String SUCCESS_UPDATE_PASSWORD = "Password was updated successfully ";
    String SUCCESS_UPDATE_EMAIL = "Email was updated successfully";
    String INVALID_OLD_EMAIL = "Old email incorrect,\n" +
            "does not match the old email try again ";
    String  ERROR_VALIDATE_EMAIL = "You new email incorrect ";
    String LOGGER_UPDATE_EMAIL = "updateEmailByUser in  method updateEmail . User id - ";
    String LOGGER_UPDATE_PASSWORD ="updatePasswordByUser in  method updateUserPassword . User id - ";
    String INVALID_CONFIRM_PASSWORD = "Your passwords do not match, please  try again  ";
    //    PersonalDebitController
    String ADD_INCOME = "Added new income by account";
    String ADD_EXPENSE_PERS = "Added new expense by account";
    String DELETE_AUTO_INCOME_PERS = "Your auto income deleted by account ";
    String DELETE_AUTO_EXPENSE_PERS = "Your auto income deleted by account ";
    String ADD_AUTO_INCOME = "Added new auto income by account";
    String ADD_AUTO_EXPENSE = "Added new auto expense by account";
    //     FamilyDebitController
    String DEACT_FAMACC_FAM = "Deactivated successfully account ";
    String DEACT_UNS_FAMACC_FAM = "Deactivated unsuccessfully account ";
    String ADD_USER_FAM = "Adding successfully user ";
    String ADD_UNS_USER_FAM = "Adding unsuccessfully user ";
    String DELETE_USER_FAM = "Deleting successfully user ";
    String DELETE_UNS_USER_FAM = "Deleting unsuccessfully user ";
    String DELETE_AUTO_INCOME_FAM = "Deleting successfully autoIncomeOperation ";
    String DELETE_AUTO_EXPENSE_FAM = "Deleting successfully autoExpenseOperation ";
    String ADD_FAMILY_ACCOUNT = "Added new family account for user ";

    String debugStartMessage = " method start with parameters: ";
    //     CreditsControllers
    String ADD_PERSONAL_CREDIT = "Add new personal credit: ";
    String ADD_FAMILY_CREDIT = "Add new family credit: ";

    String NOT_ENOUGH_MONEY_MESSAGE = "There's not enough money on your debit account to complete this operation.";

    String PASS_UPPERCASE = "Your password must contain at least one uppercase character.";
    String PASS_LOWERCASE = "Your password must contain at least one lowercase character.";
    String PASS_SHORT = "Your password must be at least 8 characters long.";
    String PASS_NUM = "Your password must contain at least one number.";
    String EMAIL_INVALID = "You have entered an invalid email. Please try again.";
    String PASS_INVALID = "You have entered an invalid password. Please try again.";
    String USER_ALREADY_EXISTS = "A user with this email already exists.";
    String EMPTY_FIELD = "You did not fill out all the fields.";
    String LATIN_CHAR = "The password must contain only Latin characters.";
    String LATIN_LETTERS = "The username must contain only Latin letters without other characters.";
    String NAME_SHORT = "The username must contain at least 2 characters.";

}
