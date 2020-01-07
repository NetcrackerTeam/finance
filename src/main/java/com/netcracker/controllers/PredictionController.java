package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.exception.PredictionException;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.services.PredictionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/personal/income", method = RequestMethod.GET )
    @ResponseBody
    public double predictPersonalIncome(Principal principal,
                                        @RequestParam("duration") int duration,
                                        Model model){

        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        double amount = 0;
        try {
            amount = predictionService.predictPersonalMonthIncome(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping(value = "/personal/expense", method = RequestMethod.GET)
    @ResponseBody
    public double predictPersonalExpense(Principal principal,
                                         @RequestParam("duration") int duration,
                                         Model model){
        BigInteger accountId = getPersonalAccountByPrincipal(principal);

        double amount = 0;

        try {
            amount = predictionService.predictPersonalMonthExpense(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping(value = "/family/income", method = RequestMethod.GET )
    @ResponseBody
    public double predictFamilyIncome(Principal principal,
                                      @RequestParam("duration") int duration,
                                      Model model){
        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthIncome(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping(value = "/family/expense", method = RequestMethod.GET)
    @ResponseBody
    public double predictFamilyExpense(Principal principal,
                                       @RequestParam("duration") int duration,
                                       Model model){
        BigInteger accountId = getFamilyAccountByPrincipal(principal);

        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthExpense(accountId, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping("/predict")
    public String predict(){
        return URL.PERSONAL_DEBIT_URL;
    }
}
