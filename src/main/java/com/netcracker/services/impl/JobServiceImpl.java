package com.netcracker.services.impl;

import com.netcracker.exception.JobException;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.MonthReport;
import com.netcracker.models.User;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.EmailServiceSender;
import com.netcracker.services.JobService;
import com.netcracker.services.MonthReportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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
    private AccountAutoOperationService accountAutoOperationService;


    @Autowired
    private EmailServiceSender emailServiceSender;

    private Path path;
    private static final String CRONBYREMIND = "* * */10 * * ?";
    private static final String CRONBYREPORT = "* * * */1 * ?";
    private LocalDate dateTo = LocalDate.now();
    private LocalDate dateFrom = dateTo.minus(1, ChronoUnit.MONTHS);

    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    //Start execute at 10 am
    @Override
    @Scheduled(cron = CRONBYREMIND)
    public void executeRemindAutoIncomePersonalEmailJob(BigInteger monthId, User user) {
          Collection<AutoOperationIncome> operationIncomeList =
                  (Collection<AutoOperationIncome>) accountAutoOperationService.getFamilyIncomeAutoOperation(monthId);
          if (!operationIncomeList.isEmpty()){
              operationIncomeList.forEach(operationIncome->{
                  try {
                      emailServiceSender.sendMailAutoFamilyIncome(user.geteMail(),user.getName(),operationIncome.getAmount(),
                              String.valueOf(operationIncome.getCategoryIncome()),user.getId());
                      logger.debug("Email have been sent.");
                  }catch (JobException e){
                      logger.debug("Email can't be sent", e);
                  }
              });
          }
    }

    @Override
    @Scheduled(cron = CRONBYREMIND)
    public void executeRemindAutoExpenseFamilyEmailJob(BigInteger monthId, User user) {
        Collection<AutoOperationExpense> operationExpensesList =
                (Collection<AutoOperationExpense>) accountAutoOperationService.getFamilyExpenseAutoOperation(monthId);
        if (!operationExpensesList.isEmpty()){
            operationExpensesList.forEach(operationExpense->{
                try {
                    emailServiceSender.sendMailAutoFamilyIncome(user.geteMail(),user.getName(),operationExpense.getAmount(),
                            String.valueOf(operationExpense.getCategoryExpense()),user.getId());
                    logger.debug("Email have been sent.");
                }catch (JobException e){
                    logger.debug("Email can't be sent", e);
                }
            });
        }
    }

    @Override
    @Scheduled(cron = CRONBYREPORT)
    public void executeMonthFamilyReportOnEmailJob(BigInteger monthId, User user) {
        Collection<MonthReport> monthReportsFamily =
                (List<MonthReport>) monthReportService.getMonthPersonalReport(monthId, dateFrom, dateTo);
        if (!monthReportsFamily.isEmpty()) {
            monthReportsFamily.forEach(monthReport -> {
                try {
                    path = monthReportService.convertToTxt(monthReport);
                    String message = " " + monthReport.getBalance() + "!";
                    //  emailServiceSender.sendMailAboutFamilyDebt(path);
                    logger.debug("Email have been sent. User id: {}, Date: {}"+ user.getId());
                } catch (JobException e) {
                    logger.debug("Email can't be sent", e);
                }
            });
        }
    }

    @Override
    @Scheduled(cron = CRONBYREPORT)
    public void executeMonthPersonalReportOnEmailJob(BigInteger monthId, User user) {
        Collection<MonthReport> monthReportsFamily =
                (List<MonthReport>) monthReportService.getMonthFamilyReport(monthId, dateFrom, dateTo);
        if (!monthReportsFamily.isEmpty()) {
            monthReportsFamily.forEach(monthReport -> {
                try {
                    path = monthReportService.convertToTxt(monthReport);
                    String message = " " + monthReport.getBalance() + "!";
                   // emailServiceSender.monthReport(path);
                    logger.debug("Email have been sent. User id: {}" + user.getId());
                } catch (JobException e) {
                    logger.debug("Email can't be sent", e);
                }
            });
        }
    }
}
