package com.netcracker.dao;

import com.netcracker.models.CreditOperation;

import java.math.BigInteger;
import java.util.List;

public interface CreditOperationDao {
    public void createFamilyCreditOperation();

    public void createPersonalCreditOperation();

    public CreditOperation getAllCreditOperByCreditFamId(List<BigInteger> id);

    public CreditOperation getAllCreditOperByCreditPersId(List<BigInteger> id);
}
