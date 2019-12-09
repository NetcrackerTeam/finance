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

import java.text.MessageFormat;


@Service
public class EmailServiceSenderImpl implements EmailServiceSenderService {

    @Autowired
    private UserDao userDao;

    private MailSender mailSender;
    //private JdbcTemplate template;
    private static final Logger logger = Logger.getLogger(EmailServiceSenderImpl.class);
    private String mail = "<nectrackerteam@gmail.com>";
    //String deactivate = this.template.queryForObject(DEACTIVATE_ACCOUNT, new Object[]{}, String.class);

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMailBeforeDeactivate(User user) {
        User userId = userDao.getUserById(user.getId());
        String userName = userDao.getUserById(user.getId()).getName();
        try {
            if (userId == null || userName == null) {
                throw new NullPointerException("user" + user.getId() + "is NULL");
            }
            if (userId.getUserStatusActive() == UserStatusActive.NO) {
                String deac = MessageFormat.format(DEACTIVATE_ACCOUNT, userDao.getUserById(user.getId()).getName());
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo("<" + user.geteMail() + ">");
                message.setFrom(mail);
                message.setText(deac);
                mailSender.send(message);
            } else {
                logger.error("user" + user.getId() + "is activate");
            }
        } catch (MailSendException mse) {
            logger.error(mse.getMessage(), mse);
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
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
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo("<" + emailAddressTo + ">");
//            message.setFrom("<nectrackerteam@gmail.com>");
//            message.setSubject("Your account is deactivate!!");
//            message.setText("Your login: " + login + "\n" + "Your password: " + accountExpense.getAmount());
//            mailSender.send(message);
//        } catch (MailSendException mex) {
//            logger.error("send massage exception");
//        }

    }

    @Override
    public void sendMailAutoIncome(String emailAddressTo, String login, String password) {

    }
}
