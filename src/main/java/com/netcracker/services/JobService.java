package com.netcracker.services;


public interface JobService {
    String CRON_BY_EVERYDAY = "0 0 8 * * ?";//at 8 am every day

    String CRON_BY_REPORT = "0 0 9 1 * ?";//At 9 am every first day of month

    void executeMonthPersonalReportOnEmailJob();

    void executeMonthFamilyReportOnEmailJob();

    void executeRemindAutoIncomeFamilyJob();

    void executeRemindAutoExpenseFamilyJob();

    void executeRemindAutoExpensePersonalJob();

    void executeRemindAutoIncomePersonalJob();

    void executeAutoCreditExpenseFamily();

    void executeAutoCreditExpensePersonal();

}
