package com.netcracker.controllers;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.Status;
import com.netcracker.services.PersonalCreditService;
import org.apache.log4j.Logger;
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
import java.util.Map;

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
    private BigInteger creditId;

    private static final Logger logger = Logger.getLogger(CreditPersonalController.class);

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public Status createCreditAccount(@RequestBody PersonalCreditAccount creditAccount, Principal principal) {
        debitId = userController.getAccountByPrincipal(principal);
        logger.debug("[createCreditAccount]" + MessageController.debugStartMessage + "[debitId = " + debitId + "]");
        personalCreditService.createPersonalCredit(debitId, creditAccount);
        return new Status(true, MessageController.ADD_PERSONAL_CREDIT + creditAccount.getName());
    }

    @RequestMapping(value = "/addPersonalCreditPayment", method = RequestMethod.POST)
    public String addPersonalCreditPayment(@ModelAttribute @RequestParam Map<String, String> amountMap, Principal principal, Model model){
        debitId = userController.getAccountByPrincipal(principal);
        logger.debug("[addPersonalCreditPayment]" + MessageController.debugStartMessage  + "[debitId = " + debitId + "], [creditId = " + creditId + "]");
        double amount = Double.parseDouble(amountMap.get("amount"));
        model.addAttribute("creditPayment", amount);
        personalCreditService.addPersonalCreditPayment(debitId, creditId, amount);
        return URL.PERSONAL_CREDIT;
    }

    @RequestMapping(value = "/getPersonalCredits", method = RequestMethod.GET)
    public @ResponseBody Collection<PersonalCreditAccount> getPersonalCredits(Principal principal){
        debitId = userController.getAccountByPrincipal(principal);
        logger.debug("[getPersonalCredits]" + MessageController.debugStartMessage  + "[debitId = " + debitId + "]");
        return personalCreditService.getPersonalCredits(debitId);
    }

    @RequestMapping(value = "/getPersonalCredit/{creditId}", method = RequestMethod.GET)
    public String getPersonalCredit(@PathVariable("creditId") BigInteger creditId, Model model) {
        PersonalCreditAccount personalCreditAccount = creditAccountDao.getPersonalCreditById(creditId);
        this.creditId = creditId;
        logger.debug("[getPersonalCredit]" + MessageController.debugStartMessage  + "[debitId = " + debitId + "], [creditId = " + creditId + "]");
        model.addAttribute("creditName", personalCreditAccount.getName());
        model.addAttribute("creditAmount", "CREDIT AMOUNT: " + personalCreditAccount.getAmount());
        model.addAttribute("creditRate", "CREDIT RATE: " + personalCreditAccount.getCreditRate());
        model.addAttribute("creditPaidAmount", "CREDIT PAID AMOUNT: " + personalCreditAccount.getPaidAmount());
        model.addAttribute("creditDateFrom", "CREDIT START DATE: " + personalCreditAccount.getDate());
        model.addAttribute("creditDateTo", "CREDIT ENDS DATE: " + personalCreditAccount.getDateTo());
        model.addAttribute("creditMonthDay", "CREDIT MONTH DAY: " + personalCreditAccount.getMonthDay());
        LocalDate debtDateFrom = personalCreditAccount.getDebt().getDateFrom();
        LocalDate debtDateTo = personalCreditAccount.getDebt().getDateTo();
        if (debtDateFrom != null) model.addAttribute("creditDebtDateFrom", "CREDIT DEBT START DATE: " + debtDateFrom);
        else model.addAttribute("creditDebtDateFrom", "null");
        if (debtDateTo != null) model.addAttribute("creditDebtDateTo", "CREDIT DEBT ENDS DATE: " + debtDateTo);
        else model.addAttribute("creditDebtDateTo", "null");
        model.addAttribute("creditDebtAmount", "CREDIT DEBT AMOUNT: " + personalCreditAccount.getDebt().getAmountDebt());
        return URL.PERSONAL_CREDIT;
    }

    @RequestMapping(value = "deletePersonalCredit/{creditId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePersonalCredit(@PathVariable("creditId") BigInteger creditId) {
        logger.debug("[createCreditAccount]" + MessageController.debugStartMessage  + "[debitId = " + debitId + "], [creditId = " + creditId + "]");
        creditAccountDao.deleteCreditAccountByCreditId(creditId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
