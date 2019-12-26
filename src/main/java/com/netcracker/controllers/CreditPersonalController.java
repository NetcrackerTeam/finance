package com.netcracker.controllers;

import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

@Controller
@RequestMapping("/personalCredit")
public class CreditPersonalController {
    @Autowired
    PersonalCreditService personalCreditService;

    @RequestMapping(value = "{debitId}/addPersonalCreditPayment/{creditId}", method = RequestMethod.POST)
    public String addPersonalCreditPayment(
            @PathVariable(value = "debitId") BigInteger debitId,
            @PathVariable(value = "creditId") BigInteger creditId,
            @RequestParam(value = "amount") double amount, Model model){
        personalCreditService.addPersonalCreditPayment(debitId, creditId, amount);
        model.addAttribute("amount", amount);
        return "personalCredit/layoutAddPersonalCreditPayment";
    }

    @RequestMapping(value = "/getPersonalCredits", method = RequestMethod.GET)
    public @ResponseBody Collection<PersonalCreditAccount> getPersonalCredits(@RequestParam(value = "debitId") BigInteger debitId){
        return personalCreditService.getPersonalCredits(debitId);
    }

}
