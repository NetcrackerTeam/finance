package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.JobException;
import com.netcracker.models.*;
import com.netcracker.services.*;
import com.netcracker.services.utils.CreditUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = Logger.getLogger(JobServiceImpl.class);

    @Autowired
    private MonthReportService monthReportService;

    @Autowired
    private CreditAccountDao creditAccountDao;

    @Autowired
    private AccountAutoOperationService accountAutoOperationService;

    @Autowired
    private EmailServiceSender emailServiceSender;

    @Autowired
    private FamilyCreditService familyCreditService;

    @Autowired
    private PersonalCreditService personalCreditService;

    @Autowired
    private FamilyDebitService familyDebitService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private PersonalDebitService personalDebitService;

    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;

    @Autowired
    private UserService userService;

    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;

    private LocalDate localDateTo = LocalDate.now();
    private LocalDate dateFrom = localDateTo.minus(1, ChronoUnit.MONTHS);
    private int dayNow = localDateTo.getDayOfMonth();
    private Path path;
    double calculateCredit;
    private List<User> userList;

    @Override
    @Scheduled(cron = CRON_BY_REPORT)
    public void executeMonthFamilyReportOnEmailJob() {
        Collection<FamilyDebitAccount> familyDebitAccounts = familyDebitService.getAllFamilyAccounts();
        if (!familyDebitAccounts.isEmpty()) {
            familyDebitAccounts.forEach(debitAccountFamily -> {
                userList = debitAccountFamily.getParticipants();
                for (User user : userList) {
                    Collection<MonthReport> monthReportsFamily =
                            (List<MonthReport>) monthReportService.getMonthPersonalReport(user.getFamilyDebitAccount(), dateFrom, localDateTo);
                    if (!monthReportsFamily.isEmpty()) {
                        monthReportsFamily.forEach(monthReport -> {
                            try {
                                path = monthReportService.convertToTxt(monthReport);
                                emailServiceSender.monthReport(user.geteMail(), user.getId());// не дописан sender не могу передать путь к файлу
                                logger.debug("Email have been sent. User id: {}, Date: {}" + user.getId());
                            } catch (JobException e) {
                                logger.debug("Email can't be sent", e);
                            } catch (MessagingException e) {
                                logger.debug("Email can't be sent, messaging exception", e);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_REPORT)
    public void executeMonthPersonalReportOnEmailJob() {
        Collection<FamilyDebitAccount> familyDebitAccounts = familyDebitService.getAllFamilyAccounts();
        if (!familyDebitAccounts.isEmpty()) {
            familyDebitAccounts.forEach(debitAccountFamily -> {
                userList = debitAccountFamily.getParticipants();
                for (User user : userList) {
                    Collection<MonthReport> monthReportsFamily =
                            (List<MonthReport>) monthReportService.getMonthFamilyReport(user.getPersonalDebitAccount(), dateFrom, localDateTo);
                    if (!monthReportsFamily.isEmpty()) {
                        monthReportsFamily.forEach(monthReport -> {
                            try {
                                path = monthReportService.convertToTxt(monthReport);
                                emailServiceSender.monthReport(user.geteMail(), user.getId());//дописать сендер
                                logger.debug("Email have been sent. User id: {}" + user.getId());
                            } catch (JobException e) {
                                logger.debug("Email can't be sent", e);
                            } catch (MessagingException e) {
                                logger.debug("Email can't be sent, messaging exception", e);

                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomeFamilyJob() {
        Collection<AutoOperationIncome> autoOperationIncomesFamily = accountAutoOperationService.getAllTodayOperationsFamilyIncome(dayNow);
        for (AutoOperationIncome autoIncome : autoOperationIncomesFamily) {
            FamilyDebitAccount debitAccount = familyDebitService.getFamilyDebitAccount(autoIncome.getDebitId());
            operationService.createFamilyOperationIncome(autoIncome.getUserId(), autoIncome.getDebitId(), autoIncome.getAmount(), localDateTo, autoIncome.getCategoryIncome());
            double newAmount = debitAccount.getAmount() + autoIncome.getAmount();
            Collection<FamilyCreditAccount> credits = familyCreditService.getFamilyCredits(debitAccount.getId());
            for (FamilyCreditAccount cr : credits) {
                if (cr.getDebt().getAmountDebt() != 0) {
                    double payForDebt = CreditUtils.calculateMonthPayment(cr.getDate(), cr.getDateTo(), cr.getAmount(), cr.getCreditRate());
                    familyCreditService.addAutoDebtRepayment(debitAccount.getId(), cr.getCreditId(), payForDebt);
                }
            }
            try {
             //   emailServiceSender.sendMailAutoFamilyIncome(debitAccount.getOwner().geteMail(),
             //           debitAccount.getOwner().getName(), autoIncome.getAmount(), );
                logger.debug("Email have been sent with  " + debitAccount.getOwner().getName());
            } catch (JobException e) {
                logger.debug("Email can't be sent", e);
            }
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomePersonalJob() {
        Collection<AutoOperationIncome> autoOperationIncomePersonal = accountAutoOperationService.getAllTodayOperationsPersonalIncome(dayNow);
        for (AutoOperationIncome autoOperIncome : autoOperationIncomePersonal) {
            PersonalDebitAccount personalDebitAccount = personalDebitService.getPersonalDebitAccount(autoOperIncome.getDebitId());
            operationService.createPersonalOperationIncome(autoOperIncome.getDebitId(), autoOperIncome.getAmount(), localDateTo, autoOperIncome.getCategoryIncome());
            double newAmount = personalDebitAccount.getAmount() + autoOperIncome.getAmount();
            Collection<PersonalCreditAccount> creditAccounts = personalCreditService.getPersonalCredits(personalDebitAccount.getId());
            for (PersonalCreditAccount pc : creditAccounts) {
                if (pc.getDebt().getAmountDebt() != 0) {
                    double payForDebt = CreditUtils.calculateMonthPayment(pc.getDate(), pc.getDateTo(), pc.getAmount(), pc.getCreditRate());
                    personalCreditService.addAutoDebtRepayment(personalDebitAccount.getId(), pc.getCreditId(), payForDebt);
                }
                try {
                  //  emailServiceSender.sendMailAutoFamilyIncome(personalDebitAccount.getOwner().geteMail(),
                  //          personalDebitAccount.getOwner().getName(), autoOperIncome.getAmount(), );
                    logger.debug("Email have been sent with  " + personalDebitAccount.getOwner().getName());
                } catch (JobException e) {
                    logger.debug("Email can't be sent", e);
                }
            }

        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpenseFamilyJob() {
        Collection<AutoOperationExpense> autoOperationExpenseFamily = accountAutoOperationService.getAllTodayOperationsFamilyExpense(dayNow);
        for (AutoOperationExpense autoExpense : autoOperationExpenseFamily) {
            operationService.createFamilyOperationExpense(autoExpense.getUserId(), autoExpense.getDebitId(), autoExpense.getAmount(), localDateTo, autoExpense.getCategoryExpense());
            BigInteger userId = autoExpense.getUserId();
            User user = userService.getUserById(userId);
            FamilyDebitAccount familyDebitAccount = familyAccountDebitDao.getFamilyAccountById(user.getPersonalDebitAccount());
            if (familyDebitAccount.getAmount() < autoExpense.getAmount()) {
                try {
                    emailServiceSender.sendMailAboutPersonalDebt(user.geteMail(), user.getName(), "что за perName", familyDebitAccount.getAmount(), user.getId());
                    logger.debug("Email have been sent with auto credit . User id: {} " + user.getId());
                } catch (JobException e) {
                    logger.debug("Email can't be sent", e);
                }
            } else {
                //он обновит полностью
                // нужны ли методы в этом дао для конкретно добавление и  списание со счета ? я не до конца понимаю
                personalDebitAccountDao.updateAmountOfPersonalAccount(autoExpense.getDebitId(), autoExpense.getAmount());
            }
        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpensePersonalJob() {
        Collection<AutoOperationExpense> autoOperationExpensePersonal = accountAutoOperationService.getAllTodayOperationsPersonalExpense(dayNow);
        for (AutoOperationExpense autoExpense : autoOperationExpensePersonal) {
            operationService.createPersonalOperationExpense(autoExpense.getUserId(), autoExpense.getAmount(), localDateTo, autoExpense.getCategoryExpense());
            BigInteger userId = autoExpense.getUserId();
            User user = userService.getUserById(userId);
            PersonalDebitAccount personalDebitAccount = personalDebitAccountDao.getPersonalAccountById(user.getPersonalDebitAccount());
            if (personalDebitAccount.getAmount() < autoExpense.getAmount()) {
                try {
                    emailServiceSender.sendMailAboutPersonalDebt(user.geteMail(), user.getName(), "что за perName", personalDebitAccount.getAmount(), user.getId());
                    logger.debug("Email have been sent with auto credit . User id: {} " + user.getId());
                } catch (JobException e) {
                    logger.debug("Email can't be sent", e);
                }
            } else {
                //аналогично
                personalDebitAccountDao.updateAmountOfPersonalAccount(autoExpense.getDebitId(), autoExpense.getAmount());
            }
        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeAutoCreditExpenseFamily() {
        Collection<FamilyCreditAccount> allFamilyCredit = creditAccountDao.getAllFamilyCreditIdsByMonthDay(dayNow);
        for (FamilyCreditAccount familyCredit : allFamilyCredit) {
            BigInteger id = creditAccountDao.getFamilyDebitIdByCreditId(familyCredit.getCreditId());
            calculateCredit = CreditUtils.calculateMonthPayment(familyCredit.getDate(), familyCredit.getDateTo(), familyCredit.getPaidAmount(), familyCredit.getCreditRate());
            User user = familyDebitService.getFamilyDebitAccount(id).getOwner();
            boolean paymentAutoFamilyCredit = familyCreditService.addFamilyCreditPaymentAuto(id, familyCredit.getCreditId(), calculateCredit);
            if (!paymentAutoFamilyCredit) {
                familyCreditService.increaseDebt(familyCredit.getCreditId(), familyCredit.getPaidAmount());
                emailServiceSender.sendMailAboutFamilyDebt(user.geteMail(), user.getName(), familyCredit.getName(), calculateCredit, user.getId());
            }
            try {
                emailServiceSender.sendMailReminderFamilyCredit(user.geteMail(), user.getName(), calculateCredit, familyCredit.getName(), id, localDateTo);
                logger.debug("Email have been sent with auto credit . User id: {} " + user.getId());
            } catch (JobException e) {
                logger.debug("Email can't be sent", e);
            }

        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeAutoCreditExpensePersonal() {
        Collection<PersonalCreditAccount> allPersonalCredit = creditAccountDao.getAllPersonCreditIdsByMonthDay(dayNow);
        for (PersonalCreditAccount personalCredit : allPersonalCredit) {
            BigInteger id = creditAccountDao.getPersonalDebitIdByCreditId(personalCredit.getCreditId());
            calculateCredit = CreditUtils.calculateMonthPayment(personalCredit.getDate(), personalCredit.getDateTo(), personalCredit.getPaidAmount(), personalCredit.getCreditRate());
            User user = personalDebitService.getPersonalDebitAccount(id).getOwner();
            boolean paymentAutoPersonalCredit = personalCreditService.addPersonalCreditPaymentAuto(id, personalCredit.getCreditId(), calculateCredit);
            if (!paymentAutoPersonalCredit) {
                personalCreditService.increaseDebt(personalCredit.getCreditId(), personalCredit.getPaidAmount());
            }
            try {
                emailServiceSender.sendMailReminderPersonalCredit(user.geteMail(), user.getName(), calculateCredit, personalCredit.getName(), id, localDateTo);
                logger.debug("Email have been sent with auto credit . User id: {} " + user.getId());
            } catch (JobException e) {
                logger.debug("Email can't be sent", e);
            }
        }
    }
}
