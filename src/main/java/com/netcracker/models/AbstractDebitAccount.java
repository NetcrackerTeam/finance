package com.netcracker.models;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractDebitAccount {
    private BigInteger id;
    private Long amount;
    private User owner;
    private List<AccountIncome> accountIncomesList;
    private List<AccountExpense> accountExpensesList;
}
