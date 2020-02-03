package com.netcracker.services;


public interface JobService {
    String CRON_BY_EVERYDAY = "0 0 6 * * ?";//at 6 am every day

    String CRON_BY_REPORT = "0 0 1 1 * ?";//At 1 am every first day of month

    void executeMonthPersonalReportOnEmailJob();

    void executeMonthFamilyReportOnEmailJob();

    void executeRemindAutoIncomeFamilyJob();

    void executeRemindAutoExpenseFamilyJob();

    void executeRemindAutoExpensePersonalJob();

    void executeRemindAutoIncomePersonalJob();

    void executeAutoCreditExpenseFamily();

    void executeAutoCreditExpensePersonal();

}
