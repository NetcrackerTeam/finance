package com.netcracker.services;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDateTime;


public interface EmailServiceSender {

    void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId);

    void sendMailAboutPersonalDebt(String emailTo, String userName, String perName, double amount);

    void sendMailAboutFamilyDebt(String emailTo, String userName, String famName, double amount);

    void monthReport(String emailTo, String userNam, Path path) throws MessagingException;

    void sendMailReminderPersonalCredit(String emailTo, String userName, double amountPaid, String credName, LocalDateTime date);

    void sendMailReminderFamilyCredit(String emailTo, String userName, double amountPaid, String credName, LocalDateTime date);

    void sendMailAutoPersonalExpense(String emailTo, String userName, double amountPaid, String expenseName);

    void sendMailAutoPersonalIncome(String emailTo, String userName, double amountPaid, String credName);

    void sendMailAutoFamilyExpense(String emailTo, String userName, double amountPaid, String expenseName);

    void sendMailAutoFamilyIncome(String emailTo, String userName, double amountPaid, String incName);

    String MAIL = "<nectrackerteam@gmail.com>";

    String FILE_NAME = "Month_report.txt";

    String RIGHT_BRACKET = "<";

    String LEFT_BRACKET = ">";

}
