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
    String ADD_INCOME_PERS = "Added new income by account";
    String ADD_EXPENSE_PERS = "Added new expense by account";
    String DELETE_AUTO_INCOME_PERS = "Your auto income deleted by account ";
    String DELETE_AUTO_EXPENSE_PERS = "Your auto income deleted by account ";
    String ADD_AUTO_INCOME = "Added new auto income by account";
    String ADD_AUTO_EXPENSE = "Added new auto expense by account";
    //     FamilyDebitController
    String  DEACT_FAMACC_FAM = "Deactivated unsuccessfully account ";
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
}
