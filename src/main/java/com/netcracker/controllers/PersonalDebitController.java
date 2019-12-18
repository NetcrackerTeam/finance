package com.netcracker.controllers;

import com.google.gson.Gson;
import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.services.*;
import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.UserException;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.User;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.OperationService;
import org.apache.log4j.Logger;
import com.netcracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

@Controller
@RequestMapping("/PersonalDebit")
public class PersonalDebitController {

    @Autowired
    PersonalDebitService personalDebitService;
    @Autowired
    AutoOperationDao autoOperationDao;
    @Autowired
    AccountAutoOperationService accountAutoOperationService;
    @Autowired
    UserService userService;
    @Autowired
    private OperationService operationService;

    @Autowired
    CreditAccountDao creditAccountDao;
    @Autowired
    PersonalCreditService creditService;

    @RequestMapping(value = "{id}/addCredit", method = RequestMethod.POST)
    public String addCreditAccount(@RequestBody PersonalCreditAccount creditAccount,
                                   @PathVariable("id") BigInteger id, Model model) {
        creditService.createPersonalCredit(id, creditAccount);
        Gson gson = new Gson();
        model.addAttribute("json_res", creditAccountDao.getAllFamilyCreditsByAccountId(id));
        return "test";
    }

    private static final Logger logger = Logger.getLogger(PersonalDebitController.class);

    @RequestMapping(value = "/addCreditAcc", method = RequestMethod.POST)
    public String addCreditAccount() {
        return null;
    }

    @RequestMapping(value = "/addIncomePersonal", method = RequestMethod.POST)
    public String addIncomePersonal(
    ) {
        return null;
    }

    @RequestMapping(value = "/addExpensePersonal", method = RequestMethod.POST)
    public @ResponseBody
    AccountExpense addExpensePersonal(
            @RequestParam(value = "debitId") BigInteger debitId,
            @RequestParam(value = "amount") double amount,
            @RequestParam(value = "date") LocalDate date,
            @RequestParam(value = "categoryExpense") CategoryExpense categoryExpense) {
        operationService.createPersonalOperationExpense(debitId, amount, date, categoryExpense);
        return new AccountExpense.Builder().accountDebitId(debitId).accountAmount(amount).accountDate(date)
                .categoryExpense(categoryExpense).build();
    }

    @RequestMapping(value = "{personalId}/getHistoryPersonal", method = RequestMethod.GET)
    public String getHistory(@PathVariable("personalId") BigInteger personalId,
                             @PathVariable("dateFrom") LocalDate date,
                             Model model) {
        logger.debug("getHistory Personal");
        Collection<AbstractAccountOperation> transactions = personalDebitService.getHistory(personalId, date);
        model.addAttribute("transaction", transactions);
        return "historyPersonal";
    }

    @RequestMapping(value = "{personalId}/createAutoIncomePersonal", method = RequestMethod.POST)
    public String createAutoIncome(@PathVariable("personalId") BigInteger personalId,
                                   @RequestBody AutoOperationIncome autoOperationIncome,
                                   Model model) {
        accountAutoOperationService.createPersonalIncomeAutoOperation(autoOperationIncome, personalId);
        logger.debug("autoIncome is done!");
        int dayOfMonth = autoOperationIncome.getDayOfMonth();
        Gson gson = new Gson();
        model.addAttribute("autoIncomes", accountAutoOperationService.getAllTodayOperationsPersonalIncome(dayOfMonth));
        return "success/autoIncome";
    }

    @RequestMapping(value = "{personalId}/createAutoExpensePersonal", method = RequestMethod.POST)
    public String createAutoExpense(
            @RequestBody AutoOperationExpense autoOperationExpense,
            @PathVariable("personalId") BigInteger personalId,
            Model model
    ) {
        accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, personalId);
        logger.debug("expense is done!");
        int dayOfMonth = autoOperationExpense.getDayOfMonth();
        Gson gson = new Gson();
        model.addAttribute("autoExpense", accountAutoOperationService.getAllTodayOperationsPersonalExpense(dayOfMonth));
        return "success/autoExpense";
    }

    @RequestMapping(value = "{incomeId}/deleteAutoIncomePersonal", method = RequestMethod.POST)
    public String deleteAutoIncome(@PathVariable("incomeId") BigInteger incomeId,
                                   Model model) {
        logger.debug("delete autoIncomePersonal");
        accountAutoOperationService.deleteAutoOperation(incomeId);
        model.addAttribute("incomeId", incomeId);
        return "deletePersonalAutoIncome";
    }

    @RequestMapping(value = "{expenseId}/deleteAutoExpensePersonal", method = RequestMethod.POST)
    public String deleteAutoExpense(
                @RequestBody AutoOperationExpense autoOperationExpense,
                @PathVariable("expenseId") BigInteger expenseId,
                Model model
                                    ) {
        accountAutoOperationService.deleteAutoOperation(expenseId);
        model.addAttribute("expenseId", expenseId);
        return "deleteAutoExpensePersonal";
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    public String getReport() {
        return null;
    }

}