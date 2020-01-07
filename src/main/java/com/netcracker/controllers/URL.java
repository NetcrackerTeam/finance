package com.netcracker.controllers;

public interface URL {
    String INDEX = "index";
    String REGISTRATIONS_URL = "viewsLoginRegestration/layoutRegistrationUser";
    String LOGIN_URL = "viewsLoginRegestration/layoutLoginUser";
    String PERSONAL_DEBIT_URL = "personalDebit/layoutPrediction";
//    PersonalDebitController URL
    String MODAL_DIALOG_PERS = "personalDebit/modalDialog";
    String PERSONAL_DEBIT = "personalDebit/layoutPersonalDebit";
    String REPORT_PERS = "personalDebit/layoutReport";
    String TEMPLATE_URL = "templateForURL";
    String FAMILY_DEBIT = "familyDebit/layoutFamilyDebit";
//    PersonalCreditController URL
    String PERSONAL_CREDIT = "personalCredit/layoutCreateCredit";
//    PersonalCreditController URL
    String FAMILY_CREDIT = "familyCredit/layoutCreateFamilyCredit";

    String EXCEPTION_PAGE = "exceptionPage";
    String PARTICIPANTS_OF_FAMILY = "familyDebit/ParticipantsOfFamilyAccount";
}
