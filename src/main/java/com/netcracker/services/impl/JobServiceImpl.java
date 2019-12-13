package com.netcracker.services.impl;

import com.netcracker.models.MonthReport;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.EmailServiceSender;
import com.netcracker.services.JobService;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = Logger.getLogger(JobServiceImpl.class);

    @Autowired
    private MonthReportService monthReportService;

    @Autowired
    private AccountAutoOperationService accountAutoOperationService;

    @Autowired
    private EmailServiceSender emailServiceSender;


    private static final String CRONBYREMIND = "* * */10 * * ?";
    private static final String CRONBYREPORT = "* * * */1 * ?";
    private LocalDate localDateTo = LocalDate.now();
    private LocalDate localDateFrom = localDateTo.minus(1, ChronoUnit.MONTHS);

    private Date dateTo = DateUtils.localDateToDate(localDateTo);
    private Date dateFrom = DateUtils.localDateToDate(localDateFrom);

    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    //Start execute at 10 am
    @Override
    @Scheduled(cron = CRONBYREMIND)
    public void executeRemindAutoIncomePersonalEmailJob() {
        //  Collection<AutoOperationExpense> operationExpensesList = accountAutoOperationService
    }

    @Override
    @Scheduled(cron = CRONBYREMIND)
    public void executeRemindAutoExpenseFamilyEmailJob() {
        //  Collection<AutoOperationExpense> operationExpensesList = accountAutoOperationService
    }

    @Override
    @Scheduled(cron = CRONBYREPORT)
    public void executeMonthFamilyReportOnEmailJob() {

    }

    @Override
    @Scheduled(cron = CRONBYREPORT)
    public void executeMonthPersonalReportOnEmailJob() {
    }
}
