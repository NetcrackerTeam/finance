package com.netcracker.controllers;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.UserException;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.User;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.services.AccountAutoOperationService;
import com.netcracker.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    private static final Logger logger = Logger.getLogger(PersonalDebitController.class);

    @RequestMapping(value = "/addCreditAcc", method = RequestMethod.POST )
    public String addCreditAccount(){
        return null;
    }

    @RequestMapping(value = "/addIncomePersonal", method = RequestMethod.POST )
    public String addIncomePersonal(
    ){
        return null;
    }

    @RequestMapping(value = "/addExpensePersonal", method = RequestMethod.POST )
    public String addExpensePersonal(){
        return null;
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.GET )
    public String getHistory(){
        return null;
    }

    @RequestMapping(value = "/createAutoIncome", method = RequestMethod.POST )
    public String createAutoIncome(@RequestParam(value = "userId") BigInteger userId,
                                   @RequestParam(value = "categoryIncome") CategoryExpense categoryIncome,
                                   @RequestParam(value = "incomeAmount") Double incomeAmount,
                                   @RequestParam(value = "dateIncome") LocalDate dateincome,
                                   @RequestParam(value = "dayOfMonth") int dayOfMonth){
        try {
            User user = userService.getUserById(userId);
            BigInteger personalId = user.getPersonalDebitAccount();
            AutoOperationExpense autoOperationExpense = new AutoOperationExpense.Builder()
                    .categoryExpense(categoryIncome)
                    .accountAmount(incomeAmount)
                    .accountDate(dateincome)
                    .dayOfMonth(dayOfMonth)
                    .build();
            accountAutoOperationService.createPersonalExpenseAutoOperation(autoOperationExpense, personalId);
            logger.debug("autoIncome is done!");
            return "success/autoIncome";
        } catch (UserException ex){
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/createAutoExpense", method = RequestMethod.POST )
    public String createAutoExpense(
            @RequestParam(value = "userId") BigInteger userId,
            @RequestParam(value = "categoryExpense") CategoryExpense categoryExpense,
            @RequestParam(value = "expenseAmount") Double expenseAmount,
            @RequestParam(value = "dateExpense") LocalDate dateExpense,
            @RequestParam(value = "dayOfMonth") int dayOfMonth
    ){
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
    }


    @RequestMapping(value = "/deleteAutoIncome", method = RequestMethod.POST )
    public String deleteAutoIncome(){
        return null;
    }

    @RequestMapping(value = "/deleteAutoExpense", method = RequestMethod.POST )
    public String deleteAutoExpense(){
        return null;
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST )
    public String getReport(){
        return null;
    }

}