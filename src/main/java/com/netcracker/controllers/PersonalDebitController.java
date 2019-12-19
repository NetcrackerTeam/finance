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
import java.util.List;

@Controller
@RequestMapping("/personalDebit/{id}")
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
    MonthReportService monthReportService;
    @Autowired
    CreditAccountDao creditAccountDao;
    @Autowired
    PersonalCreditService creditService;

    private static final Logger logger = Logger.getLogger(PersonalDebitController.class);

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public List<PersonalCreditAccount> addCreditAccount(@RequestBody PersonalCreditAccount creditAccount,
                                   @PathVariable("id") BigInteger id) {
        creditService.createPersonalCredit(id, creditAccount);
        return creditAccountDao.getAllPersonalCreditsByAccountId(id);
    }

    @RequestMapping(value = "/addIncome", method = RequestMethod.POST)
    public String addIncomePersonal(
    ) {
        return null;
    }

    @RequestMapping(value = "/addExpense", method = RequestMethod.POST)
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

    @RequestMapping(value = "{personalId}/history", method = RequestMethod.GET)
    public @ResponseBody Collection<AbstractAccountOperation> getHistory(@PathVariable("personalId") BigInteger personalId,
                             @RequestParam("dateFrom") LocalDate date) {
        logger.debug("getHistory Personal");
        return personalDebitService.getHistory(personalId, date);
    }

    @RequestMapping(value = "{personalId}/createAutoIncome", method = RequestMethod.POST)
    public @ResponseBody AutoOperationIncome createAutoIncome(@PathVariable("personalId") BigInteger personalId,
                                   @RequestBody AutoOperationIncome autoOperationIncome) {
        AutoOperationIncome autoIncome = accountAutoOperationService.createPersonalIncomeAutoOperation(autoOperationIncome, personalId);
        logger.debug("autoIncome is done!");
        return autoIncome;
    }

    @RequestMapping(value = "{personalId}/createAutoExpense", method = RequestMethod.POST)
    public @ResponseBody AutoOperationExpense createAutoExpense(
            @RequestBody AutoOperationExpense autoOperationExpense,
            @PathVariable("personalId") BigInteger personalId
    ) {
        AutoOperationExpense autoExpense = accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, personalId);
        logger.debug("expense is done!");
        return autoExpense;
    }

    @RequestMapping(value = "/deleteAutoIncome/{incomeId}", method = RequestMethod.GET)
    public String deleteAutoIncome(@PathVariable("incomeId") BigInteger incomeId,
                                   Model model
    ) {
        logger.debug("delete autoIncomePersonal");
        accountAutoOperationService.deleteAutoOperation(incomeId);
        model.addAttribute("incomeId", incomeId);
        return "personal/deletePersonalAutoIncome";
    }

    @RequestMapping(value = "/deleteAutoExpense/{expenseId}", method = RequestMethod.DELETE)
    public String deleteAutoExpense(
                @PathVariable("expenseId") BigInteger expenseId,
                Model model
    ) {
        logger.debug("delete autoExpensePersonal");
        accountAutoOperationService.deleteAutoOperation(expenseId);
        model.addAttribute("expenseId", expenseId);
        return "personal/deleteAutoExpensePersonal";
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    public String getReport() {
        return null;
    }

}