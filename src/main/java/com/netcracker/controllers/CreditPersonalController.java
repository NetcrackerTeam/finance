package com.netcracker.controllers;

import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.Collection;

@Controller
@RequestMapping("/personalCredit")
public class CreditPersonalController {
    @Autowired
    PersonalCreditService personalCreditService;

    @RequestMapping(value = "/addPersonalCreditPayment/{debitId}/{creditId}", method = RequestMethod.POST)
    public String addPersonalCreditPayment(
            @PathVariable(value = "debitId") BigInteger debitId,
            @PathVariable(value = "creditId") BigInteger creditId,
            @RequestParam(value = "amount") double amount, Model model){
        personalCreditService.addPersonalCreditPayment(debitId, creditId, amount);
        model.addAttribute("amount", amount);
        return "addPersonalCreditPayment";
    }

    @RequestMapping(value = "/getPersonalCredits/{debitId}", method = RequestMethod.POST)
    public Collection<PersonalCreditAccount> getPersonalCredits(@PathVariable(value = "debitId") BigInteger debitId){
        return personalCreditService.getPersonalCredits(debitId);
    }
}
