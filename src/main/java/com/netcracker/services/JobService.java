package com.netcracker.services;

import com.netcracker.models.User;

import java.math.BigInteger;

public interface JobService {

    public void executeMonthPersonalReportOnEmailJob(BigInteger monthId, User user);

    public void executeMonthFamilyReportOnEmailJob(BigInteger monthId, User user);

    public void executeRemindAutoIncomePersonalEmailJob(BigInteger monthId, User user);

    public void executeRemindAutoExpenseFamilyEmailJob(BigInteger monthId, User user);

}
