package com.netcracker.controllers;

import com.netcracker.exception.PredictionException;
import com.netcracker.models.Status;
import com.netcracker.services.PredictionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/prediction/{id}")
public class PredictionController {

    private static final Logger logger = Logger.getLogger(PredictionController.class);

    @Autowired
    PredictionService predictionService;

    @RequestMapping(value = "/personal/income", method = RequestMethod.GET )
    @ResponseBody
    public double predictPersonalIncome(@PathVariable("id") BigInteger id,
                                        @RequestParam("duration") int duration,
                                        Model model){
        double amount = 0;
        try {
            amount = predictionService.predictPersonalMonthIncome(id, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping(value = "/personal/expense", method = RequestMethod.GET)
    @ResponseBody
    public double predictPersonalExpense(@PathVariable("id") BigInteger id,
                                         @RequestParam("duration") int duration,
                                         Model model){
        double amount = 0;

        try {
            amount = predictionService.predictPersonalMonthExpense(id, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping(value = "/family/income", method = RequestMethod.GET )
    @ResponseBody
    public double predictFamilyIncome(@PathVariable("id") BigInteger id,
                                      @RequestParam("duration") int duration,
                                      Model model){
        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthIncome(id, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping(value = "/family/expense", method = RequestMethod.GET)
    @ResponseBody
    public double predictFamilyExpense(@PathVariable("id") BigInteger id,
                                       @RequestParam("duration") int duration,
                                       Model model){
        double amount = 0;

        try {
            amount = predictionService.predictFamilyMonthExpense(id, duration);
        } catch (PredictionException e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("status", new Status(false, e.getMessage()));
        }
        return amount;
    }

    @RequestMapping("/predict")
    public String predict(){
        return "personalDebit/layoutPrediction";
    }
}
