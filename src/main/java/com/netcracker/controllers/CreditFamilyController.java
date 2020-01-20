package com.netcracker.controllers;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.models.*;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.FamilyCreditService;
import com.netcracker.services.PersonalCreditService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.netcracker.controllers.MessageController.NOT_ACTIVE_USER;
import static com.netcracker.controllers.UserController.getCurrentUsername;

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
    CreditOperationDao creditOperationDao;

    @Autowired
    PersonalCreditService personalCreditService;
    private static final Logger logger = Logger.getLogger(CreditFamilyController.class);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Status createFamilyCreditAccount(@RequestBody FamilyCreditAccount creditAccount, Principal principal) {
        logger.debug("[createFamilyCreditAccount]" + MessageController.debugStartMessage + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "]");
        familyDebitId = getFamilyAccountByPrincipal(principal);
        userId = getUserIdByPrincipal(principal);
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        boolean validUserActive = UserStatusActive.YES.equals(userTemp.getUserStatusActive());
        if (!validUserActive)
            return new Status(false, NOT_ACTIVE_USER);
        if (creditAccount.getName().length() > 20) {
            return new Status(false, MessageController.CREDIT_NAME_LENGHT_FULL);
        }
        Status checked = personalCreditService.checkCreditData(creditAccount);
        if (!checked.isStatus()) return checked;
        familyCreditService.createFamilyCredit(familyDebitId, creditAccount, userId);
        return new Status(true, MessageController.ADD_FAMILY_CREDIT + creditAccount.getName());
    }

    @RequestMapping(value = "/pay/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Status addFamilyCreditPayment(@RequestBody Double amount, Principal principal, @PathVariable("id") BigInteger crId) {
        if (amount < MessageController.MIN || amount > MessageController.MAX)
            return new Status(false, MessageController.INCORRECT_AMOUNT);
        getUserInfo(principal);
        familyDebitId = getFamilyAccountByPrincipal(principal);
        userId = getUserIdByPrincipal(principal);
        logger.debug("[addFamilyCreditPayment]" + MessageController.debugStartMessage + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "], [creditId = " + crId + "]");
        try {
            familyCreditService.addFamilyCreditPayment(familyDebitId, crId, amount, new Date(), userId);
        } catch (CreditAccountException ex) {
            return new Status(false, ex.getMessage());
        }
        return new Status(true, MessageController.SUCCESS_CREDIT_PAYMENT);
    }

    @RequestMapping(value = "/getFamilyCredits", method = RequestMethod.GET)
    public @ResponseBody
    Collection<FamilyCreditAccount> getFamilyCredits(Principal principal) {
        getUserInfo(principal);
        familyDebitId = getFamilyAccountByPrincipal(principal);
        userId = getUserIdByPrincipal(principal);
        logger.debug("[getFamilyCredits]" + MessageController.debugStartMessage + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "]");
        return familyCreditService.getFamilyCredits(familyDebitId);
    }

    @RequestMapping(value = "/getFamilyCredit", method = RequestMethod.GET)
    public @ResponseBody
    FamilyCreditAccount getFamilyCredit(Principal principal) {
        familyDebitId = getFamilyAccountByPrincipal(principal);
        userId = getUserIdByPrincipal(principal);
        logger.debug("[getFamilyCredit]" + MessageController.debugStartMessage + "[familyDebitId = " + familyDebitId + "], [creditId = " + creditId + "]");
        return creditAccountDao.getFamilyCreditById(creditId);
    }

    @RequestMapping(value = "/sendCreditId", method = RequestMethod.POST)
    public ResponseEntity<String> sendCreditId(@RequestBody BigInteger creditId, Principal principal) {
        familyDebitId = getFamilyAccountByPrincipal(principal);
        userId = getUserIdByPrincipal(principal);
        logger.debug("[sendCreditId]" + MessageController.debugStartMessage + "[debitId = " + familyDebitId + "], [creditId = " + creditId + "]");
        this.creditId = creditId;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "deleteFamilyCredit/{creditId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFamilyCredit(@PathVariable("creditId") BigInteger creditId) {
        logger.debug("[deleteFamilyCredit]" + MessageController.debugStartMessage + "[personalDebitID = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [userId = " + userId + "], [creditId = " + creditId + "]");
        creditAccountDao.deleteCreditAccountByCreditId(creditId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "updateFamilyCredit", method = RequestMethod.PUT)
    public ResponseEntity<FamilyCreditAccount> updateFamilyCreditAccount(@RequestBody FamilyCreditAccount familyCreditAccount, Principal principal) {
        familyDebitId = getFamilyAccountByPrincipal(principal);
        userId = getUserIdByPrincipal(principal);
        logger.debug("[updatePersonalCredit]" + MessageController.debugStartMessage + "[personalDebitId = " + personalDebitId +
                "], [familyDebitId = " + familyDebitId + "], [creditId = " + creditId + "]");
        creditAccountDao.updateCreditAccountByCreditId(familyCreditAccount, creditId);
        return new ResponseEntity<>(familyCreditAccount, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCreditHistoryFamily", method = RequestMethod.GET)
    public @ResponseBody
    List<CreditOperation> getCreditHistoryFamily() {
        logger.debug("[getCreditHistoryFamily]" + MessageController.debugStartMessage + "[familyDebitId = " + familyDebitId +
                "], [creditId = " + creditId + "]");
        return creditOperationDao.getAllCreditOperationsByCreditFamilyId(creditId);
    }

    private void getUserInfo(Principal principal) {
        currentUser = userDao.getUserByEmail(principal.getName());
        personalDebitId = currentUser.getPersonalDebitAccount();
        familyDebitId = currentUser.getFamilyDebitAccount();
        userId = currentUser.getId();
    }

    private BigInteger getFamilyAccountByPrincipal(Principal principal) {
        User user = userDao.getParticipantByEmail(principal.getName());
        return user.getFamilyDebitAccount();
    }

    private BigInteger getUserIdByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getId();
    }
}
