package com.netcracker.services;


public interface JobService {

    public void executeMonthPersonalReportOnEmailJob();

    public void executeMonthFamilyReportOnEmailJob();

    public void executeRemindAutoIncomePersonalEmailJob();

    public void executeRemindAutoExpenseFamilyEmailJob();

    public void  executeRemindAutoIncomeFamilyEmailJob();

    public void executeRemindAutoExpensePersonalEmailJob();

    public  void  executePersonalAutoDebtRepayment();

    public void executeFamilyAutoDebtRepayment();


}
