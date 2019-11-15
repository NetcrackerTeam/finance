package com.netcracker.services;

public interface EmailServiceSenderService {
    public void sendMailBeforeDeactivate();

    public void sendMailAboutDebt();

    public void sendMailReminderCredit();

    public void sendMailReminderExpense();

    public void sendMailAutoIncome();
}
