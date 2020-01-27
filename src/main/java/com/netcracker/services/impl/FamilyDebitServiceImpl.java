package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.OperationDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import com.netcracker.services.FamilyDebitService;
import com.netcracker.services.OperationService;
import com.netcracker.services.PersonalDebitService;
import com.netcracker.services.UserService;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class FamilyDebitServiceImpl implements FamilyDebitService {

    @Autowired
    private FamilyAccountDebitDao familyAccountDebitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OperationService operationService;
    @Autowired
    private UserService userService;
    @Autowired
    private MonthReportDao monthReportDao;
    @Autowired
    private PersonalDebitService personalDebitService;
    @Autowired
    private OperationDao operationDao;

    private static final Logger logger = Logger.getLogger(FamilyDebitServiceImpl.class);

    @Override
    public FamilyDebitAccount createFamilyDebitAccount(FamilyDebitAccount familyDebitAccount) {
        logger.debug("Entering insert(createFamilyDebitAccount=" + familyDebitAccount + ")");
        ObjectsCheckUtils.isNotNull(familyDebitAccount);
        return familyAccountDebitDao.createFamilyAccount(familyDebitAccount);
    }

    @Override
    public FamilyDebitAccount getFamilyDebitAccount(BigInteger id) {
        logger.debug("Entering select(getFamilyDebitAccount=" + id + ")");
        ObjectsCheckUtils.isNotNull(id);
        FamilyDebitAccount debitAccount = familyAccountDebitDao.getFamilyAccountById(id);
        LocalDateTime startMonthDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
        Collection<AccountIncome> incomes = operationService.getIncomesFamilyAfterDateByAccountId(id, startMonthDate);
        Collection<AccountExpense> expenses = operationService.getExpensesFamilyAfterDateByAccountId(id, startMonthDate);
        debitAccount.setMonthIncome(incomes.stream().mapToDouble(AccountIncome::getAmount).sum());
        debitAccount.setMonthExpense(expenses.stream().mapToDouble(AccountExpense::getAmount).sum());
        return debitAccount;
    }

    @Override
    public void deleteFamilyDebitAccount(BigInteger accountId, BigInteger userId) {
        Collection<User> participants = this.getParticipantsOfFamilyAccount(accountId);
        if (participants.size() == 1) {
            logger.debug("Entering update(deleteFamilyDebitAccount=" + accountId + ")");
            familyAccountDebitDao.deleteFamilyAccount(accountId, userId);
        } else {
            logger.error("the family debit account " + accountId + " has participants");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_FAMILY_PARTICIPANTS);
        }
    }

    @Override
    public boolean addUserToAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering insert(addUserToAccount=" + accountId + " " + userId + ")");
        FamilyDebitAccount familyDebitAccount = this.getFamilyDebitAccount(accountId);
        FamilyAccountStatusActive statusFamily = familyDebitAccount.getStatus();
        User tempUser = userDao.getUserById(userId);
        boolean statusUser = userService.isUserActive(userId);
        boolean userFamilyAccount = userService.isUserHaveFamilyAccount(userId);
        if (!statusUser) {
            logger.error("The user " + userId + " is unActive");
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER_STATUS, tempUser);
        } else if (FamilyAccountStatusActive.NO == statusFamily) {
            logger.error("The family account " + accountId + " is unActive");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_FAMILY_STATUS, familyDebitAccount);
        } else if (userFamilyAccount) {
            logger.error("The user " + tempUser + " own family account");
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER_EXIST_FAMILY, tempUser);
        } else if (this.isUserParticipant(userId)) {
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER_EXIST_FAMILY, tempUser);
        } else {
            familyAccountDebitDao.addUserToAccountById(accountId, userId);
            logger.debug("Entering insert success(addUserToAccount=" + accountId + " " + userId + ")");
            return true;
        }
    }

    @Override
    public void deleteUserFromAccount(BigInteger accountId, BigInteger userId) {
        logger.debug("Entering update(deleteUserFromAccount=" + accountId + " " + userId + ")");
        ObjectsCheckUtils.isNotNull(accountId, userId);
        User user = userDao.getUserById(userId);
        if (!accountId.equals(user.getFamilyDebitAccount())) {
            familyAccountDebitDao.deleteUserFromAccountById(accountId, userId);
        } else {
            logger.error("The user " + userId + "is owner of family account " + accountId);
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_OWNER);
        }
    }

    @Override
    public List<AbstractAccountOperation> getHistory(BigInteger familyAccountId, LocalDateTime date) {
        logger.debug("Entering select(getHistory=" + familyAccountId + " " + date + ")");
        return operationService.getAllFamilyOperations(familyAccountId, date);
    }

    @Override
    public Collection<User> getParticipantsOfFamilyAccount(BigInteger accountId) {
        ObjectsCheckUtils.isNotNull(accountId);
        logger.debug("Entering list(getParticipantsOfFamilyAccount=" + accountId + ")");
        return familyAccountDebitDao.getParticipantsOfFamilyAccount(accountId);
    }

    @Override
    public boolean isUserParticipant(BigInteger userId) {
        logger.debug("Entering user is participant(isUserParticipant " + userId + ")");
        Collection<User> participants = familyAccountDebitDao.getAllParticipantsOfFamilyAccounts();
        for (User participant : participants) {
            if (participant.getId() == null) {
                logger.error("The userId " + userId + " is NULL");
                throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER, participant);
            } else if (userId.equals(participant.getId())) {
                logger.error("The user " + participant.getId() + " is has family account");
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<FamilyDebitAccount> getAllFamilyAccounts() {
        return familyAccountDebitDao.getAllFamilyAccounts();
    }

    @Override
    public void updateAmountOfFamilyAccount(BigInteger accountId, double amount) {
        ObjectsCheckUtils.isNotNull(accountId, amount);
        if (amount < 0){
            logger.error("The amount of family debit account " + accountId + " less than 0");
            throw new FamilyDebitAccountException(ExceptionMessages.ERROR_MESSAGE_ILLEGAL_AMOUNT);
        } else {
            familyAccountDebitDao.updateAmountOfFamilyAccount(accountId, amount);
        }
    }

    @Override
    public List<ChartItem> getMonthData(BigInteger accountId) {
        List<MonthReport> monthReports = new ArrayList<>(monthReportDao.getFullFamilyReports(accountId));
        Locale locale = Locale.getDefault();
        FamilyDebitAccount debitAccount = getFamilyDebitAccount(accountId);
        List<ChartItem> chartItems = personalDebitService.genChartListFromReports(monthReports, locale);

        if (chartItems.size() > 2) {
            chartItems.get(chartItems.size() - 3).setAmountExp(debitAccount.getMonthExpense());
            chartItems.get(chartItems.size() - 3).setAmountInc(debitAccount.getMonthIncome());
            return chartItems;
        } else {
            chartItems.add(new ChartItem(LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, locale), debitAccount.getMonthExpense(), debitAccount.getMonthIncome()));
            return chartItems;
        }
    }

    @Override
    public Collection<DonutChartItem> getMonthExpenseList(BigInteger accountId) {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesFamilyGroupByCategoriesWithoutUser(accountId, startDate);
        return personalDebitService.genExpenseList(expenseReports);
    }

    @Override
    public Collection<DonutChartItem> getMonthIncomeList(BigInteger accountId) {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesFamilyGroupByCategoriesWithoutUser(accountId, startDate);
        return personalDebitService.genIncomeList(incomeReports);
    }
}