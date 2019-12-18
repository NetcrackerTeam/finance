package com.netcracker.controllers;

import com.google.gson.Gson;
import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.OperationService;
import com.netcracker.services.PersonalCreditService;
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
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import com.netcracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;

@Controller
@RequestMapping("/PersonalDebit")
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

    @RequestMapping(value = "/getHistory", method = RequestMethod.GET)
    public String getHistory() {
        return null;
    }

    @RequestMapping(value = "{personalId}/createAutoIncome", method = RequestMethod.POST)
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

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    public String createAutoExpense(
            @RequestParam(value = "userId") BigInteger userId,
            @RequestParam(value = "categoryExpense") CategoryExpense categoryExpense,
            @RequestParam(value = "expenseAmount") Double expenseAmount,
            @RequestParam(value = "dateExpense") LocalDate dateExpense,
            @RequestParam(value = "dayOfMonth") int dayOfMonth
    ) {
        try {
            User user = userService.getUserById(userId);
            AutoOperationExpense autoOperationExpense = new AutoOperationExpense.Builder()
                    .categoryExpense(categoryExpense)
                    .accountAmount(expenseAmount)
                    .accountDate(dateExpense)
                    .dayOfMonth(dayOfMonth)
                    .build();
            accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, user.getPersonalDebitAccount());
            logger.debug("expense is done!");
            return "expensePersonal/ready";
        } catch (UserException ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/deleteAutoIncome", method = RequestMethod.POST)
    public String deleteAutoIncome() {
        return null;
    }

    @RequestMapping(value = "/deleteAutoExpense", method = RequestMethod.POST)
    public String deleteAutoExpense() {
        return null;
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    public String getReport() {
        return null;
    }

}