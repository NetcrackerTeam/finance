package com.netcracker.services.impl;

import com.netcracker.dao.*;
import com.netcracker.dao.impl.TemplatesDaoImpl;
import com.netcracker.exception.UserException;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.EmailServiceSender;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;

@Service
public class EmailServiceSenderImpl implements EmailServiceSender {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private CreditAccountDao creditAccountDao;
    @Autowired
    private OperationDao operationDao;
    @Autowired
    TemplatesDaoImpl templatesDao;
    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    UserService userService;

    private static final Logger logger = Logger.getLogger(EmailServiceSenderImpl.class);
    private String mail = "<nectrackerteam@gmail.com>";

    public void setMailSender(MailSender mailSender) {
        this.mailSender = (JavaMailSender) mailSender;
    }

    @Override
    public void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId) {
        //User userTemp = userDao.getUserById(userId);
        if (userId == null || userName == null || emailTo == null) {
            logger.debug("The user " + userId + " is NULL");
            throw new UserException("The user is doesn`t exist");
        }   else if( userDao.getUserById(userId).getUserStatusActive() == UserStatusActive.NO) {
            String deac = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(19)), userName);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("<" + emailTo + ">");
            message.setFrom(mail);
            message.setText(deac);
            this.mailSender.send(message);
        } else {
            logger.debug("The user " + userId + " is unActive");
            throw new UserException("The user is unactive");
        }
        logger.debug("Mail sending is success(sendTo=" + userId);
    }

    @Override
    public void sendMailAboutPersonalDebt(String emailTo, String userName,String perName, Long amount, BigInteger userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(12)), userName, perName, amount);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailAboutFamilyDebt(String emailTo, String userName,String famName, BigInteger amount,BigInteger userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(9)),userId);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderPersonalCredit(User user, BigInteger id, BigInteger userId, Date date) {
        String userName = user.getName();
        String persCred = creditAccountDao.getPersonalCreditById(id).getName();
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(9)), userName);
        message.setTo("<" + user.geteMail() + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderFamilyCredit(User user, BigInteger id, BigInteger userId, Date date) {

        SimpleMailMessage message = new SimpleMailMessage();
        //String debitPersonal = MessageFormat.format();
        message.setTo("<" + user.geteMail() + ">");
        message.setFrom(mail);
        // message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderPersonalExpense(User user, BigInteger id, BigInteger userId, Date date) {
        String userName = userDao.getUserById(userId).getName();
        Collection persEx = operationDao.getExpensesPersonalAfterDateByAccountId(id, date);
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format( userName, persEx);
        message.setTo("<" + user.geteMail() + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoPersonalIncome(User user, BigInteger id, BigInteger userId, Date date) {
        //name us name personal
        String userName = userDao.getUserById(userId).getName();
        Collection persInc = operationDao.getIncomesPersonalAfterDateByAccountId(id,date);
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format( userName, persInc);
        message.setTo("<" + user.geteMail() + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderFamilyExpense(User user, BigInteger id, BigInteger userId, Date date) {
        String userName = userDao.getUserById(userId).getName();
        Collection famEx = operationDao.getExpensesFamilyAfterDateByAccountId(id, date);
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format( userName, famEx);
        message.setTo("<" + user.geteMail() + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoFamilyIncome(User user, BigInteger id, BigInteger userId, Date date) {
        String userName = userDao.getUserById(userId).getName();
        Collection famInc = operationDao.getIncomesFamilyAfterDateByAccountId(id, date);
        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format( userName, famInc);
        message.setTo("<" + user.geteMail() + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }


}
