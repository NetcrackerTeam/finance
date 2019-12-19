package com.netcracker.controllers;

import com.netcracker.models.Status;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.PredictionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
                                        @RequestParam("duration") int duration
                                        ){
        return predictionService.predictPersonalMonthIncome(id, duration);
    }

    @RequestMapping(value = "/personal/expense", method = RequestMethod.GET)
    @ResponseBody
    public double predictPersonalExpense(@PathVariable("id") BigInteger id,
                                         @RequestParam("duration") int duration
    ){
        return predictionService.predictPersonalMonthExpense(id, duration);
    }

    @RequestMapping(value = "/family/income", method = RequestMethod.GET )
    @ResponseBody
    public double predictFamilyIncome(@PathVariable("id") BigInteger id,
                                        @RequestParam("duration") int duration
    ){
        return predictionService.predictFamilyMonthIncome(id, duration);
    }

    @RequestMapping(value = "/family/expense", method = RequestMethod.GET)
    @ResponseBody
    public double predictFamilyExpense(@PathVariable("id") BigInteger id,
                                         @RequestParam("duration") int duration
    ){
        return predictionService.predictFamilyMonthExpense(id, duration);
    }
}
