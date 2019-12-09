package com.netcracker.services;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.User;


public interface EmailServiceSenderService {

    public void sendMailBeforeDeactivate(User user);

    public void sendMailAboutDebt(String emailAddressTo, String login, String password);

    public void sendMailReminderCredit(String emailAddressTo, String login, String password);

    public void sendMailReminderExpense(String emailAddressTo, String login, AccountExpense accountExpense);

    public void sendMailAutoIncome(String emailAddressTo, String login, String password);

    String DEACTIVATE_ACCOUNT = "SELECT NAME, MESSAGE FROM TEMPLATE WHERE ID = 9";
}
