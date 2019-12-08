package com.netcracker.services;

public interface JobService {
    public void createJob();

    public void executeMonthReportOnEmailJob();
    public void executeRemindEmailJob();

}
