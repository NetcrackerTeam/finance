package com.netcracker.controllers;

import com.netcracker.exception.NullObjectException;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.Status;
import com.netcracker.services.FamilyCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/familyCredit")
public class CreditFamilyController {

    @Autowired
    FamilyCreditService creditService;

    @RequestMapping(value = "/getFamilyCredits", method = RequestMethod.GET)
    @ResponseBody
    public Collection<FamilyCreditAccount> getAllCredits(@PathVariable("id") BigInteger id) {
        return creditService.getFamilyCredits(id);
    }

    @RequestMapping(value = "{idCr}/pay", method = RequestMethod.PUT)
    @ResponseBody
    public Status addFamilyCreditPayment(@PathVariable("id") BigInteger id,
                                         @PathVariable("idCr") BigInteger idCr,
                                         @RequestParam("amount") double amount,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                         @RequestParam("userId") BigInteger userId) {
        Status status = new Status();
        try {
            creditService.addFamilyCreditPayment(id, idCr, amount, date, userId);
            status.setStatus(true);
            status.setMessage("Added successfully");
        } catch (NullObjectException e) {
            status.setStatus(false);
            status.setMessage("Invalid parameters");
        }
        return status;
    }
}
