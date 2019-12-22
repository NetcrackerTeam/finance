package com.netcracker.controllers;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.*;
import com.netcracker.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/debit/personal/{id}")
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

    private static final Logger logger = Logger.getLogger(PersonalDebitController.class);

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public List<PersonalCreditAccount> addCreditAccount(@RequestBody PersonalCreditAccount creditAccount,
                                                        @PathVariable("id") BigInteger id) {
        creditService.createPersonalCredit(id, creditAccount);
        return creditAccountDao.getAllPersonalCreditsByAccountId(id);
    }

    @RequestMapping(value = "/addIncome", method = RequestMethod.POST)
    public Status addIncomePersonal(@RequestBody AccountIncome income,
                                    @PathVariable("id") BigInteger id) {
        operationService.createPersonalOperationIncome(id, income.getAmount(), income.getDate(), income.getCategoryIncome());
        PersonalDebitAccount debit = personalDebitAccountDao.getPersonalAccountById(id);
        double amount = debit.getAmount() + income.getAmount();
        personalDebitAccountDao.updateAmountOfPersonalAccount(id, amount);
        return new Status(true, "Added new income by account " + id);
    }

    @RequestMapping(value = "/addExpensePersonal/{debitId}/{afterDate}", method = RequestMethod.POST)
    public @ResponseBody List<AccountExpense> addExpensePersonal(
            @RequestBody AccountExpense expense,
            @PathVariable(value = "debitId") BigInteger debitId,
            @PathVariable(value = "afterDate") LocalDate afterDate) {
        operationService.createPersonalOperationExpense(debitId, expense.getAmount(), expense.getDate(), expense.getCategoryExpense());
        return operationService.getExpensesPersonalAfterDateByAccountId(debitId, afterDate);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    Collection<AbstractAccountOperation> getHistory(@PathVariable("id") BigInteger personalId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.debug("getHistory Personal");
        return personalDebitService.getHistory(personalId, date);
    }

    @RequestMapping(value = "/createAutoIncome", method = RequestMethod.POST)
    public @ResponseBody AutoOperationIncome createAutoIncome(@PathVariable("id") BigInteger id,
                                   @RequestBody AutoOperationIncome autoOperationIncome) {
       AutoOperationIncome autoOperationIncome1 = accountAutoOperationService.createPersonalIncomeAutoOperation(autoOperationIncome, id);
        logger.debug("autoIncome is done!");
        return accountAutoOperationService.getPersonalIncomeAutoOperation(autoOperationIncome1.getId());
    }

        @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    public @ResponseBody AutoOperationExpense createAutoExpense(
            @RequestBody AutoOperationExpense autoOperationExpense,
            @PathVariable("id") BigInteger id
    ) {
       AutoOperationExpense accountExpense = accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, id);
        logger.debug("expense is done!");
        return accountAutoOperationService.getPersonalExpenseAutoOperation(accountExpense.getId());
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

    @RequestMapping(value = "/Report", method = RequestMethod.GET)
    @ResponseBody
    public MonthReport getReport(
            @PathVariable("id") BigInteger personalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
            ) {
       MonthReport monthReport = monthReportService.getMonthPersonalReport(personalId, dateFrom, dateTo);
        logger.debug("Month report is ready");
        return monthReport;
    }

}