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

    public void setMailSender(MailSender mailSender) {
        this.mailSender = (JavaMailSender) mailSender;
    }

    private void sendMail(String emailTo, String mess) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("<" + emailTo + ">");
        message.setFrom(MAIL);
        message.setText(mess);
        mailSender.send(message);
    }

    @Override
    public void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, userId);

        if (userDao.getUserById(userId).getUserStatusActive() == UserStatusActive.NO) {
            String userIsDeactivate = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(9)), userName);
            sendMail(emailTo, userIsDeactivate);
            logger.debug("Mail sending is success" + userId);
        } else {
            logger.debug("The user " + userId + " is Active");
            throw new UserException("The user is active");
        }
    }

    @Override
    public void sendMailAboutPersonalDebt(String emailTo, String userName, String debtName, double amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, debtName, userId);

        String personalDebt = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(10)), userName, debtName, amount);
        sendMail(emailTo, personalDebt);
    }

    @Override
    public void sendMailAboutFamilyDebt(String emailTo, String userName, String debtName, double amount, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, debtName, userId);

        String familyDebt = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(11)), userName, debtName, amount);
        sendMail(emailTo, familyDebt);
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
        String path1 = "path";
        FileSystemResource file1 = new FileSystemResource(new File(path1));
        helper.addAttachment("Txt.txt", file1);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderPersonalCredit(String emailTo, String userName, double amountPaid, String credName, BigInteger userId, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId, date);

        String personalCred = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(9)), userName, amountPaid, credName, date);
        sendMail(emailTo, personalCred);
    }

    @Override
    public void sendMailReminderFamilyCredit(String emailTo, String userName, double amountPaid, String credName, BigInteger userId, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId, date);

        String familyCredit = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(5)), userName, amountPaid, credName, date);
        sendMail(emailTo, familyCredit);
    }

    @Override
    public void sendMailAutoPersonalExpense(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        String personalExp = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(6)), userName, amountPaid, credName);
        sendMail(emailTo, personalExp);
    }

    @Override
    public void sendMailAutoPersonalIncome(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        String personalInc = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(7)), userName, amountPaid, credName);
        sendMail(emailTo, personalInc);
    }

    @Override
    public void sendMailAutoFamilyExpense(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        String familyExp = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(8)), userName, amountPaid, credName);
        sendMail(emailTo, familyExp);
    }

    @Override
    public void sendMailAutoFamilyIncome(String emailTo, String userName, double amountPaid, String credName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, userId);

        String familyInc = MessageFormat.format(templatesDao.sendMassageById(BigInteger.valueOf(5)), userName, amountPaid, credName);
        sendMail(emailTo, familyInc);
    }
}