package com.netcracker.controllers;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDate;
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
    @Autowired
    CreditAccountDao creditAccountDao;

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public Status addCreditAccount(@RequestBody PersonalCreditAccount creditAccount, Principal principal) {
        debitId = userController.getAccountByPrincipal(principal);
        personalCreditService.createPersonalCredit(debitId, creditAccount);
        return new Status(true, MessageController.ADD_CREDIT_PERS + debitId);
    }

    @RequestMapping(value = "/addPersonalCreditPayment/{creditId}", method = RequestMethod.PUT)
    public ResponseEntity<String> addPersonalCreditPayment(@PathVariable(value = "creditId") BigInteger creditId,
            @RequestParam(value = "amount") double amount, Principal principal){
        debitId = userController.getAccountByPrincipal(principal);
        PersonalCreditAccount personalCreditAccount = creditAccountDao.getPersonalCreditById(creditId);
        if (personalCreditAccount != null) {
            personalCreditService.addPersonalCreditPayment(debitId, creditId, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/getPersonalCredits", method = RequestMethod.GET)
    public @ResponseBody Collection<PersonalCreditAccount> getPersonalCredits(Principal principal){
        debitId = userController.getAccountByPrincipal(principal);
        return personalCreditService.getPersonalCredits(debitId);
    }

    @RequestMapping(value = "/getPersonalCredit/{creditId}", method = RequestMethod.GET)
    public String getPersonalCredit(@PathVariable("creditId") BigInteger creditId, Model model) {
        PersonalCreditAccount personalCreditAccount = creditAccountDao.getPersonalCreditById(creditId);
        model.addAttribute("creditName", personalCreditAccount.getName());
        model.addAttribute("creditAmount", "CREDIT AMOUNT: " + personalCreditAccount.getAmount());
        model.addAttribute("creditRate", "CREDIT RATE: " + personalCreditAccount.getCreditRate());
        model.addAttribute("creditPaidAmount", "CREDIT PAID AMOUNT: " + personalCreditAccount.getPaidAmount());
        model.addAttribute("creditDateFrom", "CREDIT START DATE: " + personalCreditAccount.getDate());
        model.addAttribute("creditDateTo", "CREDIT ENDS DATE: " + personalCreditAccount.getDateTo());
        LocalDate debtDateFrom = personalCreditAccount.getDebt().getDateFrom();
        LocalDate debtDateTo = personalCreditAccount.getDebt().getDateTo();
        if (debtDateFrom != null) model.addAttribute("creditDebtDateFrom", "CREDIT DEBT START DATE: " + debtDateFrom);
        else model.addAttribute("creditDebtDateFrom", "null");
        if (debtDateTo != null) model.addAttribute("creditDebtDateTo", "CREDIT DEBT ENDS DATE: " + debtDateTo);
        else model.addAttribute("creditDebtDateTo", "null");
        model.addAttribute("creditDebtAmount", "CREDIT DEBT AMOUNT: " + personalCreditAccount.getDebt().getAmountDebt());
        return "personalCredit/layoutCreateCredit";
    }

    @RequestMapping(value = "deletePersonalCredit/{creditId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePersonalCredit(@PathVariable("creditId") BigInteger creditId) {
        PersonalCreditAccount personalCreditAccount = creditAccountDao.getPersonalCreditById(creditId);
        if(personalCreditAccount != null){
            creditAccountDao.deletePersonalCreditAccountByCreditId(creditId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
