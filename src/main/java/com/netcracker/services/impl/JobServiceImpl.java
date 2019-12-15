//package com.netcracker.services.impl;
//
//import com.netcracker.exception.JobException;
//import com.netcracker.models.MonthReport;
//import com.netcracker.models.User;
//import com.netcracker.services.EmailServiceSender;
//import com.netcracker.services.JobService;
//import com.netcracker.services.MonthReportService;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//]import org.springframework.stereotype.Service;
//import java.math.BigInteger;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@Service
//public class JobServiceImpl implements JobService {
//
//    private static final Logger logger = Logger.getLogger(JobServiceImpl.class);
//
//    @Autowired
//    private MonthReportService monthReportService;
//
//
//    @Autowired
//    private EmailServiceSender emailServiceSender;
//
//
//    private static final String CRONBYREMIND = "* * */10 * * ?";
//    private static final String CRONBYREPORT = "* * * */1 * ?";
//    private LocalDate dateTo = LocalDate.now();
//    private LocalDate dateFrom = dateTo.minus(1, ChronoUnit.MONTHS);
//
//    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
//    //Start execute at 10 am
//    @Override
//    @Scheduled(cron = CRONBYREMIND)
//    public void executeRemindAutoIncomePersonalEmailJob(BigInteger monthId , User user) {
//        //  Collection<AutoOperationExpense> operationExpensesList = accountAutoOperationService
//    }
//
//    @Override
//    @Scheduled(cron = CRONBYREMIND)
//    public void executeRemindAutoExpenseFamilyEmailJob(BigInteger monthId, User user) {
//        //  Collection<AutoOperationExpense> operationExpensesList = accountAutoOperationService
//    }
//
//    @Override
//    @Scheduled(cron = CRONBYREPORT)
//    public void executeMonthFamilyReportOnEmailJob(BigInteger monthId, User user) {
//        List<MonthReport> monthReportsFamily =
//                (List<MonthReport>) monthReportService.getMonthPersonalReport(monthId, dateFrom, dateTo);
//        if (!monthReportsFamily.isEmpty()) {
//            monthReportsFamily.forEach(monthReport -> {
//                try {
//                    String message = " " + monthReport.getBalance() + "!";
//                  //  emailServiceSender.sendMailAboutFamilyDebt();
//                    logger.info("Email have been sent. User id: {}, Date: {}");
//                } catch (JobException e) {
//                    logger.error("Email can't be sent", e);
//                }
//           });
//        }
//    }
//
//    @Override
//    @Scheduled(cron = CRONBYREPORT)
//    public void executeMonthPersonalReportOnEmailJob(BigInteger monthId, User user) {
////        List<MonthReport> monthReportsPersonal =
////                (List<MonthReport>) monthReportService.getMonthFamilyReport(new BigInteger(String.valueOf(2)), dateFrom, dateTo);
////        if (!monthReportsPersonal.isEmpty()) {
////
////        }
//    }
//}
