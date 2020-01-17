package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.JobException;
import com.netcracker.models.*;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.*;
import com.netcracker.services.utils.CreditUtils;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = Logger.getLogger(JobServiceImpl.class);

    private static final int MaxNumber = 31;
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
    PersonalDebitAccountDao personalDebitAccountDao;

    @Autowired
    FamilyAccountDebitDao familyDebitAccountDao;

    private LocalDateTime localDateNow = LocalDateTime.now();
    private LocalDateTime dateFrom = DateUtils.addMonthsToDate(localDateNow, -1);
    private int dayNow = localDateNow.getDayOfMonth();
    private int lastDays = DateUtils.MaxDayInCurrentMonth();

    @Override
    @Scheduled(cron = CRON_BY_REPORT)
    public void executeMonthFamilyReportOnEmailJob() {
        Collection<FamilyDebitAccount> familyDebitAccounts = familyDebitService.getAllFamilyAccounts();
        if (familyDebitAccounts.isEmpty())
            return;
        familyDebitAccounts.forEach(debitAccountFamily -> {
            boolean checkStatus = UserStatusActive.YES.equals(debitAccountFamily.getOwner().getUserStatusActive());
            if (checkStatus) {
                ObjectsCheckUtils.isNotNull(debitAccountFamily);
                monthReportService.formMonthFamilyReportFromDb(debitAccountFamily.getId(), dateFrom, localDateNow);
                MonthReport monthReport = monthReportService.getMonthPersonalReport(debitAccountFamily.getId(), localDateNow, true);
                sendReportByMail(monthReport, (AbstractDebitAccount) familyDebitAccounts);
            } else
                logger.debug("user status not active " + debitAccountFamily.getOwner().getId());
        });
    }

    @Override
    @Scheduled(cron = CRON_BY_REPORT)
    public void executeMonthPersonalReportOnEmailJob() {
        Collection<PersonalDebitAccount> personalDebitAccounts = personalDebitService.getAllPersonalAccounts();
        if (personalDebitAccounts.isEmpty())
            return;
        personalDebitAccounts.forEach(debitAccountPersonal -> {
            boolean checkStatus = UserStatusActive.YES.equals(debitAccountPersonal.getOwner().getUserStatusActive());
            ObjectsCheckUtils.isNotNull(debitAccountPersonal);
            if (checkStatus) {
                monthReportService.formMonthPersonalReportFromDb(debitAccountPersonal.getId(), dateFrom, localDateNow);
                MonthReport monthReport = monthReportService.getMonthPersonalReport(debitAccountPersonal.getId(), localDateNow, true);
                sendReportByMail(monthReport, (AbstractDebitAccount) personalDebitAccounts);
            } else
                logger.debug("user status not active " + debitAccountPersonal.getOwner().getId());
        });
    }

    private void sendReportByMail(MonthReport monthReport, AbstractDebitAccount debitAccount) {
        Path path;
        try {
            path = monthReportService.convertToTxt(monthReport);
            emailServiceSender.monthReport(debitAccount.getOwner().geteMail(), debitAccount.getOwner().getName(), path);
            logger.debug("Email have been sent");
        } catch (MessagingException e) {
            logger.debug("Email can't be sent, messaging exception", e);
        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomeFamilyJob() {
        Collection<AutoOperationIncome> autoOperationIncomesFamily = accountAutoOperationService.getAllTodayOperationsFamilyIncome(dayNow);
        if (autoOperationIncomesFamily.isEmpty())
            return;
        for (AutoOperationIncome autoIncome : autoOperationIncomesFamily) {
            ObjectsCheckUtils.isNotNull(autoIncome);
            FamilyDebitAccount debitAccount = familyDebitService.getFamilyDebitAccount(autoIncome.getDebitId());
            operationService.createFamilyOperationIncome(autoIncome.getUserId(), autoIncome.getDebitId(), autoIncome.getAmount(), localDateNow, autoIncome.getCategoryIncome());
            double newAmount = debitAccount.getAmount() + autoIncome.getAmount();
            debitAccount.setAmount(newAmount);
            familyDebitAccountDao.updateAmountOfFamilyAccount(debitAccount.getId(), debitAccount.getAmount());
            Collection<FamilyCreditAccount> credits = familyCreditService.getFamilyCredits(debitAccount.getId());
            for (FamilyCreditAccount cr : credits) {
                checkDebtRepayment(cr, debitAccount);
            }
            emailServiceSender.sendMailAutoFamilyIncome(debitAccount.getOwner().geteMail(),
                    debitAccount.getOwner().getName(), autoIncome.getAmount(), autoIncome.getCategoryIncome().name());
            logger.debug("Email have been sent with  " + debitAccount.getOwner().getName());

            if (dayNow == lastDays) {
                executeFamilyIncomeAutoOperationLastDay(dayNow);
            }
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomePersonalJob() {
        Collection<AutoOperationIncome> autoOperationIncomePersonal = accountAutoOperationService.getAllTodayOperationsPersonalIncome(dayNow);
        if (autoOperationIncomePersonal.isEmpty())
            return;
        for (AutoOperationIncome autoIncome : autoOperationIncomePersonal) {
            ObjectsCheckUtils.isNotNull(autoIncome);
            PersonalDebitAccount debitAccount = personalDebitService.getPersonalDebitAccount(autoIncome.getDebitId());
            operationService.createPersonalOperationIncome(autoIncome.getDebitId(), autoIncome.getAmount(), localDateNow, autoIncome.getCategoryIncome());
            double newAmount = debitAccount.getAmount() + autoIncome.getAmount();
            debitAccount.setAmount(newAmount);
            personalDebitAccountDao.updateAmountOfPersonalAccount(debitAccount.getId(), newAmount);
            Collection<PersonalCreditAccount> creditAccounts = personalCreditService.getPersonalCredits(debitAccount.getId());
            for (PersonalCreditAccount cr : creditAccounts) {
                checkDebtRepayment(cr, debitAccount);
            }
            emailServiceSender.sendMailAutoPersonalIncome(debitAccount.getOwner().geteMail(),
                    debitAccount.getOwner().getName(), autoIncome.getAmount(), autoIncome.getCategoryIncome().name());
            logger.debug("Email have been sent with  " + debitAccount.getOwner().getName());
            if (dayNow == lastDays) {
                executePersonalIncomeAutoOperationLastDay(dayNow);
            }
        }
    }

    private void checkDebtRepayment(AbstractCreditAccount cr, AbstractDebitAccount debitAccount) {
        double payForDebt = CreditUtils.calculateMonthPayment(cr.getDate(), cr.getDateTo(), cr.getAmount(), cr.getCreditRate());
        while (cr.getDebt().getAmountDebt() != 0) {
            if (debitAccount.getAmount() > payForDebt) {
                personalCreditService.addAutoDebtRepayment(debitAccount.getId(), cr.getCreditId(), payForDebt);
                debitAccount.setAmount(debitAccount.getAmount() - payForDebt);
            } else break;
        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpenseFamilyJob() {
        Collection<AutoOperationExpense> autoOperationExpenseFamily = accountAutoOperationService.getAllTodayOperationsFamilyExpense(dayNow);
        for (AutoOperationExpense autoExpense : autoOperationExpenseFamily) {
            ObjectsCheckUtils.isNotNull(autoExpense);
            operationService.createFamilyOperationExpense(autoExpense.getUserId(), autoExpense.getDebitId(), autoExpense.getAmount(), localDateNow, autoExpense.getCategoryExpense());
            FamilyDebitAccount debitAccount = familyAccountDebitDao.getFamilyAccountById(autoExpense.getId());
            if (debitAccount.getAmount() < autoExpense.getAmount()) {
                logger.debug("Auto expense cannot be done. Not enough money");
            } else {
                double newAmount = debitAccount.getAmount() - autoExpense.getAmount();
                familyDebitAccountDao.updateAmountOfFamilyAccount(autoExpense.getDebitId(), newAmount);
                emailServiceSender.sendMailAutoFamilyExpense(debitAccount.getOwner().geteMail(),
                        debitAccount.getOwner().getName(),
                        autoExpense.getAmount(),
                        autoExpense.getCategoryExpense().name());
            }
        }
        if (dayNow == lastDays) {
            executeFamilyExpenseAutoOperationLastDay(dayNow);
        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpensePersonalJob() {
        Collection<AutoOperationExpense> autoOperationExpensePersonal = accountAutoOperationService.getAllTodayOperationsPersonalExpense(dayNow);
        for (AutoOperationExpense autoExpense : autoOperationExpensePersonal) {
            ObjectsCheckUtils.isNotNull(autoExpense);
            operationService.createPersonalOperationExpense(autoExpense.getUserId(), autoExpense.getAmount(), localDateNow, autoExpense.getCategoryExpense());
            PersonalDebitAccount debitAccount = personalDebitAccountDao.getPersonalAccountById(autoExpense.getId());
            if (debitAccount.getAmount() < autoExpense.getAmount()) {
                logger.debug("Auto expense cannot be done. Not enough money");
            } else {
                double newAmount = debitAccount.getAmount() - autoExpense.getAmount();
                personalDebitAccountDao.updateAmountOfPersonalAccount(autoExpense.getDebitId(), newAmount);
                emailServiceSender.sendMailAutoPersonalExpense(debitAccount.getOwner().geteMail(),
                        debitAccount.getOwner().getName(),
                        autoExpense.getAmount(),
                        autoExpense.getCategoryExpense().name());
            }
        }
        if (dayNow == lastDays) {
            executePersonalExpenseAutoOperationLastDay(dayNow);
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeAutoCreditExpenseFamily() {
        double calculateCredit;
        Collection<FamilyCreditAccount> allFamilyCredit = creditAccountDao.getAllFamilyCreditIdsByMonthDay(dayNow);
        for (FamilyCreditAccount familyCredit : allFamilyCredit) {
            ObjectsCheckUtils.isNotNull(familyCredit);
            BigInteger id = creditAccountDao.getFamilyDebitIdByCreditId(familyCredit.getCreditId());
            calculateCredit = CreditUtils.calculateMonthPayment(familyCredit.getDate(), familyCredit.getDateTo(), familyCredit.getPaidAmount(), familyCredit.getCreditRate());
            User user = familyDebitService.getFamilyDebitAccount(id).getOwner();
            boolean paymentAutoFamilyCredit = familyCreditService.addFamilyCreditPaymentAuto(id, familyCredit.getCreditId(), calculateCredit, user.getId());
            if (!paymentAutoFamilyCredit) {
                familyCreditService.increaseDebt(familyCredit.getCreditId(), familyCredit.getPaidAmount());
                emailServiceSender.sendMailAboutFamilyDebt(user.geteMail(), user.getName(), familyCredit.getName(), calculateCredit);
            }
            try {
                emailServiceSender.sendMailReminderFamilyCredit(user.geteMail(), user.getName(), calculateCredit, familyCredit.getName(), localDateNow);
                logger.debug("Email have been sent with auto credit . User id: {} " + user.getId());
            } catch (JobException e) {
                logger.debug("Email can't be sent", e);
            }
        }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeAutoCreditExpensePersonal() {
        double calculateCredit;
        Collection<PersonalCreditAccount> allPersonalCredit = creditAccountDao.getAllPersonCreditIdsByMonthDay(dayNow);
        for (PersonalCreditAccount personalCredit : allPersonalCredit) {
            ObjectsCheckUtils.isNotNull(personalCredit);
            BigInteger id = creditAccountDao.getPersonalDebitIdByCreditId(personalCredit.getCreditId());
            calculateCredit = CreditUtils.calculateMonthPayment(personalCredit.getDate(), personalCredit.getDateTo(), personalCredit.getPaidAmount(), personalCredit.getCreditRate());
            User user = personalDebitService.getPersonalDebitAccount(id).getOwner();
            boolean paymentAutoPersonalCredit = personalCreditService.addPersonalCreditPaymentAuto(id, personalCredit.getCreditId(), calculateCredit);
            if (!paymentAutoPersonalCredit) {
                personalCreditService.increaseDebt(personalCredit.getCreditId(), personalCredit.getPaidAmount());
            }
            try {
                emailServiceSender.sendMailReminderPersonalCredit(user.geteMail(), user.getName(), calculateCredit, personalCredit.getName(), localDateNow);
                logger.debug("Email have been sent with auto credit . User id: {} " + user.getId());
            } catch (JobException e) {
                logger.debug("Email can't be sent", e);
            }
        }
    }


    public void executePersonalIncomeAutoOperationLastDay(int lastDayOfCurrentMonth) {
        for (int lastDay = lastDayOfCurrentMonth; lastDay <= MaxNumber; lastDay++) {
            Collection<AutoOperationIncome> autoOperationIncomePersonal = accountAutoOperationService.getAllTodayOperationsPersonalIncome(lastDay);
            if (autoOperationIncomePersonal.isEmpty())
                return;
            for (AutoOperationIncome autoIncome : autoOperationIncomePersonal) {
                ObjectsCheckUtils.isNotNull(autoIncome);
                PersonalDebitAccount debitAccount = personalDebitService.getPersonalDebitAccount(autoIncome.getDebitId());
                operationService.createPersonalOperationIncome(autoIncome.getDebitId(), autoIncome.getAmount(), localDateNow, autoIncome.getCategoryIncome());
                double newAmount = debitAccount.getAmount() + autoIncome.getAmount();
                debitAccount.setAmount(newAmount);
                personalDebitAccountDao.updateAmountOfPersonalAccount(debitAccount.getId(), newAmount);
                Collection<PersonalCreditAccount> creditAccounts = personalCreditService.getPersonalCredits(debitAccount.getId());
                for (PersonalCreditAccount cr : creditAccounts) {
                    checkDebtRepayment(cr, debitAccount);
                }
            }
        }
    }


    public void executeFamilyIncomeAutoOperationLastDay(int lastDayOfCurrentMonth) {
        for (int lastDay = lastDayOfCurrentMonth; lastDay <= MaxNumber; lastDay++) {
            Collection<AutoOperationIncome> autoOperationIncomesFamily = accountAutoOperationService.getAllTodayOperationsFamilyIncome(lastDay);
            if (autoOperationIncomesFamily.isEmpty())
                for (AutoOperationIncome autoIncome : autoOperationIncomesFamily) {
                    ObjectsCheckUtils.isNotNull(autoIncome);
                    FamilyDebitAccount debitAccount = familyDebitService.getFamilyDebitAccount(autoIncome.getDebitId());
                    operationService.createFamilyOperationIncome(autoIncome.getUserId(), autoIncome.getDebitId(), autoIncome.getAmount(), localDateNow, autoIncome.getCategoryIncome());
                    double newAmount = debitAccount.getAmount() + autoIncome.getAmount();
                    debitAccount.setAmount(newAmount);
                    familyDebitAccountDao.updateAmountOfFamilyAccount(debitAccount.getId(), debitAccount.getAmount());
                    Collection<FamilyCreditAccount> credits = familyCreditService.getFamilyCredits(debitAccount.getId());
                    for (FamilyCreditAccount cr : credits) {
                        checkDebtRepayment(cr, debitAccount);
                    }
                }
        }
    }

    public void executePersonalExpenseAutoOperationLastDay(int lastDayOfCurrentMonth) {
        for (int lastDay = lastDayOfCurrentMonth; lastDay <= MaxNumber; lastDay++) {
            Collection<AutoOperationExpense> autoOperationExpensePersonal = accountAutoOperationService.getAllTodayOperationsPersonalExpense(lastDay);
            for (AutoOperationExpense autoExpense : autoOperationExpensePersonal) {
                ObjectsCheckUtils.isNotNull(autoExpense);
                operationService.createPersonalOperationExpense(autoExpense.getUserId(), autoExpense.getAmount(), localDateNow, autoExpense.getCategoryExpense());
                PersonalDebitAccount debitAccount = personalDebitAccountDao.getPersonalAccountById(autoExpense.getId());
                if (debitAccount.getAmount() < autoExpense.getAmount()) {
                    logger.debug("Auto expense cannot be done. Not enough money");
                } else {
                    double newAmount = debitAccount.getAmount() - autoExpense.getAmount();
                    personalDebitAccountDao.updateAmountOfPersonalAccount(autoExpense.getDebitId(), newAmount);
                    emailServiceSender.sendMailAutoPersonalExpense(debitAccount.getOwner().geteMail(),
                            debitAccount.getOwner().getName(),
                            autoExpense.getAmount(),
                            autoExpense.getCategoryExpense().name());
                }
            }
        }
    }


    public void executeFamilyExpenseAutoOperationLastDay(int lastDayOfCurrentMonth) {
        for (int lastDay = lastDayOfCurrentMonth; lastDay <= MaxNumber; lastDay++) {
            Collection<AutoOperationExpense> autoOperationExpenseFamily = accountAutoOperationService.getAllTodayOperationsFamilyExpense(lastDay);
            for (AutoOperationExpense autoExpense : autoOperationExpenseFamily) {
                ObjectsCheckUtils.isNotNull(autoExpense);
                operationService.createFamilyOperationExpense(autoExpense.getUserId(), autoExpense.getDebitId(), autoExpense.getAmount(), localDateNow, autoExpense.getCategoryExpense());
                FamilyDebitAccount debitAccount = familyAccountDebitDao.getFamilyAccountById(autoExpense.getId());
                if (debitAccount.getAmount() < autoExpense.getAmount()) {
                    logger.debug("Auto expense cannot be done. Not enough money");
                } else {
                    double newAmount = debitAccount.getAmount() - autoExpense.getAmount();
                    familyDebitAccountDao.updateAmountOfFamilyAccount(autoExpense.getDebitId(), newAmount);
                    emailServiceSender.sendMailAutoFamilyExpense(debitAccount.getOwner().geteMail(),
                            debitAccount.getOwner().getName(),
                            autoExpense.getAmount(),
                            autoExpense.getCategoryExpense().name());
                }
            }
        }
    }
}

