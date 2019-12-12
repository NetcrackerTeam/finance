package com.netcracker.services;

import com.netcracker.models.User;

import javax.swing.plaf.basic.BasicButtonUI;
import java.math.BigInteger;
import java.nio.file.LinkOption;
import java.util.Date;


public interface EmailServiceSender {

    public void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId);

    public void sendMailAboutPersonalDebt(String emailTo, String userName, String perName, Long amount, BigInteger userId);

    public void sendMailAboutFamilyDebt(String emailTo, String userName,String famName, BigInteger amount, BigInteger userId);

    public void sendMailReminderPersonalCredit(User user, BigInteger id, BigInteger userId, Date date);

    public void sendMailReminderFamilyCredit(User user, BigInteger id, BigInteger userId, Date date);

    public void sendMailReminderPersonalExpense(User user, BigInteger id, BigInteger userId, Date date);

    public void sendMailAutoPersonalIncome(User user, BigInteger id, BigInteger userId, Date date);

    public void sendMailReminderFamilyExpense(User user, BigInteger id, BigInteger userId, Date date);

    public void sendMailAutoFamilyIncome(User user, BigInteger id, BigInteger userId, Date date);



}
