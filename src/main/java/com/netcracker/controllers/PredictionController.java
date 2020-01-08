package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.Status;
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
    public ResponseEntity<String> checkPersonalCreditPossibility(Principal principal,
                                                  @RequestParam("duration") int duration,
                                                  @RequestParam("amount") double amount){

        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        boolean check;
        try{
            check = predictionService.predictPersonalCreditPossibility(accountId, duration, amount);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        if (check) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/family/checkCredit")
    @ResponseBody
    public ResponseEntity<String> checkFamilyCreditPossibility(Principal principal,
                                                                 @RequestParam("duration") int duration,
                                                                 @RequestParam("amount") double amount){

        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        boolean check;
        try{
            check = predictionService.predictFamilyCreditPossibility(accountId, duration, amount);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        if (check) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/personal/income", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<Double> predictPersonalIncome(Principal principal,
                                                @RequestParam("duration") int duration){

        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        double amount = 0;
        try {
            amount = predictionService.predictPersonalMonthIncome(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(amount, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = "/personal/expense", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Double> predictPersonalExpense(Principal principal,
                                         @RequestParam("duration") int duration){
        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        double amount = 0;

        try {
            amount = predictionService.predictPersonalMonthExpense(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(amount, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = "/family/income", method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<Double> predictFamilyIncome(Principal principal,
                                      @RequestParam("duration") int duration){
        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthIncome(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(amount, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }

    @RequestMapping(value = "/family/expense", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Double> predictFamilyExpense(Principal principal,
                                       @RequestParam("duration") int duration,
                                       Model model){
        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthExpense(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(amount, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }
}
