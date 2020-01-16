package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.Status;
import com.netcracker.models.TwoValue;
import com.netcracker.models.User;
import com.netcracker.services.PredictionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.Principal;

@Controller
@RequestMapping("/prediction")
public class PredictionController {

    private static final Logger logger = Logger.getLogger(PredictionController.class);

    @Autowired
    PredictionService predictionService;

    @Autowired
    UserDao userDao;

    private BigInteger getPersonalAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getPersonalDebitAccount();
    }

    private BigInteger getFamilyAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getFamilyDebitAccount();
    }

    @RequestMapping(value = "/personal/checkCredit")
    @ResponseBody
    public Status checkPersonalCreditPossibility(Principal principal,
                                                  @RequestParam("duration") int duration,
                                                  @RequestParam("amount") double amount,
                                                  @RequestParam("rate") double rate)
    {

        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        boolean check;
        try{
            check = predictionService.predictPersonalCreditPossibility(accountId, duration, amount, rate);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new Status(false, e.getMessage());
        }
        if (check) {
            return new Status(true, MessageController.ABLE_TO_PAY);
        } else {
            return new Status(true, MessageController.NOT_ABLE_TO_PAY);
        }
    }

    @RequestMapping(value = "/family/checkCredit")
    @ResponseBody
    public Status checkFamilyCreditPossibility(Principal principal,
                                                                 @RequestParam("duration") int duration,
                                                                 @RequestParam("amount") double amount,
                                                                 @RequestParam("rate") double rate){

        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        boolean check;
        try{
            check = predictionService.predictFamilyCreditPossibility(accountId, duration, amount, rate);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new Status(false, e.getMessage());
        }
        if (check) {
            return new Status(true, MessageController.ABLE_TO_PAY);
        } else {
            return new Status(true, MessageController.NOT_ABLE_TO_PAY);
        }
    }

    @RequestMapping(value = "/personal/income", method = RequestMethod.GET )
    @ResponseBody
    public Status predictPersonalIncome(Principal principal,
                                          Model model,
                                          @RequestParam("duration") int duration){
        if(duration < 1 || duration > 24) {
            return new Status(false, MessageController.INVALID_DURATION);
        }
        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        double amount = 0;
        try {
            amount = predictionService.predictPersonalMonthIncome(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new Status(true, e.getMessage());
        }
        return new Status(true, String.valueOf(amount));
    }

    @RequestMapping(value = "/personal/expense", method = RequestMethod.GET)
    @ResponseBody
    public Status predictPersonalExpense(Principal principal,
                                         @RequestParam("duration") int duration){
        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        if(duration < 1 || duration > 24) {
            return new Status(false, MessageController.INVALID_DURATION);
        }

        double amount = 0;

        try {
            amount = predictionService.predictPersonalMonthExpense(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new Status(false, e.getMessage());
        }
        return new Status(true, String.valueOf(amount));
    }

    @RequestMapping(value = "/family/income", method = RequestMethod.GET )
    @ResponseBody
    public Status predictFamilyIncome(Principal principal,
                                      @RequestParam("duration") int duration){

        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        if(duration < 1 || duration > 24) {
            return new Status(false, MessageController.INVALID_DURATION);
        }

        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthIncome(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new Status(false, e.getMessage());
        }
        return new Status(true, String.valueOf(amount));
    }

    @RequestMapping(value = "/family/expense", method = RequestMethod.GET)
    @ResponseBody
    public Status predictFamilyExpense(Principal principal,
                                       @RequestParam("duration") int duration){
        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        if(duration < 1 || duration > 24) {
            return new Status(false, MessageController.INVALID_DURATION);
        }

        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthExpense(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new Status(false, MessageController.INVALID_DURATION);
        }
        return new Status(true, String.valueOf(amount));
    }
}
