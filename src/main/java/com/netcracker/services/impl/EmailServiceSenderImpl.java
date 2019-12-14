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
    public void sendMailAboutPersonalDebt(String emailTo, String userName, String perName, Long amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, perName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        String debitPersonal = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(10)), userName, perName, amount);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(debitPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailAboutFamilyDebt(String emailTo, String userName, String famName, Long amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, famName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        String debitFamily = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(11)), userId);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(debitFamily);
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
    public void sendMailReminderPersonalCredit(String emailTo, String userName, Long amountPaid, String credName, BigInteger userId, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId, date);

        SimpleMailMessage message = new SimpleMailMessage();
        String creditPersonal = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(4)), userName, amountPaid, credName, date);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(creditPersonal);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderFamilyCredit(String emailTo, String userName, Long amountPaid, String credName, BigInteger userId, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId, date);

        SimpleMailMessage message = new SimpleMailMessage();
        String creditFamily = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(4)), userName, amountPaid, credName, date);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(creditFamily);
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoPersonalExpense(String emailTo, String userName, Long amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        String personalAutoExpense = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(6)), userName, amountPaid, credName);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(personalAutoExpense);
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoPersonalIncome(String emailTo, String userName, Long amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        String personalAutoInc = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(5)), userName, amountPaid, credName);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(personalAutoInc);
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoFamilyExpense(String emailTo, String userName, Long amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        String familyAutoExpense = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(6)), userName, amountPaid, credName);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(familyAutoExpense);
        mailSender.send(message);
    }

    @Override
    public void sendMailAutoFamilyIncome(String emailTo, String userName, Long amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        SimpleMailMessage message = new SimpleMailMessage();
        String familyAutoInc = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(5)), userName, amountPaid, credName);
        message.setTo("<" + emailTo + ">");
        message.setFrom(mail);
        message.setText(familyAutoInc);
        mailSender.send(message);
    }


}
