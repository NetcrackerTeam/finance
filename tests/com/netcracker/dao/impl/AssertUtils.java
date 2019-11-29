package com.netcracker.dao.impl;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.CreditOperation;

import static org.junit.Assert.assertEquals;

public class AssertUtils {

    public static void assertAutoOperationIncome(AutoOperationIncome expAO, AutoOperationIncome actualAO) {
        assertEquals(expAO.getId(), actualAO.getId());
        assertEquals(expAO.getUserId(), actualAO.getUserId());
        assertEquals(expAO.getCategoryIncome(), actualAO.getCategoryIncome());
        assertEquals(expAO.getAmount(), actualAO.getAmount());
        assertEquals(expAO.getDate(), actualAO.getDate());
        assertEquals(expAO.getDayOfMonth(), actualAO.getDayOfMonth());
    }

    public static void assertAutoOperationExpense(AutoOperationExpense expAO, AutoOperationExpense actualAO) {
        assertEquals(expAO.getId(), actualAO.getId());
        assertEquals(expAO.getUserId(), actualAO.getUserId());
        assertEquals(expAO.getCategoryExpense(), actualAO.getCategoryExpense());
        assertEquals(expAO.getAmount(), actualAO.getAmount());
        assertEquals(expAO.getDate(), actualAO.getDate());
        assertEquals(expAO.getDayOfMonth(), actualAO.getDayOfMonth());
    }

    public static void assertCreditOperation(CreditOperation expCO, CreditOperation actualCO) {
        assertEquals(expCO.getCreditOperationId(), actualCO.getCreditOperationId());
        assertEquals(expCO.getAmount(), actualCO.getAmount());
        assertEquals(expCO.getDate(), actualCO.getDate());
    }

}
