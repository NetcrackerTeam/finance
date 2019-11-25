package com.netcracker.dao;

import com.netcracker.models.CreditOperation;

import java.math.BigInteger;
import java.util.List;

public interface CreditOperationDao {
    void createFamilyCreditOperation();

    void createPersonalCreditOperation();

    CreditOperation getAllCreditOperByCreditFamId(List<BigInteger> id);

    CreditOperation getAllCreditOperByCreditPersId(List<BigInteger> id);
}
