package com.netcracker.services.impl;

import com.netcracker.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = Logger.getLogger(JobServiceImpl.class);

    @Autowired
    private MonthReportService monthReportService;

    @Autowired
    private AccountAutoOperationService accountAutoOperationService;


    @Autowired
    private EmailServiceSender emailServiceSender;


    @Autowired
    private FamilyCreditService familyCreditService;

    @Autowired
    private PersonalCreditService personalCreditService;

    private Path path;
    private static final String CRON_BY_EVERYDAY = "0 0 8 * * ?";//at 8 am every day
    private static final String CRON_BY_REPORT = "0 0 18 L * ?";//At 6 pm on the last of every month
    private LocalDate dateTo = LocalDate.now();
    private LocalDate dateFrom = dateTo.minus(1, ChronoUnit.MONTHS);

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomePersonalEmailJob() {
//          Collection<AutoOperationIncome> operationIncomeList =
//                  (Collection<AutoOperationIncome>) accountAutoOperationService.getFamilyIncomeAutoOperation(monthId);
//          if (!operationIncomeList.isEmpty()){
//              operationIncomeList.forEach(operationIncome->{
//                  try {
//                      emailServiceSender.sendMailAutoFamilyIncome(user.geteMail(),user.getName(),operationIncome.getAmount(),
//                              String.valueOf(operationIncome.getCategoryIncome()),user.getId());
//                      logger.debug("Email have been sent.");
//                  }catch (JobException e){
//                      logger.debug("Email can't be sent", e);
//                  }
//              });
//          }
    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpenseFamilyEmailJob() {
//        Collection<AutoOperationExpense> operationExpensesList =
//                (Collection<AutoOperationExpense>) accountAutoOperationService.getFamilyExpenseAutoOperation(monthId);
//        if (!operationExpensesList.isEmpty()){
//            operationExpensesList.forEach(operationExpense->{
//                try {
//                    emailServiceSender.sendMailAutoFamilyIncome(user.geteMail(),user.getName(),operationExpense.getAmount(),
//                            String.valueOf(operationExpense.getCategoryExpense()),user.getId());
//                    logger.debug("Email have been sent.");
//                }catch (JobException e){
//                    logger.debug("Email can't be sent", e);
//                }
//            });
//        }
    }

    @Override
    @Scheduled(cron = CRON_BY_REPORT)
    public void executeMonthFamilyReportOnEmailJob() {
//        Collection<MonthReport> monthReportsFamily =
//                (List<MonthReport>) monthReportService.getMonthPersonalReport(monthId, dateFrom, dateTo);
//        if (!monthReportsFamily.isEmpty()) {
//            monthReportsFamily.forEach(monthReport -> {
//                try {
//                    path = monthReportService.convertToTxt(monthReport);
//                    //  emailServiceSender.sendMailAboutFamilyDebt(path);
//                    logger.debug("Email have been sent. User id: {}, Date: {}"+ user.getId());
//                } catch (JobException e) {
//                    logger.debug("Email can't be sent", e);
//                }
//            });
//        }
    }

    @Override
    @Scheduled(cron = CRON_BY_REPORT)
    public void executeMonthPersonalReportOnEmailJob() {
//        Collection<MonthReport> monthReportsFamily =
//                (List<MonthReport>) monthReportService.getMonthFamilyReport(monthId, dateFrom, dateTo);
//        if (!monthReportsFamily.isEmpty()) {
//            monthReportsFamily.forEach(monthReport -> {
//                try {
//                    path = monthReportService.convertToTxt(monthReport);
//                   // emailServiceSender.monthReport(path);
//                    logger.debug("Email have been sent. User id: {}" + user.getId());
//                } catch (JobException e) {
//                    logger.debug("Email can't be sent", e);
//                }
//            });
//        }
    }


    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoIncomeFamilyEmailJob() {

    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeRemindAutoExpensePersonalEmailJob() {

    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executePersonalAutoDebtRepayment() {

    }

    @Override
    @Scheduled(cron = CRON_BY_EVERYDAY)
    public void executeFamilyAutoDebtRepayment() {

    }
}
