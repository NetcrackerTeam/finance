package com.netcracker.controllers;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.exception.ErrorsMap;
import com.netcracker.models.*;
import com.netcracker.services.FamilyCreditService;
import com.netcracker.services.PersonalCreditService;
import com.netcracker.services.utils.ExceptionMessages;
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
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/familyCredit")
public class CreditFamilyController {
    @Autowired
    private FamilyCreditService familyCreditService;
    private BigInteger personalDebitId;
    private BigInteger familyDebitId;
    private BigInteger userId;
    private User currentUser;
    private BigInteger debitId;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CreditAccountDao creditAccountDao;
    private BigInteger creditId;

    @Autowired
    PersonalCreditService personalCreditService;
    private static final Logger logger = Logger.getLogger(CreditFamilyController.class);

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST)
    @ResponseBody
    public Status createFamilyCreditAccount(@RequestBody FamilyCreditAccount creditAccount, Principal principal) {
        debitId = userDao.getParticipantByEmail(principal.getName()).getFamilyDebitAccount();
        logger.debug("[createFamilyCreditAccount]" + MessageController.debugStartMessage  + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "]");
        if (!familyCreditService.doesCreditWithNameNotExist(debitId, creditAccount.getName()))
            return new Status(false, MessageController.CREDIT_NAME_EXISTS);
        Status checked = personalCreditService.checkCreditName(creditAccount.getName());
        if (!checked.isStatus()) return checked;
        familyCreditService.createFamilyCredit(familyDebitId, creditAccount);
        return new Status(true, MessageController.ADD_FAMILY_CREDIT + creditAccount.getName());
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public Status addFamilyCreditPayment(@RequestBody Double amount, Principal principal){
        getUserInfo(principal);
        logger.debug("[addFamilyCreditPayment]" + MessageController.debugStartMessage  + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "], [creditId = " + creditId + "]");
        try {
            familyCreditService.addFamilyCreditPayment(familyDebitId, creditId, amount, new Date(), userId);
        } catch (CreditAccountException ex) {
            return new Status(false, ex.getMessage());
        }
        return new Status(true, null);
    }

    @RequestMapping(value = "/getFamilyCredits", method = RequestMethod.GET)
    public @ResponseBody Collection<FamilyCreditAccount> getFamilyCredits(Principal principal){
        getUserInfo(principal);
        logger.debug("[getFamilyCredits]" + MessageController.debugStartMessage  + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "]");
        return familyCreditService.getFamilyCredits(familyDebitId);
    }

    @RequestMapping(value = "/getFamilyCredit", method = RequestMethod.GET)
    public @ResponseBody FamilyCreditAccount getPersonalCredit() {
        logger.debug("[getFamilyCredit]" + MessageController.debugStartMessage  + "[familyDebitId = " + familyDebitId + "], [creditId = " + creditId + "]");
        return creditAccountDao.getFamilyCreditById(creditId);
    }

    @RequestMapping(value = "/sendCreditId", method = RequestMethod.POST)
    public ResponseEntity<String> sendCreditId(@RequestBody BigInteger creditId) {
        logger.debug("[sendCreditId]" + MessageController.debugStartMessage  + "[debitId = " + familyDebitId + "], [creditId = " + creditId + "]");
        this.creditId = creditId;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "deleteFamilyCredit/{creditId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFamilyCredit(@PathVariable("creditId") BigInteger creditId) {
        logger.debug("[deleteFamilyCredit]" + MessageController.debugStartMessage  + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "], [creditId = " + creditId + "]");
        creditAccountDao.deleteCreditAccountByCreditId(creditId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "updateFamilyCredit", method = RequestMethod.PUT)
    public ResponseEntity<FamilyCreditAccount> updateFamilyCreditAccount(@RequestBody FamilyCreditAccount familyCreditAccount) {
        logger.debug("[updatePersonalCredit]" + MessageController.debugStartMessage  + "[personalDebitId = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [creditId = " + creditId + "]");
        creditAccountDao.updateCreditAccountByCreditId(familyCreditAccount, creditId);
        return new ResponseEntity<>(familyCreditAccount, HttpStatus.OK);
    }

    private void getUserInfo(Principal principal) {
        currentUser =  userDao.getUserByEmail(principal.getName());
        personalDebitId = currentUser.getPersonalDebitAccount();
        familyDebitId = currentUser.getFamilyDebitAccount();
        userId = currentUser.getId();
    }
}
