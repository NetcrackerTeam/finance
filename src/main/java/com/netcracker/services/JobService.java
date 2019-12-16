package com.netcracker.services;


public interface JobService {
    String CRON_BY_EVERYDAY = "0 0 8 * * ?";//at 8 am every day

    String CRON_BY_REPORT = "0 0 18 L * ?";//At 6 pm on the last of every month

    public void executeMonthPersonalReportOnEmailJob();

    public void executeMonthFamilyReportOnEmailJob();

    public void executeRemindAutoIncomeFamilyJob();

    public void executeRemindAutoExpenseFamilyJob();

    public void executeRemindAutoExpensePersonalJob();

    public void executeRemindAutoIncomePersonalJob();

    public void executeAutoCreditExpenseFamily();

    public void executeAutoCreditExpensePersonal();

}
