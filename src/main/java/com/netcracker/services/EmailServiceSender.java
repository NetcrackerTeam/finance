package com.netcracker.services;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;


public interface EmailServiceSender {

    public void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId);

    public void sendMailAboutPersonalDebt(String emailTo, String userName, String perName, double amount, BigInteger userId);

    public void sendMailAboutFamilyDebt(String emailTo, String userName, String famName, double amount, BigInteger userId);

    public void monthReport(String emailTo, String userName,BigInteger userId, Path path) throws MessagingException;

    public void sendMailReminderPersonalCredit(String emailTo, String userName, double amountPaid, String credName, BigInteger userId, LocalDate date);

    public void sendMailReminderFamilyCredit(String emailTo, String userName, double amountPaid, String credName, BigInteger userId, LocalDate date);

    public void sendMailAutoPersonalExpense(String emailTo, String userName, double amountPaid, String credName, BigInteger userId);

    public void sendMailAutoPersonalIncome(String emailTo, String userName, double amountPaid, String credName, BigInteger userId);

    public void sendMailAutoFamilyExpense(String emailTo, String userName, double amountPaid, String credName, BigInteger userId);

    public void sendMailAutoFamilyIncome(String emailTo, String userName, double amountPaid, String credName, BigInteger userId);

    String MAIL = "<nectrackerteam@gmail.com>";

}
