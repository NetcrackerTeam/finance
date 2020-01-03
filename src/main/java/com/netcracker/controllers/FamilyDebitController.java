package com.netcracker.controllers;


import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.exception.NullObjectException;
import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryIncome;
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
@RequestMapping("/debitFamily")
public class FamilyDebitController {
    @Autowired
    FamilyDebitService familyDebitService;
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
    FamilyCreditService creditService;

    private static final Logger logger = Logger.getLogger(FamilyDebitController.class);

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    @ResponseBody
    public FamilyDebitAccount createFamilyDebitAccount(@RequestBody FamilyDebitAccount familyDebitAccount) {
        return familyDebitService.createFamilyDebitAccount(familyDebitAccount);
    }

    @RequestMapping(value = "/deactivation", method = RequestMethod.GET)
    public Status deleteFamilyDebitAccount(@PathVariable("id") BigInteger accountId,
                                           @RequestParam(value = "userId") BigInteger userId,
                                           Model model) {
        try {
            logger.debug("deactivate family account " + accountId + "user id " + userId);
            familyDebitService.deleteFamilyDebitAccount(accountId, userId);
            logger.debug("success deactivate");
            return new Status(true, "Deactivated successfully account " + accountId);
        } catch (NullObjectException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return new Status(true, "Deactivated unsuccessfully account " + accountId);
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Status addUserToAccount(@PathVariable("id") BigInteger accountId,
                                   @RequestParam(value = "userId") BigInteger userId,
                                   Model model) {
        try {
            logger.debug("add user to account " + accountId + "user id " + userId);
            familyDebitService.addUserToAccount(accountId, userId);
            logger.debug("success adding user");
            return new Status(true, "Adding successfully user " + userId + " to account " + accountId);
        } catch (NullObjectException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return new Status(true, "Adding unsuccessfully user " + userId + " to account " + accountId);
        }
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public Status deleteUserFromAccount(@PathVariable("id") BigInteger accountId,
                                        @RequestParam(value = "userId") BigInteger userId,
                                        Model model) {
        try {
            logger.debug("delete user to account " + accountId + "user id " + userId);
            familyDebitService.deleteUserFromAccount(accountId, userId);
            logger.debug("success adding user");
            return new Status(true, "Deleting successfully user " + userId + " from account " + accountId);
        } catch (NullObjectException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return new Status(true, "Deleting successfully user " + userId + " from account " + accountId);
        }
    }

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public List<FamilyCreditAccount> addCreditAccount(@RequestBody FamilyCreditAccount creditAccount,
                                                        @PathVariable("id") BigInteger id) {
        creditService.createFamilyCredit(id, creditAccount);
        return creditAccountDao.getAllFamilyCreditsByAccountId(id);
    }

    @RequestMapping(value = "/addIncome", method = RequestMethod.POST)
    @ResponseBody
    public AccountIncome addIncomeFamily(@PathVariable(value = "id") BigInteger debitId,
                                         @RequestParam(value = "userId") BigInteger userId,
                                         @RequestParam(value = "amount") double amount,
                                         @RequestParam(value = "date") LocalDate date,
                                         @RequestParam(value = "categoryIncome") CategoryIncome categoryIncome) {
        operationService.createFamilyOperationIncome(userId, debitId, amount, date, categoryIncome);
        return new AccountIncome.Builder().accountDebitId(debitId).accountAmount(amount).accountDate(date)
                .categoryIncome(categoryIncome).build();
    }

    @RequestMapping(value = "/addExpenseFamily/{afterDate}", method = RequestMethod.POST)
    @ResponseBody
    public List<AccountExpense> addExpenseFamily(
            @RequestBody AccountExpense expense,
            @PathVariable(value = "id") BigInteger debitId,
            @PathVariable(value = "afterDate") LocalDate afterDate,
            @RequestParam(value = "userId") BigInteger userId) {
        operationService.createFamilyOperationExpense(userId, debitId, expense.getAmount(), expense.getDate(), expense.getCategoryExpense());
        return operationService.getExpensesFamilyAfterDateByAccountId(debitId, afterDate);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody
    public Collection<AbstractAccountOperation> getHistory(@PathVariable("id") BigInteger familyId,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.debug("getHistory Personal");
        return familyDebitService.getHistory(familyId, date);
    }

    @RequestMapping(value = "/createAutoIncome", method = RequestMethod.POST)
    @ResponseBody
    public AutoOperationIncome createAutoIncome(@PathVariable("id") BigInteger familyId,
                                           @RequestParam(value = "userId") BigInteger userId,
                                           @RequestBody AutoOperationIncome autoOperationIncome) {
        AutoOperationIncome autoIncome = accountAutoOperationService.createFamilyIncomeAutoOperation(autoOperationIncome,userId, familyId);
        logger.debug("autoIncome is done!");
        return accountAutoOperationService.getFamilyIncomeAutoOperation(autoIncome.getId());
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    @ResponseBody
    public AutoOperationExpense createAutoExpense(@RequestBody AutoOperationExpense autoOperationExpense,
                                                  @RequestParam(value = "userId") BigInteger userId,
                                                  @PathVariable("id") BigInteger familyId
    ) {
        AutoOperationExpense autoExpense = accountAutoOperationService.createFamilyExpenseAutoOperation(autoOperationExpense, userId, familyId);
        logger.debug("expense is done!");
        return accountAutoOperationService.getFamilyExpenseAutoOperation(autoExpense.getId());
    }

    @RequestMapping(value = "/deleteAutoIncome/{incomeId}", method = RequestMethod.GET)
    public Status deleteAutoIncome(@PathVariable("incomeId") BigInteger incomeId,
                                   Model model
    ) {
        logger.debug("delete autoIncomePersonal");
        accountAutoOperationService.deleteAutoOperation(incomeId);
        model.addAttribute("incomeId", incomeId);
        return new Status(true, "Deleting successfully autoIncomeOperation " + incomeId);
    }

    @RequestMapping(value = "/deleteAutoExpense/{expenseId}", method = RequestMethod.GET)
    public Status deleteAutoExpense(@PathVariable("expenseId") BigInteger expenseId,
                                    Model model
    ) {
        logger.debug("delete autoExpensePersonal");
        accountAutoOperationService.deleteAutoOperation(expenseId);
        model.addAttribute("expenseId", expenseId);
        return new Status(true, "Deleting successfully autoExpenseOperation " + expenseId);
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    public String getReport() {
        return null;
    }

    @RequestMapping("/layout")
    public String getPersonalAccountPartialPage() {
        return URL.PERSONAL_DEBIT;
    }
}
