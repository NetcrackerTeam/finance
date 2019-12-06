package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.EmailServiceSenderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceSenderServiceImpl implements EmailServiceSenderService {

    @Autowired
    private UserDao userDao;

    private MailSender mailSender;
    private static final Logger logger = Logger.getLogger(EmailServiceSenderServiceImpl.class);

    public void setMailSender(MailSender mailSender){ this.mailSender = mailSender;}

    @Override
    public void sendMailBeforeDeactivate(User user) {
        if(userDao.getUserById(user.getId()).getUserStatusActive() == UserStatusActive.NO ){
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo("<" + user.geteMail() + ">");
                message.setFrom("<nectrackerteam@gmail.com>");
                message.setSubject("Your account is deactivate!!");
                message.setText("Your login: " + userDao.getUserById(user.getId()).getName() + "\n" + "Your password: " + user.getPassword());
                mailSender.send(message);
            } catch (MailSendException mex){
                logger.error("send massage exception");
            }
        }
    }

    @Override
    public void sendMailAboutDebt(String emailAddressTo, String login, String password) {

    }

    @Override
    public void sendMailReminderCredit(String emailAddressTo, String login, String password) {

    }

    @Override
    public void sendMailReminderExpense(String emailAddressTo, String login, AccountExpense accountExpense) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("<" + emailAddressTo + ">");
            message.setFrom("<nectrackerteam@gmail.com>");
            message.setSubject("Your account is deactivate!!");
            message.setText("Your login: " + login + "\n" + "Your password: " + accountExpense.getAmount());
            mailSender.send(message);
        } catch (MailSendException mex){
            logger.error("send massage exception");
        }

    }

    @Override
    public void sendMailAutoIncome(String emailAddressTo, String login, String password) {

    }
}
