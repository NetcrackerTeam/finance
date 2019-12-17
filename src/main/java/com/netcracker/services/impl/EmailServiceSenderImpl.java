package com.netcracker.services.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.TemplatesDaoImpl;
import com.netcracker.exception.UserException;
import com.netcracker.models.enums.EmailServiceTemplateCategory;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.EmailServiceSender;
import com.netcracker.services.UserService;
import com.netcracker.services.utils.ExceptionMessages;
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
import java.math.BigInteger;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.LocalDate;

@Service
public class EmailServiceSenderImpl implements EmailServiceSender {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TemplatesDaoImpl templatesDao;
    @Autowired
    public JavaMailSender mailSender;

    private static final Logger logger = Logger.getLogger(EmailServiceSenderImpl.class);

    public void setMailSender(MailSender mailSender) {
        this.mailSender = (JavaMailSender) mailSender;
    }

    private void sendMail(String emailTo, String mess, BigInteger id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(RIGHT_BRACKET + emailTo + LEFT_BRACKET);
        message.setSubject(templatesDao.sendNameMessageById(id));
        message.setFrom(MAIL);
        message.setText(mess);
        mailSender.send(message);
    }

    @Override
    public void sendMailBeforeDeactivate(String emailTo, String userName, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, userId);

        if (userDao.getUserById(userId).getUserStatusActive() == UserStatusActive.NO) {
            String userIsDeactivate = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.DEACTIVATE.getId()), userName);
            sendMail(emailTo, userIsDeactivate, EmailServiceTemplateCategory.DEACTIVATE.getId());
            logger.debug("Mail sending is success" + userId);
        } else {
            logger.debug("The user " + userId + " is Active");
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_PERSONAL_STATUS);
        }
    }

    @Override
    public void sendMailAboutPersonalDebt(String emailTo, String userName, String debtName, double amount) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, debtName);

        String personalDebt = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.PERSONAL_DEBIT.getId()), userName, debtName, amount);
        sendMail(emailTo, personalDebt, EmailServiceTemplateCategory.PERSONAL_DEBIT.getId());
    }

    @Override
    public void sendMailAboutFamilyDebt(String emailTo, String userName, String debtName, double amount) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, debtName);

        String familyDebt = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.FAMILY_DEBIT.getId()), userName, debtName, amount);
        sendMail(emailTo, familyDebt, EmailServiceTemplateCategory.FAMILY_DEBIT.getId());
    }

    @Override
    public void monthReport(String emailTo, String userName, Path path) throws MessagingException {
        ObjectsCheckUtils.isNotNull(emailTo);
        String monthReportMess = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.MONTH_REPORT.getId()), userName);

        MimeMessage message = mailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
        helper.setTo(RIGHT_BRACKET + emailTo + LEFT_BRACKET);
        helper.setSubject(templatesDao.sendMassageById(EmailServiceTemplateCategory.MONTH_REPORT.getId()));
        helper.setText(monthReportMess);
        FileSystemResource monthReport = new FileSystemResource(path);
        helper.addAttachment(FILE_NAME, monthReport);
        mailSender.send(message);
    }

    @Override
    public void sendMailReminderPersonalCredit(String emailTo, String userName, double amountPaid, String credName, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, date);

        String personalCred = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.REMINDER_PERSONAL_CREDIT.getId()), userName, amountPaid, credName, date);
        sendMail(emailTo, personalCred, EmailServiceTemplateCategory.REMINDER_PERSONAL_CREDIT.getId());
    }

    @Override
    public void sendMailReminderFamilyCredit(String emailTo, String userName, double amountPaid, String credName, LocalDate date) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, credName, date);

        String familyCredit = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.REMINDER_FAMILY_CREDIT.getId()), userName, amountPaid, credName, date);
        sendMail(emailTo, familyCredit, EmailServiceTemplateCategory.REMINDER_FAMILY_CREDIT.getId());
    }

    @Override
    public void sendMailAutoPersonalExpense(String emailTo, String userName, double amountPaid, String expenseName) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, expenseName);

        String personalExp = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.AUTO_PERSONAL_EXPENSE.getId()), userName, expenseName, amountPaid);
        sendMail(emailTo, personalExp, EmailServiceTemplateCategory.AUTO_PERSONAL_EXPENSE.getId());
    }

    @Override
    public void sendMailAutoPersonalIncome(String emailTo, String userName, double amountPaid, String incName) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, incName);

        String personalInc = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.AUTO_PERSONAL_INCOME.getId()), userName, incName, amountPaid);
        sendMail(emailTo, personalInc, EmailServiceTemplateCategory.AUTO_PERSONAL_INCOME.getId());
    }

    @Override
    public void sendMailAutoFamilyExpense(String emailTo, String userName, double amountPaid, String expenseName) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, expenseName);

        String familyExp = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.AUTO_FAMILY_EXPENSE.getId()), userName, expenseName, amountPaid);
        sendMail(emailTo, familyExp, EmailServiceTemplateCategory.AUTO_FAMILY_EXPENSE.getId());
    }

    @Override
    public void sendMailAutoFamilyIncome(String emailTo, String userName, double amountPaid, String incName) {
        ObjectsCheckUtils.isNotNull(emailTo, userName, incName);

        String familyInc = MessageFormat.format(templatesDao.sendMassageById(EmailServiceTemplateCategory.AUTO_FAMILY_INCOME.getId()), userName, incName, amountPaid);
        sendMail(emailTo, familyInc, EmailServiceTemplateCategory.AUTO_FAMILY_INCOME.getId());
    }
}