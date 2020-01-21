package com.netcracker.controllers;


import com.netcracker.controllers.validators.UserDataValidator;
import com.netcracker.dao.*;
import com.netcracker.exception.NullObjectException;
import com.netcracker.models.*;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.models.enums.UserRole;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.*;
import com.netcracker.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.netcracker.controllers.MessageController.*;
import static com.netcracker.controllers.UserController.getCurrentUsername;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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
    @Autowired
    LoginController loginController;

    private static final Logger logger = Logger.getLogger(FamilyDebitController.class);

    private BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getParticipantByEmail(principal.getName());
        return user.getFamilyDebitAccount();
    }

    private BigInteger getUserIdByPrincipal(Principal principal) {
        User user = userDao.getParticipantByEmail(principal.getName());
        return user.getId();
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    @ResponseBody
    public Status createFamilyDebitAccount(@RequestParam(value = "nameAccount") String nameAccount,
                                           Principal principal, HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean validStatusActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
            if (!validStatusActive) {
                return new Status(true, NOT_ACTIVE_USER);
            } else {
                UserDataValidator.isValidNameFamily(nameAccount);
                BigInteger accountId = getCurrentUser().getFamilyDebitAccount();
                if (accountId != null){
                    return new Status(false, HAVE_FAMILY_ACCOUNT);
                } else {
                    User user = userDao.getUserByEmail(principal.getName());
                    FamilyDebitAccount familyDebitAccount = new FamilyDebitAccount.Builder()
                            .debitObjectName(nameAccount)
                            .debitAmount(0)
                            .debitFamilyAccountStatus(FamilyAccountStatusActive.YES)
                            .debitOwner(user)
                            .build();
                    familyDebitService.createFamilyDebitAccount(familyDebitAccount);
                    userDao.updateRole(user.getId(), UserRole.OWNER.getId());
                    loginController.logoutPage(request, response);
                    return new Status(true, MessageController.ADD_FAMILY_ACCOUNT);
                }
            }
        } catch (RuntimeException ex) {
            return new Status(false, ex.getMessage());
        }
    }

    @RequestMapping(value = "/deactivation", method = RequestMethod.GET)
    @ResponseBody
    public Status deleteFamilyDebitAccount(Principal principal, HttpServletRequest request, HttpServletResponse response) {

        try {
            User user = userDao.getParticipantByEmail(principal.getName());
            FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(user.getFamilyDebitAccount());
            BigInteger userId = user.getId();
            BigInteger accountId = familyDebitAccount.getId();
            logger.debug("deactivate family account " + accountId + " user id " + userId);
            Collection<FamilyCreditAccount> credits = familyCreditService.getFamilyCredits(accountId);
            for (FamilyCreditAccount credit : credits) {
                if (CreditStatusPaid.NO == credit.isPaid()) {
                    return new Status(false, MessageController.FAMILY_ACCOUNT_HAS_NOT_PAID_CREDITS);
                }
            }
            double familyAmount = familyDebitAccount.getAmount();
            familyDebitService.deleteFamilyDebitAccount(accountId, userId);
            PersonalDebitAccount personalDebitAccount = personalDebitService.getPersonalDebitAccount(user.getPersonalDebitAccount());
            userDao.updateRole(userId, UserRole.USER.getId());
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            logger.error(auth.getAuthorities().toString());
            double amount = personalDebitAccount.getAmount() + familyAmount;
            personalDebitAccountDao.updateAmountOfPersonalAccount(personalDebitAccount.getId(), amount);
            logger.debug("success deactivate");
            loginController.logoutPage(request, response);
            return new Status(true, MessageController.DEACT_FAMACC_FAM);
        } catch (RuntimeException ex) {
            return new Status(false, ex.getMessage());
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Status addUserToAccount(@RequestParam(value = "userLogin") String userLogin,
                                   Principal principal) {

        try {
            BigInteger userId = userDao.getParticipantByEmail(userLogin).getId();
            User userTemp = userDao.getUserById(userId);
            boolean validStatusActive = UserStatusActive.YES.equals(userTemp.getUserStatusActive());
            if (!validStatusActive) {
                return new Status(true, NOT_ACTIVE_USER);
            } else {
                BigInteger accountId = getAccountByPrincipal(principal);
                logger.debug("add user to account " + accountId + "user id " + userId);
                familyDebitService.addUserToAccount(accountId, userId);
                logger.debug("success adding user");
                userDao.updateRole(userId, UserRole.PARTICIPANT.getId());
                return new Status(true, ADD_USER_FAM + userDao.getUserById(userId).getName());
            }
        } catch (RuntimeException ex) {
            return new Status(false, ex.getMessage());
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
            return new Status(true, DELETE_USER_FAM + userDao.getUserById(userId).getName());
        } catch (RuntimeException ex) {
            return new Status(false, DELETE_UNS_USER_FAM + userDao.getUserById(userId).getName());
        }
    }

    @RequestMapping(value = "/income", method = RequestMethod.POST)
    @ResponseBody
    public Status addIncomeFamily(@RequestBody AccountIncome income,
                                  Principal principal) {
        double incomeAmount = income.getAmount();
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (incomeAmount < MIN || incomeAmount > MAX) {
            return new Status(false, INCORRECT_AMOUNT);
        } else {
            BigInteger accountId = getAccountByPrincipal(principal);
            BigInteger userId = getUserIdByPrincipal(principal);
            operationService.createFamilyOperationIncome(userId, accountId, income.getAmount(), LocalDateTime.now(), income.getCategoryIncome());
            FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(accountId);
            double amount = new BigDecimal(familyDebitAccount.getAmount() + income.getAmount()).setScale(2, RoundingMode.UP).doubleValue();
            familyDebitService.updateAmountOfFamilyAccount(accountId, amount);
            return new Status(true, ADD_INCOME);
        }
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    @ResponseBody
    public Status addExpenseFamily(
            @RequestBody AccountExpense expense, Principal principal) {
        double expenseAmount = expense.getAmount();
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (expenseAmount < MIN || expenseAmount > MAX) {
            return new Status(false, INCORRECT_AMOUNT);
        } else {
            BigInteger accountId = getAccountByPrincipal(principal);
            BigInteger userId = getUserIdByPrincipal(principal);
            FamilyDebitAccount debit = familyDebitService.getFamilyDebitAccount(accountId);
            if (debit.getAmount() < expenseAmount) {
                return new Status(false, NOT_ENOUGH_MONEY_MESSAGE);
            }
            operationService.createFamilyOperationExpense(userId, accountId, expense.getAmount(), LocalDateTime.now(), expense.getCategoryExpense());
            double amount = new BigDecimal(debit.getAmount() - expense.getAmount()).setScale(2, RoundingMode.UP).doubleValue();
            familyDebitService.updateAmountOfFamilyAccount(accountId, amount);
            return new Status(true, ADD_EXPENSE_PERS);
        }
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
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (incomeAmount < MIN || incomeAmount > MAX) {
            return new Status(true, INCORRECT_AMOUNT);
        } else if (validDate) {
            accountAutoOperationService.createFamilyIncomeAutoOperation(autoOperationIncome, userId, accountId);
            logger.debug("autoIncome is done!");
            return new Status(true, ADD_AUTO_INCOME);
        }
        logger.debug("autoExpense is not valid !" + autoOperationIncome.getId() + " " + autoOperationIncome.getCategoryIncome());
        return new Status(false, INVALID_DAY_OF_MONTH);
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST)
    @ResponseBody
    public Status createAutoExpense(@RequestBody AutoOperationExpense autoOperationExpense,
                                    Principal principal) {
        BigInteger accountId = getAccountByPrincipal(principal);
        BigInteger userId = getUserIdByPrincipal(principal);
        boolean validDate = (UserDataValidator.isValidDateForAutoOperation(autoOperationExpense));
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (validDate) {
            accountAutoOperationService.createFamilyExpenseAutoOperation(autoOperationExpense, userId, accountId);
            logger.debug("autoIncome is done!");
            return new Status(true, ADD_AUTO_EXPENSE);
        }
        logger.debug("autoExpense is not valid !" + autoOperationExpense.getId() + " " + autoOperationExpense.getCategoryExpense());
        return new Status(false, INVALID_DAY_OF_MONTH);

    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public Status getReport(
            Principal principal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        boolean validUserActive = UserStatusActive.YES.equals(getCurrentUser().getUserStatusActive());
        if (!validUserActive) {
            return new Status(false, NOT_ACTIVE_USER);
        } else if (date.isAfter(LocalDate.now())) {
            return new Status(false, INVALID_DATE);
        }
        BigInteger accountId = getAccountByPrincipal(principal);

        LocalDateTime dateReformat = LocalDateTime.of(date.getYear(), date.getMonth().getValue(),
                date.getDayOfMonth(), 0, 0, 0);
        MonthReport monthReport = monthReportService.getMonthFamilyReport(accountId, dateReformat, false);

        Path path = monthReportService.convertToTxt(monthReport);

        return new Status(true, monthReportService.convertToString(path));
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
        LocalDateTime dateReformat = LocalDateTime.of(date.getYear(), date.getMonth().getValue(),
                date.getDayOfMonth(), 0, 0, 0);
        MonthReport monthReport = monthReportService.getMonthFamilyReport(accountId, dateReformat, false);

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

    public User getCurrentUser() {
        User userTemp = userDao.getParticipantByEmail(getCurrentUsername());
        return userTemp;
    }
}
