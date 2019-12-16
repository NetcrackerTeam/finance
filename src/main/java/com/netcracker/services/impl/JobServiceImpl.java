package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.exception.JobException;
import com.netcracker.models.*;
import com.netcracker.services.*;
import com.netcracker.services.utils.CreditUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
        for (AutoOperationIncome autoIncome: autoOperationIncomesFamily) {
            operationService.createFamilyOperationIncome(autoIncome.getDebitId(),autoIncome.getDebitId(),autoIncome.getAmount() ,localDateTo,autoIncome.getCategoryIncome());
            familyCreditService.getFamilyCredits(autoIncome.getDebitId());

        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpenseFamilyJob() {
        Collection<AutoOperationExpense> autoOperationExpense = accountAutoOperationService.getAllTodayOperationsFamilyExpense(dayNow);
        for (AutoOperationExpense autoExpense: autoOperationExpense) {
            operationService.createFamilyOperationExpense(autoExpense.getId(),autoExpense.getUserId(),autoExpense.getAmount(),localDateTo,autoExpense.getCategoryExpense());
            familyCreditService.getFamilyCredits(autoExpense.getId());
        }

    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpensePersonalJob() {
        Collection<AutoOperationExpense> autoOperationExpensePersonal = accountAutoOperationService.getAllTodayOperationsPersonalExpense(dayNow);
        for (AutoOperationExpense autoExpense: autoOperationExpensePersonal) {
            operationService.createPersonalOperationExpense(autoExpense.getId(),autoExpense.getAmount(),localDateTo,autoExpense.getCategoryExpense());
            personalCreditService.getPersonalCredits(autoExpense.getId());
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomePersonalJob() {
        Collection<AutoOperationIncome> autoOperationIncomePersonal = accountAutoOperationService.getAllTodayOperationsPersonalIncome(dayNow);
        for (AutoOperationIncome autoIncome: autoOperationIncomePersonal) {
            operationService.createPersonalOperationIncome(autoIncome.getDebitId(),autoIncome.getAmount(), localDateTo,autoIncome.getCategoryIncome());
            personalCreditService.getPersonalCredits(autoIncome.getDebitId());
        }
    }
// проверить кредитный счет

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeAutoCreditExpenseFamily() {
        Collection<FamilyCreditAccount> allFamilyCredit = creditAccountDao.getAllFamilyCreditIdsByMonthDay(dayNow);
        for (FamilyCreditAccount familyCredit : allFamilyCredit) {
            calculateCredit = CreditUtils.calculateMonthPayment(familyCredit.getDate(), familyCredit.getDateTo(), familyCredit.getPaidAmount(), familyCredit.getCreditRate());
            boolean paymentAutoFamilyCredit = familyCreditService.addFamilyCreditPaymentAuto(creditAccountDao.getPersonalDebitIdByCreditId(familyCredit.getDebt().getDebtId()),
                    familyCredit.getCreditId(), calculateCredit);
            if (!paymentAutoFamilyCredit) {
                familyCreditService.increaseDebt(familyCredit.getCreditId(), familyCredit.getPaidAmount());
            }
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeAutoCreditExpensePersonal() {
        Collection<PersonalCreditAccount> allPersonalCredit = creditAccountDao.getAllPersonCreditIdsByMonthDay(dayNow);
        for (PersonalCreditAccount personalCredit : allPersonalCredit) {
            calculateCredit = CreditUtils.calculateMonthPayment(personalCredit.getDateTo(), personalCredit.getDateTo(), personalCredit.getPaidAmount(), personalCredit.getCreditRate());
            boolean paymentAutoPersonalCredit = personalCreditService.addPersonalCreditPaymentAuto(creditAccountDao.getFamilyDebitIdByCreditId(personalCredit.getDebt().getDebtId()),
                    personalCredit.getCreditId(), calculateCredit);
            if (!paymentAutoPersonalCredit) {
                personalCreditService.increaseDebt(personalCredit.getCreditId(), personalCredit.getPaidAmount());
            }
        }
    }
}
