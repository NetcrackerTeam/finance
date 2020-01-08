package com.netcracker.controllers;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.*;
import com.netcracker.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/debitPersonal")
public class PersonalDebitController {

    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    AutoOperationDao autoOperationDao;
    @Autowired
    AccountAutoOperationService accountAutoOperationService;
    @Autowired
    UserService userService;
    @Autowired
    OperationService operationService;
    @Autowired
    MonthReportService monthReportService;
    @Autowired
    CreditAccountDao creditAccountDao;
    @Autowired
    PersonalCreditService creditService;
    @Autowired
    PersonalDebitService personalDebitService;
    @Autowired
    UserDao userDao;

    private static final Logger logger = Logger.getLogger(PersonalDebitController.class);

    @RequestMapping(value = "/income", method = RequestMethod.POST)
    @ResponseBody
    public Status addIncomePersonal(@RequestBody AccountIncome income,
                                    Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        operationService.createPersonalOperationIncome(accountId, income.getAmount(), LocalDate.now(), income.getCategoryIncome());
        PersonalDebitAccount debit = personalDebitAccountDao.getPersonalAccountById(accountId);
        double amount = debit.getAmount() + income.getAmount();
        personalDebitAccountDao.updateAmountOfPersonalAccount(accountId, amount);
        return new Status(true, MessageController.ADD_INCOME);
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    @ResponseBody
    public Status addExpensePersonal(
            @RequestBody AccountExpense expense, Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        operationService.createPersonalOperationExpense(accountId, expense.getAmount(), LocalDate.now(), expense.getCategoryExpense());
        PersonalDebitAccount debit = personalDebitAccountDao.getPersonalAccountById(accountId);
        double amount = debit.getAmount() - expense.getAmount();
        personalDebitAccountDao.updateAmountOfPersonalAccount(accountId, amount);
        return new Status(true, MessageController.ADD_EXPENSE_PERS);
    }

    private BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getPersonalDebitAccount();
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    Collection<AbstractAccountOperation> getHistory(Principal principal,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.debug("getHistory Personal");
        BigInteger debitId = getAccountByPrincipal(principal);
        return personalDebitService.getHistory(debitId, date);
    }

    @RequestMapping(value = "/autoOperationHistory", method = RequestMethod.GET)
    public @ResponseBody
    List<AbstractAutoOperation> getAutoHis(Principal principal) {
        BigInteger debitId = getAccountByPrincipal(principal);
        return accountAutoOperationService.getAllOperationsPersonal(debitId);
    }

    @RequestMapping(value = "/createAutoIncome", method = RequestMethod.POST)
    public @ResponseBody
    Status createAutoIncome(@RequestBody AutoOperationIncome autoOperationIncome,
                            Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        accountAutoOperationService.createPersonalIncomeAutoOperation(autoOperationIncome, accountId);
        logger.debug("autoIncome is done!");
        return new Status(true, MessageController.ADD_AUTO_INCOME);
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    public @ResponseBody
    Status createAutoExpense(@RequestBody AutoOperationExpense autoOperationExpense,
                             Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, accountId);
        logger.debug("expense is done!");
        return new Status(true, MessageController.ADD_AUTO_EXPENSE);
    }

    @RequestMapping(value = "/deleteAutoIncome", method = RequestMethod.GET)
    public Status deleteAutoIncome(Principal principal
    ) {
        logger.debug("delete autoIncomePersonal");
        BigInteger debitId = getAccountByPrincipal(principal);
        accountAutoOperationService.deleteAutoOperation(debitId);
        return new Status(true, MessageController.DELETE_AUTO_INCOME_PERS);
    }

    @RequestMapping(value = "/deleteAutoExpense", method = RequestMethod.DELETE)
    public Status deleteAutoExpense(
            Principal principal
    ) {
        logger.debug("delete autoExpensePersonal");
        BigInteger debitId = getAccountByPrincipal(principal);
        accountAutoOperationService.deleteAutoOperation(debitId);
        return new Status(true, MessageController.DELETE_AUTO_EXPENSE_PERS);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public String getReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        BigInteger accountId = BigInteger.valueOf(2); //getAccountByPrincipal(principal);

        MonthReport monthReport = monthReportService.getMonthPersonalReport(accountId, dateFrom, dateTo);

        Path path = monthReportService.convertToTxt(monthReport);

        String report = null;
        try {
            report = new String(Files.readAllBytes(path.getFileName()));
        } catch (IOException e) {

        }

        logger.debug("Month report is ready");

        return report;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public PersonalDebitAccount getPersonalAccount(Principal principal) {
        return personalDebitService.getPersonalDebitAccount(getAccountByPrincipal(principal));
    }

    @RequestMapping("/layout")
    public String getPersonalAccountPartialPage() {
        return URL.PERSONAL_DEBIT;
    }

    @RequestMapping("/getReportView")
    public String getReportView() {
        return URL.REPORT_PERS;
    }

}