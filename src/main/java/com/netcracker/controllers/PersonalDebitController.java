package com.netcracker.controllers;

import com.google.gson.Gson;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.Status;
import com.netcracker.services.PersonalCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

@Controller
public class PersonalDebitController {


    @Autowired
    PersonalCreditService creditService;

    @RequestMapping(value = "{id}/addCredit", method = RequestMethod.POST)
    public String addCreditAccount(@RequestBody PersonalCreditAccount creditAccount,
                                   @PathVariable("id") BigInteger id, Model model) {
        creditService.createPersonalCredit(id, creditAccount);
        Gson gson = new Gson();
        Status status = new Status(true, "Credit account was created in account " + id);
        model.addAttribute("json_res", gson.toJson(status));
        return "test";
    }

    public void addIncomePersonal() {
    }

    public void addExpensePersonal() {
    }

    public void getHistory() {
    }

    public void createAutoIncome() {
    }

    public void createAutoExpense() {
    }

    public void deleteAutoIncome() {
    }

    public void deleteAutoExpence() {
    }

    public void getReport() {
    }

}
