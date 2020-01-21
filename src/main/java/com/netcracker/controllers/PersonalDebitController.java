package com.netcracker.controllers;

import com.netcracker.controllers.validators.UserDataValidator;
import com.netcracker.dao.*;
import com.netcracker.models.*;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.*;
import com.netcracker.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.netcracker.controllers.MessageController.*;
import static com.netcracker.controllers.UserController.getCurrentUsername;

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
    @Autowired
    OperationDao operationDao;
    @Autowired
    EmailServiceSender emailServiceSender;

    private static final Logger logger = Logger.getLogger(PersonalDebitController.class);

    @RequestMapping(value = "/income", method = RequestMethod.POST)
    @ResponseBody
    public Status addIncomePersonal(@RequestBody AccountIncome income,
                                    Principal principal) {
        double incomeAmount = income.getAmount();
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if ((incomeAmount < MessageController.MIN || incomeAmount > MessageController.MAX)) {
            return new Status(false, MessageController.INCORRECT_AMOUNT);
        } else {
            BigInteger accountId = getAccountByPrincipal(principal);
            operationService.createPersonalOperationIncome(accountId, income.getAmount(), LocalDateTime.now(), income.getCategoryIncome());
            PersonalDebitAccount debit = personalDebitAccountDao.getPersonalAccountById(accountId);
            double amount = new BigDecimal(debit.getAmount() + income.getAmount()).setScale(2, RoundingMode.UP).doubleValue();
            personalDebitAccountDao.updateAmountOfPersonalAccount(accountId, amount);
            return new Status(true, MessageController.ADD_INCOME);
        }
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    @ResponseBody
    public Status addExpensePersonal(
            @RequestBody AccountExpense expense, Principal principal) {
        double expenseAmount = expense.getAmount();
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (expenseAmount < MessageController.MIN || expenseAmount > MessageController.MAX) {
            return new Status(false, MessageController.INCORRECT_AMOUNT);
        } else {
            BigInteger accountId = getAccountByPrincipal(principal);
            PersonalDebitAccount debit = personalDebitAccountDao.getPersonalAccountById(accountId);
            if (debit.getAmount() < expense.getAmount())
                return new Status(false, MessageController.NOT_ENOUGH_MONEY_MESSAGE);
            operationService.createPersonalOperationExpense(accountId, expense.getAmount(), LocalDateTime.now(), expense.getCategoryExpense());
            double amount = new BigDecimal(debit.getAmount() - expense.getAmount()).setScale(2, RoundingMode.UP).doubleValue();
            personalDebitAccountDao.updateAmountOfPersonalAccount(accountId, amount);
            return new Status(true, MessageController.ADD_EXPENSE_PERS);
        }
    }

    private BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getPersonalDebitAccount();
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    List<HistoryOperation> getHistory(Principal principal) {
        logger.debug("getHistory Personal");
        BigInteger debitId = getAccountByPrincipal(principal);
        return operationDao.getFirstPersonalHistoryByAccountId(debitId);
    }

    @RequestMapping(value = "/historyByPerio", method = RequestMethod.GET)
    public @ResponseBody
    List<HistoryOperation> getHistoryByPeriod(Principal principal,
                                              @RequestParam("period") int period
    ) {
        logger.debug("getHistory Personal");
        BigInteger debitId = getAccountByPrincipal(principal);
        return operationDao.getHistoryByAccountId(debitId, period);
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
        double incomeAmount = autoOperationIncome.getAmount();
        boolean validDate = (UserDataValidator.isValidDateForAutoOperation(autoOperationIncome));
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if ((incomeAmount < MIN || incomeAmount > MAX)) {
            return new Status(true, INCORRECT_AMOUNT);
        } else if (validDate) {
            accountAutoOperationService.createPersonalIncomeAutoOperation(autoOperationIncome, accountId);
            logger.debug("autoIncome is done!");
            return new Status(true, ADD_AUTO_INCOME);
        }
        logger.debug("autoIncome is not valid !" + autoOperationIncome.getId() + " " + autoOperationIncome.getCategoryIncome());
        return new Status(false, INVALID_DAY_OF_MONTH);
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    public @ResponseBody
    Status createAutoExpense(@RequestBody AutoOperationExpense autoOperationExpense,
                             Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        boolean validDate = (UserDataValidator.isValidDateForAutoOperation(autoOperationExpense));
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (validDate) {
            accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, accountId);
            logger.debug("expense is done!");
            return new Status(true, ADD_AUTO_EXPENSE);
        }
        logger.debug("autoExpense is not valid !" + autoOperationExpense.getId() + " " + autoOperationExpense.getCategoryExpense());
        return new Status(false, INVALID_DAY_OF_MONTH);

    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<MonthReport> getReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
//        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
//        if (!validUserActive) {
//            return new Status(false, NOT_ACTIVE_USER);
//        } else
//        if (date.isAfter(LocalDate.now())) {
//            return new Status(false, INVALID_DATE);
//        }
        LocalDateTime dateReformat = LocalDateTime.of(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), 0, 0, 0);
        BigInteger accountId = getAccountByPrincipal(principal);

        MonthReport monthReport = monthReportService.getMonthPersonalReport(accountId, dateReformat, false);

        return new ResponseEntity<>(monthReport, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendReport", method = RequestMethod.GET)
    @ResponseBody
    public Status sendReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        if (date.isAfter(LocalDate.now())) {
            return new Status(false, INVALID_DATE);
        }
        BigInteger accountId = getAccountByPrincipal(principal);
        LocalDateTime dateReformat = LocalDateTime.of(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), 0, 0, 0);
        MonthReport monthReport = monthReportService.getMonthPersonalReport(accountId, dateReformat, false);

        Path path;
        try {
            path = monthReportService.convertToTxt(monthReport);
            emailServiceSender.monthReport(principal.getName(), userDao.getUserByEmail(principal.getName()).getName(), path);
            logger.debug("Email have been sent");
        } catch (MessagingException e) {
            logger.debug("Email can't be sent, messaging exception", e);
        }

        logger.debug("Month report is ready");
        return new Status(true, SUCCESSFUL_SENDING);
    }

    @RequestMapping(value = "/deleteAuto", method = RequestMethod.GET)
    @ResponseBody
    public Status deleteAutoOperation(@RequestParam(value = "id") BigInteger id){

        try {
            logger.debug("deleting AO with id " + id);
            accountAutoOperationService.deleteAutoOperation(id);
            logger.debug("success delete with id " + id);
            return new  Status (true, SUCCESSFUL_DELETE_AO);
        } catch (RuntimeException ex){
            return  new Status(false, ex.getMessage());
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public PersonalDebitAccount getPersonalAccount(Principal principal) {
        return personalDebitService.getPersonalDebitAccount(getAccountByPrincipal(principal));
    }

    private BigInteger getUserIdByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getId();
    }


    @RequestMapping("/layout")
    public String getPersonalAccountPartialPage() {
        return URL.PERSONAL_DEBIT;
    }

    @RequestMapping("/getReportView")
    public String getReportView() {
        return URL.REPORT_PERS;
    }

    public User getCurrentUser(){
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        return userTemp;
    }
}