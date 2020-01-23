package com.netcracker.services.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.*;
import com.netcracker.services.OperationService;
import com.netcracker.services.PersonalDebitService;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class PersonalDebitServiceImpl implements PersonalDebitService {

    @Autowired
    private PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    private OperationService operationService;
    @Autowired
    private MonthReportDao monthReportDao;

    private static final Logger logger = Logger.getLogger(PersonalDebitServiceImpl.class);

    @Override
    public PersonalDebitAccount createPersonalDebitAccount(PersonalDebitAccount personalDebitAccount) {
        personalDebitAccount.setObjectName(personalDebitAccount.getOwner().getName() + ACCOUNT_NAME);
        logger.debug("createPersonalDebitAccount() method. projectId = " + personalDebitAccount);
        return personalDebitAccountDao.createPersonalAccount(personalDebitAccount);
    }

    @Override
    public void deletePersonalDebitAccount(BigInteger accountId, BigInteger userId) {
        ObjectsCheckUtils.isNotNull(accountId, userId);
        logger.debug("deletePersonalDebitAccount method. account_id = " + accountId + " user_id = " + userId);
        personalDebitAccountDao.deletePersonalAccountById(accountId, userId);
    }

    @Override
    public PersonalDebitAccount getPersonalDebitAccount(BigInteger id) {
        logger.debug("get personal debit account by id:" + id);
        ObjectsCheckUtils.isNotNull(id);
        PersonalDebitAccount debitAccount = personalDebitAccountDao.getPersonalAccountById(id);
        LocalDateTime startMonthDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
        Collection<AccountIncome> incomes = operationService.getIncomesPersonalAfterDateByAccountId(id, startMonthDate);
        Collection<AccountExpense> expenses = operationService.getExpensesPersonalAfterDateByAccountId(id, startMonthDate);
        debitAccount.setMonthIncome(incomes.stream().mapToDouble(AccountIncome::getAmount).sum());
        debitAccount.setMonthExpense(expenses.stream().mapToDouble(AccountExpense::getAmount).sum());
        return debitAccount;
    }


    public List<AbstractAccountOperation> getHistory(BigInteger accountId, LocalDateTime date) {
        logger.debug("Entering select(getHistory=" + accountId + " date :" + date + ")");
        return operationService.getAllPersonalOperations(accountId, date);
    }

    @Override
    public Collection<PersonalDebitAccount> getAllPersonalAccounts() {
        return personalDebitAccountDao.getAllPersonalAccounts();
    }

    @Override
    public List<ChartItem> getMonthData(BigInteger accountId) {
        List<MonthReport> monthReports = new ArrayList<>(monthReportDao.getFullPersonalReports(accountId));
        Locale locale = Locale.getDefault();
        PersonalDebitAccount debitAccount = getPersonalDebitAccount(accountId);
        List<ChartItem> chartItems = genChartListFromReports(monthReports, locale);
//        monthReports.sort(Comparator.comparing(MonthReport::getDateFrom));
//        List<ChartItem> chartItems = new ArrayList<>();
//        for (MonthReport rep : monthReports) {
//            chartItems.add(new ChartItem(rep.getDateFrom().getMonth().getDisplayName(TextStyle.SHORT, locale), rep.getTotalExpense(), rep.getTotalIncome()));
//        }

        chartItems.add(new ChartItem(LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, locale), debitAccount.getMonthExpense(), debitAccount.getMonthIncome()));
        return chartItems;
    }

    public List<ChartItem> genChartListFromReports (List<MonthReport> monthReports, Locale locale) {
        monthReports.sort(Comparator.comparing(MonthReport::getDateFrom));
        List<ChartItem> chartItems = new ArrayList<>();
        for (MonthReport rep : monthReports) {
            chartItems.add(new ChartItem(rep.getDateFrom().getMonth().getDisplayName(TextStyle.SHORT, locale), rep.getTotalExpense(), rep.getTotalIncome()));
        }
        return chartItems;
    }

}
