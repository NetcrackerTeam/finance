package com.netcracker.services.impl;

import com.netcracker.services.JobService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class JobServiceImpl implements JobService {

    private LocalDate localDate = LocalDate.now();
    private String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd"));

//    public static void main(String[] args) {
//        LocalDate localDate = LocalDate.now();
//        String formattedLocalDate = localDate.format(DateTimeFormatter.ofPattern("dd"));
//        System.out.println(formattedLocalDate);
//    }

    @Override
    public void createJob() {
    }
    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    //Start execute at 10 am
    @Override
    @Scheduled(cron = "* * */10 * * ?")
    public void executeRemindEmailJob() {

    }

    @Override
    @Scheduled(cron = "* * * */1 * ?")
    public void executeMonthReportOnEmailJob() {

    }

}
