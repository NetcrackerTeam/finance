package com.netcracker.controllers;


import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.UserDao;
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
import java.security.Principal;
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
    @Autowired
    UserDao userDao;

    private static final Logger logger = Logger.getLogger(FamilyDebitController.class);

    private BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getFamilyDebitAccount();
    }

    private BigInteger getUserIdByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getId();
    }

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
            return new Status(true, MessageController.DEACT_FAMACC_FAM + accountId);
        } catch (NullObjectException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return new Status(true, MessageController.DEACT_UNS_FAMACC_FAM + accountId);
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
            return new Status(true, MessageController.ADD_USER_FAM + userDao.getUserById(userId).getName());
        } catch (NullObjectException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return new Status(true, MessageController.ADD_UNS_USER_FAM + userDao.getUserById(userId).getName());
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
            return new Status(true, MessageController.DELETE_USER_FAM + userDao.getUserById(userId).getName());
        } catch (NullObjectException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return new Status(true, MessageController.DELETE_UNS_USER_FAM + userDao.getUserById(userId).getName());
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
    public Collection<AbstractAccountOperation> getHistory(Principal principal,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.debug("getHistory Personal");
        BigInteger debitId = getAccountByPrincipal(principal);
        return familyDebitService.getHistory(debitId, date);
    }

    @RequestMapping(value = "/autoOperationHistory", method = RequestMethod.GET)
    public @ResponseBody
    List<AbstractAutoOperation> getAutoHis(Principal principal) {
        BigInteger debitId = getAccountByPrincipal(principal);
        return accountAutoOperationService.getAllOperationsFamily(debitId);
    }

    @RequestMapping(value = "/createAutoIncome", method = RequestMethod.POST)
    @ResponseBody
    public Status createAutoIncome(@RequestBody AutoOperationIncome autoOperationIncome,
                                   Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        BigInteger userId = getUserIdByPrincipal(principal);
        accountAutoOperationService.createFamilyIncomeAutoOperation(autoOperationIncome, userId, BigInteger.valueOf(3));
        logger.debug("autoIncome is done!");
        return new Status(true, MessageController.ADD_AUTO_INCOME);
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    @ResponseBody
    public Status createAutoExpense(@RequestBody AutoOperationExpense autoOperationExpense,
                                    Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        BigInteger userId = getUserIdByPrincipal(principal);
        accountAutoOperationService.createFamilyExpenseAutoOperation(autoOperationExpense, userId, BigInteger.valueOf(3));
        logger.debug("autoIncome is done!");
        return new Status(true, MessageController.ADD_AUTO_INCOME);
    }

    @RequestMapping(value = "/deleteAutoIncome/{incomeId}", method = RequestMethod.GET)
    public Status deleteAutoIncome(@PathVariable("incomeId") BigInteger incomeId,
                                   Model model
    ) {
        logger.debug("delete autoIncomePersonal");
        accountAutoOperationService.deleteAutoOperation(incomeId);
        model.addAttribute("incomeId", incomeId);
        return new Status(true, MessageController.DELETE_AUTO_INCOME_FAM + incomeId);
    }

    @RequestMapping(value = "/deleteAutoExpense/{expenseId}", method = RequestMethod.GET)
    public Status deleteAutoExpense(@PathVariable("expenseId") BigInteger expenseId,
                                    Model model
    ) {
        logger.debug("delete autoExpensePersonal");
        accountAutoOperationService.deleteAutoOperation(expenseId);
        model.addAttribute("expenseId", expenseId);
        return new Status(true, MessageController.DELETE_AUTO_EXPENSE_FAM + expenseId);
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    public MonthReport getReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        BigInteger accountId = getAccountByPrincipal(principal);
        MonthReport monthReport = monthReportService.getMonthFamilyReport(accountId, dateFrom, dateTo);
        logger.debug("Month report is ready");
        return monthReport;
    }

    @RequestMapping("/layout")
    public String getPersonalAccountPartialPage() {
        return URL.FAMILY_DEBIT;
    }
}
