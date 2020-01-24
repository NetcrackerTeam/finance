package com.netcracker.services;

import com.netcracker.models.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public interface PersonalDebitService {

    PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount);

    public void deletePersonalDebitAccount(BigInteger accountId, BigInteger userId);

    PersonalDebitAccount getPersonalDebitAccount(BigInteger id);

    List<AbstractAccountOperation> getHistory(BigInteger accountId, LocalDateTime date);

    Collection<PersonalDebitAccount> getAllPersonalAccounts();

    List<ChartItem> getMonthData(BigInteger accountId);

    List<ChartItem> genChartListFromReports(List<MonthReport> monthReports, Locale locale);

    Collection<DonutChartItem> getMonthExpenseList(BigInteger accountId);

    Collection<DonutChartItem> getMonthIncomeList(BigInteger accountId);

    Collection<DonutChartItem> genExpenseList(Collection<CategoryExpenseReport> expenseReports);

    Collection<DonutChartItem> genIncomeList(Collection<CategoryIncomeReport> incomeReports);

    String ACCOUNT_NAME = "account";

}
