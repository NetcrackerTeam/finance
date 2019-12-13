package com.netcracker.services;

public interface JobService {
    public void executeMonthPersonalReportOnEmailJob();
    public void executeMonthFamilyReportOnEmailJob();
    public void executeRemindAutoExpensePersonalEmailJob();
    public void executeRemindAutoExpenseFamilyEmailJob();

}
