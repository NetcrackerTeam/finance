package com.netcracker.services;


public interface JobService {
    String CRON_BY_EVERYDAY = "0 0 8 * * ?";//at 8 am every day

    String CRON_BY_REPORT = "0 0 9 1 * ?";//At 9 am every first day of month

    String cront = "*/60 * * * * *"; // every ten second for test

    public void executeMonthPersonalReportOnEmailJob();

    public void executeMonthFamilyReportOnEmailJob();

    public void executeRemindAutoIncomeFamilyJob();

    public void executeRemindAutoExpenseFamilyJob();

    public void executeRemindAutoExpensePersonalJob();

    public void executeRemindAutoIncomePersonalJob();

    public void executeAutoCreditExpenseFamily();

    public void executeAutoCreditExpensePersonal();

}
