package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/personalCredit")
public class CreditPersonalController {
    @Autowired
    PersonalCreditService personalCreditService;
    @Autowired
    private UserIdController userController;
    private BigInteger debitId;
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public Status addCreditAccount(@RequestBody PersonalCreditAccount creditAccount, Principal principal) {
        debitId = userController.getAccountByPrincipal(principal);
        personalCreditService.createPersonalCredit(debitId, creditAccount);
        return new Status(true, MessageController.ADD_CREDIT_PERS + debitId);
    }

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
    public @ResponseBody Collection<PersonalCreditAccount> getPersonalCredits(Principal principal){
        debitId = userController.getAccountByPrincipal(principal);
        return personalCreditService.getPersonalCredits(debitId);
    }
}
