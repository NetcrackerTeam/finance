package com.netcracker.controllers;

public interface MessageController {
    //    PersonalDebitController
    String ADD_CREDIT_PERS = "Added new credit by account";
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
}
