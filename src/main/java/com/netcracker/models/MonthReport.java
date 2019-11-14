package com.netcracker.models;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Map;

public class MonthReport {
    private BigInteger id;
    private Long totalIncome;
    private Long totalExpense;
    private Long balance;
    private LocalDate date;
    private Map<CategoryExpense, Long> categoryExpenseLongMap;
    private Map<CategoryIncome, Long>  categoryIncomeLongMap;


}
