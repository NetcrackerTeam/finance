package com.netcracker.dao;

import com.netcracker.models.Debt;


import java.math.BigInteger;
import java.util.Date;
public interface CreditDeptDao {
    public Debt getDebtByPersonalCreditId(BigInteger id);
    public Debt getDebtByFamilyCreditId(BigInteger id);
    public Debt getDebtById(BigInteger id);
    public void updateDebtDateFrom(Date date);
    public void updateDebtDateTo(Date date);
    public void updateDebtAmount(long amount);
    public void createDebt();
}
