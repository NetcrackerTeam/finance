package com.netcracker.controllers;


import com.netcracker.controllers.validators.UserDataValidator;
import com.netcracker.dao.*;
import com.netcracker.exception.NullObjectException;
import com.netcracker.models.*;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.models.enums.UserRole;
import com.netcracker.services.*;
import com.netcracker.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.netcracker.controllers.MessageController.*;

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
    @Autowired
    PersonalDebitService personalDebitService;
    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;
    @Autowired
    EmailServiceSender emailServiceSender;
    @Autowired
    OperationDao operationDao;
    @Autowired
    private FamilyCreditService familyCreditService;

    private static final Logger logger = Logger.getLogger(FamilyDebitController.class);

    private BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getParticipantByEmail(principal.getName());
        return user.getFamilyDebitAccount();
    }

    private BigInteger getUserIdByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getId();
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    @ResponseBody
    public Status createFamilyDebitAccount(@RequestParam(value = "nameAccount") String nameAccount,
                                           Principal principal) {
        try {
            UserDataValidator.isValidNameFamily(nameAccount);
            User user = userDao.getUserByEmail(principal.getName());
            FamilyDebitAccount familyDebitAccount = new FamilyDebitAccount.Builder()
                    .debitObjectName(nameAccount)
                    .debitAmount(0)
                    .debitFamilyAccountStatus(FamilyAccountStatusActive.YES)
                    .debitOwner(user)
                    .build();
            familyDebitService.createFamilyDebitAccount(familyDebitAccount);
            userDao.updateRole(user.getId(), UserRole.OWNER.getId());
            return new Status(true, MessageController.ADD_FAMILY_ACCOUNT);
        } catch (RuntimeException ex) {
            return new Status(true, ex.getMessage());
        }
    }

    @RequestMapping(value = "/deactivation", method = RequestMethod.GET)
    @ResponseBody
    public Status deleteFamilyDebitAccount(Principal principal) {

        try {
            User user = userDao.getParticipantByEmail(principal.getName());
            FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(user.getFamilyDebitAccount());
            BigInteger userId = user.getId();
            BigInteger accountId = familyDebitAccount.getId();
            logger.debug("deactivate family account " + accountId + " user id " + userId);
            Collection<FamilyCreditAccount> credits = familyCreditService.getFamilyCredits(accountId);
            for (FamilyCreditAccount credit : credits) {
                if (CreditStatusPaid.NO == credit.isPaid()) {
                    return new Status(true, MessageController.FAMILY_ACCOUNT_HAS_NOT_PAID_CREDITS);
                }
            }
            double familyAmount = familyDebitAccount.getAmount();
            familyDebitService.deleteFamilyDebitAccount(accountId, userId);
            PersonalDebitAccount personalDebitAccount = personalDebitService.getPersonalDebitAccount(user.getPersonalDebitAccount());
            userDao.updateRole(userId, UserRole.USER.getId());
            double amount = personalDebitAccount.getAmount() + familyAmount;
            personalDebitAccountDao.updateAmountOfPersonalAccount(personalDebitAccount.getId(), amount);
            logger.debug("success deactivate");
            return new Status(true, MessageController.DEACT_FAMACC_FAM);
        } catch (RuntimeException ex) {
            return new Status(true, ex.getMessage());
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Status addUserToAccount(@RequestParam(value = "userLogin") String userLogin,
                                   Principal principal) {

        try {
            BigInteger userId = userDao.getParticipantByEmail(userLogin).getId();
            BigInteger accountId = getAccountByPrincipal(principal);
            logger.debug("add user to account " + accountId + "user id " + userId);
            familyDebitService.addUserToAccount(accountId, userId);
            logger.debug("success adding user");
            userDao.updateRole(userId, UserRole.PARTICIPANT.getId());
            return new Status(true, MessageController.ADD_USER_FAM + userDao.getUserById(userId).getName());
        } catch (RuntimeException ex) {
            return new Status(true, ex.getMessage());
        }
    }

    @RequestMapping(value = "/getParticipants", method = RequestMethod.GET)
    @ResponseBody
    public Collection<User> getParticipants(Principal principal) {

        BigInteger accountId = getAccountByPrincipal(principal);
        logger.debug("getting participants from account " + accountId);
        return familyDebitService.getParticipantsOfFamilyAccount(accountId);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public Status deleteUserFromAccount(@RequestParam(value = "userLogin") String userLogin,
                                        Principal principal) {

        BigInteger userId = userDao.getParticipantByEmail(userLogin).getId();
        BigInteger accountId = getAccountByPrincipal(principal);
        try {
            logger.debug("delete user to account " + accountId + "user id " + userId);
            familyDebitService.deleteUserFromAccount(accountId, userId);
            logger.debug("success adding user");
            userDao.updateRole(userId, UserRole.USER.getId());
            return new Status(true, MessageController.DELETE_USER_FAM + userDao.getUserById(userId).getName());
        } catch (NullObjectException ex) {
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

    @RequestMapping(value = "/income", method = RequestMethod.POST)
    @ResponseBody
    public Status addIncomeFamily(@RequestBody AccountIncome income,
                                  Principal principal) {
        double incomeAmount = income.getAmount();
        if (incomeAmount < MessageController.MIN || incomeAmount > MessageController.MAX) {
            return new Status(true, MessageController.INCORRECT_AMOUNT);
        } else {
            BigInteger accountId = getAccountByPrincipal(principal);
            BigInteger userId = getUserIdByPrincipal(principal);
            operationService.createFamilyOperationIncome(userId, accountId, income.getAmount(), LocalDate.now(), income.getCategoryIncome());
            FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(accountId);
            double amount = familyDebitAccount.getAmount() + income.getAmount();
            familyDebitService.updateAmountOfFamilyAccount(accountId, amount);
            return new Status(true, MessageController.ADD_INCOME);
        }
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    @ResponseBody
    public Status addExpenseFamily(
            @RequestBody AccountExpense expense, Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        BigInteger userId = getUserIdByPrincipal(principal);
        FamilyDebitAccount debit = familyDebitService.getFamilyDebitAccount(accountId);
        if (debit.getAmount() < expense.getAmount()) {
            return new Status(false, MessageController.NOT_ENOUGH_MONEY_MESSAGE);
        }
        operationService.createFamilyOperationExpense(userId, accountId, expense.getAmount(), LocalDate.now(), expense.getCategoryExpense());
        double amount = new BigDecimal(debit.getAmount() - expense.getAmount()).setScale(2, RoundingMode.UP).doubleValue();
        familyDebitService.updateAmountOfFamilyAccount(accountId, amount);
        return new Status(true, MessageController.ADD_EXPENSE_PERS);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    List<HistoryOperation> getHistory(Principal principal) {
        logger.debug("getHistory Personal");
        BigInteger debitId = getAccountByPrincipal(principal);
        return operationDao.getFirstFamilyHistoryByAccountId(debitId);
    }

    @RequestMapping(value = "/historyByPerio", method = RequestMethod.GET)
    public @ResponseBody
    List<HistoryOperation> getHistoryByPeriod(Principal principal,
                                              @RequestParam("period") int period
    ) {
        logger.debug("getHistory Personal");
        BigInteger debitId = getAccountByPrincipal(principal);
        return operationDao.getFamilyHistoryByAccountId(debitId, period);
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
        boolean validDate = (UserDataValidator.isValidDateForAutoOperation(autoOperationIncome));
        double incomeAmount = autoOperationIncome.getAmount();
        if (incomeAmount < MessageController.MIN || incomeAmount > MessageController.MAX) {
            return new Status(true, MessageController.INCORRECT_AMOUNT);
        } else if (validDate) {
            accountAutoOperationService.createFamilyIncomeAutoOperation(autoOperationIncome, userId, accountId);
            logger.debug("autoIncome is done!");
            return new Status(true, MessageController.ADD_AUTO_INCOME);
        }
        logger.debug("autoExpense is not valid !" + autoOperationIncome.getId() + " " + autoOperationIncome.getCategoryIncome());
        return new Status(false, NO_VALID_ADD_AUTO_INCOME );
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    @ResponseBody
    public Status createAutoExpense(@RequestBody AutoOperationExpense autoOperationExpense,
                                    Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        BigInteger userId = getUserIdByPrincipal(principal);
        boolean validDate = (UserDataValidator.isValidDateForAutoOperation(autoOperationExpense));
        if (validDate) {
            accountAutoOperationService.createFamilyExpenseAutoOperation(autoOperationExpense, userId, accountId);
            logger.debug("autoIncome is done!");
            return new Status(true, ADD_AUTO_EXPENSE);
        }
        logger.debug("autoExpense is not valid !" + autoOperationExpense.getId() + " " + autoOperationExpense.getCategoryExpense());
        return new Status(false, NO_VALID_ADD_AUTO_EXPENSE);

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

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public String getReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        BigInteger accountId = getAccountByPrincipal(principal);

        MonthReport monthReport = monthReportService.getMonthFamilyReport(accountId, date, false);

        Path path = monthReportService.convertToTxt(monthReport);

        return monthReportService.convertToString(path);
    }

    @RequestMapping(value = "/sendReport", method = RequestMethod.GET)
    public void sendReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        BigInteger accountId = getAccountByPrincipal(principal);

        MonthReport monthReport = monthReportService.getMonthFamilyReport(accountId, date, false);

        Path path;
        try {
            path = monthReportService.convertToTxt(monthReport);
            emailServiceSender.monthReport(principal.getName(), userDao.getUserByEmail(principal.getName()).getName(), path);
            logger.debug("Email have been sent");
        } catch (MessagingException e) {
            logger.debug("Email can't be sent, messaging exception", e);
        }

        logger.debug("Month report is ready");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public FamilyDebitAccount getPersonalAccount(Principal principal) {
        return familyDebitService.getFamilyDebitAccount(getAccountByPrincipal(principal));
    }

    @RequestMapping("/layout")
    public String getFamilyAccountPartialPage() {
        return URL.FAMILY_DEBIT;
    }

    @RequestMapping("/getUserControl")
    public String getParticipants() {
        return URL.PARTICIPANTS_OF_FAMILY;
    }
}
