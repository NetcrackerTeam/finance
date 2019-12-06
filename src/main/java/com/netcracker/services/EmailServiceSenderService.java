package com.netcracker.services;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.User;

import java.math.BigInteger;

public interface EmailServiceSenderService {
    public void sendMailBeforeDeactivate(User user);

    public void sendMailAboutDebt(String emailAddressTo, String login, String password);

    public void sendMailReminderCredit(String emailAddressTo, String login, String password);

    public void sendMailReminderExpense(String emailAddressTo, String login, AccountExpense accountExpense);

    public void sendMailAutoIncome(String emailAddressTo, String login, String password);
}
