package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.TemplatesDaoImpl;
import com.netcracker.exception.UserException;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.EmailServiceSender;
import com.netcracker.services.UserService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.time.LocalDate;

@Service
public class EmailServiceSenderImpl implements EmailServiceSender {

    @Autowired
    private UserDao userDao;
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

    private String messageAboutDebt(BigInteger id, String userName, String debtName, double amount){
        String debit = MessageFormat.format(templatesDao.sendMassageById(id), userName, debtName, amount);
        return debit;
    }

    private String messageReminderCredit(BigInteger id, String userName, double amountPaid, String credName, LocalDate date){
        String credit = MessageFormat.format(templatesDao.sendMassageById(id), userName, amountPaid, credName, date);
        return credit;
    }

    private String messageAutoInEx(BigInteger id,String userName, double amountPaid, String credName){
        String autoInEx = MessageFormat.format(templatesDao.sendMassageById(id), userName, amountPaid, credName);
        return  autoInEx;
    }
    @Override
    public void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, userId);

        if (userDao.getUserById(userId).getUserStatusActive() == UserStatusActive.NO) {
            String userIsDeactivate = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(9)), userName);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("<" + emailTo + ">");
            message.setFrom(mail);
            message.setText(userIsDeactivate);
            mailSender.send(message);
            logger.debug("Mail sending is success" + userId);
        } else {
            logger.debug("The user " + userId + " is Active");
            throw new UserException("The user is active");
        }
    }

    @Override
    public void sendMailAboutPersonalDebt(String emailTo, String userName, String debtName, double amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, debtName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageAboutDebt(BigInteger.valueOf(10), userName, debtName, amount));
        mailSender.send(message);
    }

    @Override
    public void sendMailAboutFamilyDebt(String emailTo, String userName, String debtName, double amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, debtName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageAboutDebt(BigInteger.valueOf(11), userName, debtName, amount));
        mailSender.send(message);
    }

    @Override
    public void monthReport(String emailTo, BigInteger userId) throws MessagingException {
        ObjectsCheckUtils.isNotNull(emailTo, userId);

        MimeMessage message = mailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
        helper.setTo("<" + emailTo + ">");
        helper.setSubject("Test email with attachments");
        helper.setText("Hello, Im testing email with attachments!");
        String path1 = "D:/test.txt";
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment("Txt.txt", file1);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderPersonalCredit(String emailTo, String userName, double amountPaid, String credName, BigInteger userId, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId, date);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageReminderCredit(BigInteger.valueOf(4), userName, amountPaid, credName, date));
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderFamilyCredit(String emailTo, String userName, double amountPaid, String credName, BigInteger userId, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId, date);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageReminderCredit(BigInteger.valueOf(5), userName, amountPaid, credName, date));
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoPersonalExpense(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageAutoInEx(BigInteger.valueOf(6), userName, amountPaid, credName));
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoPersonalIncome(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageAutoInEx(BigInteger.valueOf(7), userName, amountPaid, credName));
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoFamilyExpense(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageAutoInEx(BigInteger.valueOf(8), userName, amountPaid, credName));
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoFamilyIncome(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(messageAutoInEx(BigInteger.valueOf(5), userName, amountPaid, credName));
        mailSender.send(message);
    }


}